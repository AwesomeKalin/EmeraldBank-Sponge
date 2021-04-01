package com.github.AwesomeKalin.EmeraldBankSponge.api;

import com.github.AwesomeKalin.EmeraldBankSponge.ConfigurationManager;

import java.util.ArrayList;
import java.util.List;

public class GetBankPlace {
    public static int getBankPlace(String bank) {
        ConfigurationManager config = ConfigurationManager.getInstance();
        List<String> tempList = (List<String>) config.getConfig().getNode("bank.banks").getValue();
        assert tempList != null;
        ArrayList<String> tempBanks = new ArrayList<>(tempList);
        int banks = tempBanks.size();
        int place = 0;
        int i = 0;
        boolean found = false;
        while (!found) {
            if (tempList.get(i).equals(bank)) {
                place = i;
                found = true;
            } else {
                i += 1;
                if(i >= banks) {
                    place = -1;
                    found = true;
                }
            }
        }
        return place;
    }
}
