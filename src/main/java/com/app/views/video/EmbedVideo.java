package com.app.views.video;

import com.app.backend.VideoService;
import com.app.views.AppLayoutBottomNavBar;
import com.vaadin.flow.component.html.H1;
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
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("HomeFlix")
@Route(value = "play", layout = AppLayoutBottomNavBar.class)
public class EmbedVideo extends VerticalLayout implements HasUrlParameter<String> {
    @Autowired
    private VideoService service;
    public EmbedVideo() {
        super();
        setAlignItems(FlexComponent.Alignment.CENTER);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        Video video = new Video(s);
        video.setMaxWidth("500px");
        add(video);
        H1 header = new H1("Now Playing ");
        var name = service.getVideo(s).getName();
        H1 movieName = new H1(name.substring(0, name.indexOf(".")));
        add(movieName);
        header.addClassNames(LumoUtility.Margin.Top.XLARGE, LumoUtility.Margin.Bottom.MEDIUM);
        add(header);
    }
}