package com.app.views.empty;

import com.app.views.list.ListVideo;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("HomeFlix")
@Route(value = "empty")
@RouteAlias(value = "")
public class EmptyView extends VerticalLayout {
    private final Button button = new Button("Get started");

    public EmptyView() {
        setAlignItems(FlexComponent.Alignment.CENTER);
        add(button);
        button.addClickListener(buttonClickEvent -> {
            UI.getCurrent().navigate(ListVideo.class);
        });
    }

}
