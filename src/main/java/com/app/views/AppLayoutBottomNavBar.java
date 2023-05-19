package com.app.views;

import com.app.views.list.ListVideo;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;

public class AppLayoutBottomNavBar extends AppLayout {

    public AppLayoutBottomNavBar() {
        super();
        H1 title = new H1("HomeFlix");

        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("left", "var(--lumo-space-l)").set("margin", "100")
                .set("position", "center");
        Tabs tabs = getTabs();

        H2 viewTitle = new H2("View title");

        Div content = new Div();
        content.getStyle().set("margin", "100")
                .set("position", "center")
        .set("left", "var(--lumo-space-l)")
                .set("padding", "100");

        content.add(viewTitle);
        addToNavbar(title);
        addToNavbar(true, tabs);

        setContent(content);
    }

    private Tabs getTabs() {
        var tabs = new Tabs();
        tabs.add(createMenuItem("Home", VaadinIcon.HOME, ListVideo.class));
        return tabs;
    }

    private Tab createMenuItem(String title, VaadinIcon icon, Class<? extends Component> target) {
        RouterLink link = new RouterLink(target);
        if (icon != null) link.add(icon.create());
//        link.add(title);
        Tab tab = new Tab();
        tab.add(link);
        return tab;
    }
}
