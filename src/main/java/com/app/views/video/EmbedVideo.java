package com.app.views.video;

import com.app.backend.VideoService;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@PageTitle("HomeFlix")
@Route("play")
public class EmbedVideo extends VerticalLayout implements HasUrlParameter<String> {
    public EmbedVideo() {
        super();
        setAlignItems(FlexComponent.Alignment.CENTER);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        Video video = new Video(s);
        video.setMaxWidth("500px");
        add(video);
        H2 header = new H2("Now Playing " + s);
        header.addClassNames(LumoUtility.Margin.Top.XLARGE, LumoUtility.Margin.Bottom.MEDIUM);
        add(header);
    }
}