package core.controls;

import javafx.scene.control.Tab;
import ui.mptab.LocalTab;
import ui.mptab.OnlineTab;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 7/20/2017.
 */
public class TabController {
    private static Tab locaTab;
    private static Tab onlineTab;
    public final List<Tab> tabList;

    public TabController() {
        locaTab = new LocalTab();
        onlineTab = new OnlineTab();
        this.tabList = Arrays.asList(locaTab, onlineTab);
    }

    public List<Tab> getTabsList() {
        return this.tabList;
    }

    public Tab getOppenedTab(){
        for (Tab tab : tabList) {
            if(tab.isSelected()) {
                return tab;
            }
        }
        throw new RuntimeException("Unknown selected tab!");
    }

    public LocalTab getLocalTab(){
        return (LocalTab) this.locaTab;
    }

}
