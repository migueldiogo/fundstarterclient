package fundstarterclient;

import java.util.ArrayList;

/**
 * Created by xavier on 19-10-2015.
 */
public class OptionList extends ArrayList<String> {
    public OptionList() {}

    public void addOption(String option) {
        this.add(option);
    }

    public int getSize() {
        return this.size();
    }
}
