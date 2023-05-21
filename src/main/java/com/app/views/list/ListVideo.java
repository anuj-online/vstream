package com.app.views.list;

import com.app.backend.services.VServiceInterface;
import com.app.backend.controllers.VideoFile;
import com.app.views.video.EmbedVideo;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.ScrollOptions;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Lazy;

@PageTitle("HomeFlix")
@Route(value = "")
@Lazy
public class ListVideo extends Scroller {
    public ListVideo(VServiceInterface videoService) {
        super();
        getStyle().set("background", """
                linear-gradient(black,#501414)
                """);
        setScrollDirection(ScrollDirection.VERTICAL);
        var parentDiv = new Div();
        parentDiv.getStyle().set("margin", "10");
        parentDiv.getStyle().set("padding", "10");
        parentDiv.getStyle().set("display", "inline-block");
        scrollIntoView();
        var scrollOptions = new ScrollOptions(ScrollOptions.Behavior.SMOOTH);
        var dataProvider = new ListDataProvider<>(videoService.videoLists());
        dataProvider.getItems().forEach(videoFile -> {

            Image image = new Image("https://drive.google.com/uc?id=1o7rhnNVIqG9gDgTMLD4MWZzPAo9j8DXG&authuser=2&export=download", "");
            parentDiv.scrollIntoView();
            image.setText(videoFile.getName());
            image.setAlt(videoFile.getName());

            image.setHeight("100%");
            image.setWidth("90%");
            //transform: translateY(-7px) //TODO add hover the title on mouse over
            image.getStyle().set("margin", "10px");
            image.getStyle().set("opacity", "0.5");
            image.getStyle().set("cursor", "pointer");
            image.getStyle().set("box-shadow", "10px 10px 10px black");
            image.getStyle().set("transition", "all 0.3s ease 0s");

            image.addClickListener(clickEvent -> UI.getCurrent().navigate(EmbedVideo.class, videoFile.getIdentifier().toString()));
            var text = new Label(videoFile.getName());
            text.getStyle().set("color", "white");
//            text.getStyle().set()
            text.setWidth("80%");
            text.setWhiteSpace(HasText.WhiteSpace.PRE_WRAP);
            text.setWidthFull();
            var movieDiv = new Div();
            movieDiv.getStyle().set("display", "inline-block");
            movieDiv.add(image);
            movieDiv.add(text);
            movieDiv.setHeight("300px");
            movieDiv.setWidth("19%");
            parentDiv.add(movieDiv);
        });
        scrollIntoView(scrollOptions);
        setContent(parentDiv);
        setScrollDirection(ScrollDirection.VERTICAL);
    }

    private static Renderer<VideoFile> createToggleDetailsRenderer(String name) {
        return LitRenderer.<VideoFile>of("<vaadin-button height=\"100px\" width\"10px\" theme=\"secondary\" @click=\"${handleClick}\">" + name + "</vaadin-button>").withFunction("handleClick", movie -> {
            UI.getCurrent().navigate(EmbedVideo.class, movie.getIdentifier().toString());
        });
    }
}
