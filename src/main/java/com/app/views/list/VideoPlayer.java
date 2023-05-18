//package com.app.views.streaming;
//import com.app.backend.VideoService;
//import com.brownie.videojs.VideoJS;
//import com.vaadin.flow.component.UI;
//import com.vaadin.flow.component.html.Header;
//import com.vaadin.flow.component.html.Label;
//import com.vaadin.flow.component.orderedlayout.FlexComponent;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.router.*;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.File;
//
//@PageTitle("Video streaming")
//@Route("video")
//public class VideoPlayer extends VerticalLayout implements HasUrlParameter<String> {
//
//    @Autowired
//    private VideoService service;
//    @Override
//    public void setParameter(BeforeEvent beforeEvent, @WildcardParameter String file) {
//        var label = new Label();
//        label.setText("File is :" + file);
//        add(label);
//
//        var videoJS = new VideoJS(UI.getCurrent().getSession(),
//                service.getVideo(file),
//                null);
//
//        videoJS.getElement().getStyle().set("height", "50%");
//        videoJS.getElement().getStyle().set("width", "100%");
//        add(videoJS);
//        add(new Label("This is a demo description of the vide"));
//        setHeight("50%");
//        setAlignItems(FlexComponent.Alignment.CENTER);
//        setMaxWidth("100%");
//    }
//
//}
