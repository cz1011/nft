package com.shitouren.core.controller;


import com.shitouren.core.contract.DDC1155;
import com.shitouren.core.contract.DDC721;
import com.shitouren.core.service.BlockEventService;
import org.junit.Test;

import org.web3j.protocol.core.methods.response.BaseEventResponse;

import java.math.BigInteger;
import java.util.ArrayList;
class GetDDCIDTest {
    DDCSdkClient client = new DDCSdkClient.Builder()
            .setAuthorityLogicAddress("0xFa1d2d3EEd20C4E4F5b927D9730d9F4D56314B29")
            .setChargeLogicAddress("0x0B8ae0e1b4a4Eb0a0740A250220eE3642d92dc4D")
            .setDDC721Address("0x354c6aF2cB870BEFEA8Ea0284C76e4A46B8F2870")
            .setDDC1155Address("0x0E762F4D11439B1130D402995328b634cB9c9973")
            .setGasLimit("400000")
            .setGasPrice("1")
            .setSignEventListener(new SignEventTest())
            .init();

    @Test
    void getDdcIdFromTxHash() throws Exception {
   client.setGatewayUrl("https://opbningxia.bsngate.com:18602/api/f7092d4a2149497b9d5245e3b1d8d213/evmrpc");
        BigInteger ddcId = null;
        BlockEventService blockEventService = new BlockEventService();
        ArrayList<BaseEventResponse> baseEventResponsesList = blockEventService.analyzeEventsByTxHash("0x86f94c429931f19c7b497cc5f8f1bac0dc1735e21bbf183dfec03ed2cfe89bdd");
        if (baseEventResponsesList != null && baseEventResponsesList.size() > 0) {
            for (int i = 0; i < baseEventResponsesList.size(); i++) {
                BaseEventResponse baseEventResponse = baseEventResponsesList.get(i);
                if (baseEventResponse instanceof DDC721.TransferEventResponse) {
                    ddcId = new BigInteger(String.valueOf(((DDC721.TransferEventResponse) baseEventResponse).ddcId));
                }
                if (baseEventResponse instanceof DDC1155.TransferSingleEventResponse) {
                    ddcId = new BigInteger(String.valueOf(((DDC1155.TransferSingleEventResponse) baseEventResponse).ddcId));
                }
            }
        }
        System.out.println("ddcid: " + ddcId);
    }
}