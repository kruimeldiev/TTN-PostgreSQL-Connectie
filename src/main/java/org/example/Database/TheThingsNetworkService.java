package org.example.Database;

import org.thethingsnetwork.data.common.Connection;
import org.thethingsnetwork.data.common.messages.ActivationMessage;
import org.thethingsnetwork.data.common.messages.DataMessage;
import org.thethingsnetwork.data.common.messages.DownlinkMessage;
import org.thethingsnetwork.data.common.messages.RawMessage;
import org.thethingsnetwork.data.common.messages.UplinkMessage;
import org.thethingsnetwork.data.mqtt.Client;

public class TheThingsNetworkService {

    public static void getDataFromTTN()  throws Exception {

        Client client = new Client(Constants.TTN_REGION, Constants.TTN_APP_ID, Constants.TTN_ACCESS_KEY);

        class Response {

            private boolean led;

            public Response(boolean _led) {
                led = _led;
            }
        }

        client.onMessage(null, "led", (String _devId, DataMessage _data) -> {
            try {
                RawMessage message = (RawMessage) _data;
                // Toggle the LED
                DownlinkMessage response = new DownlinkMessage(0, new Response(!message.asBoolean()));

                /**
                 * If you don't have an encoder payload function:
                 * client.send(_devId, new Response(0, message.asBoolean() ? new byte[]{0x00} : new byte[]{0x01}));
                 */
                System.out.println("Sending: " + response);
                client.send(_devId, response);
            } catch (Exception ex) {
                System.out.println("Response failed: " + ex.getMessage());
            }
        });

        // De waarde uit de TTN doorsturen naar ElephantSQL
        // Via Integer.valueOf wordt het java object (welke een String is) omgezet naar een Integer
        client.onMessage((String devId, DataMessage data) -> PostgresService.GasModuleWaardeInvoeren(devId, Integer.valueOf((String) ((UplinkMessage) data).getPayloadFields().get("ppm"))));

        // De waarde van de data uitprinten in console
        client.onMessage((String devId, DataMessage data) -> System.out.println("Message: " + devId + " " + ((UplinkMessage) data).getPayloadFields().values()));

        client.onActivation((String _devId, ActivationMessage _data) -> System.out.println("Activation: " + _devId + ", data: " + _data.getDevAddr()));

        client.onError((Throwable _error) -> System.err.println("error: " + _error.getMessage()));

        client.onConnected((Connection _client) -> System.out.println("Connectie met TTN gemaakt"));

        client.start();
    }
}
