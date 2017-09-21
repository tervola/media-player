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
    private Tab localTab;
    private Tab onlineTab;
    private static TabController INSTANCE;
    public final List<Tab> tabList;

    private TabController() {
        localTab = new LocalTab();
        onlineTab = new OnlineTab();
        this.tabList = Arrays.asList(localTab, onlineTab);
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
        return (LocalTab) localTab;
    }

    public OnlineTab getOnlineTab() {
        return (OnlineTab) onlineTab;
    }

    public static TabController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TabController();
        }
        return INSTANCE;
    }
}
