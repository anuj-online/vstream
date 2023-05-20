package com.app.views.list;

import com.app.backend.VideoFile;
import com.app.backend.VideoService;
import com.app.views.AppLayoutBottomNavBar;
import com.app.views.video.EmbedVideo;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Lazy;

@PageTitle("HomeFlix")
//@RouteAlias("")
@Route(value = "", layout = AppLayoutBottomNavBar.class)
@Lazy
//@Tag(value = "div")
public class ListVideo extends Scroller {
    public ListVideo(VideoService videoService) {
        super();
        setWidthFull();
        setHeightFull();
//        setScrollDirection(ScrollDirection.VERTICAL);
//        getStyle().set("overflow","auto");
        var parentDiv = new Div();
        parentDiv.getStyle().set("margin", "10");
        parentDiv.getStyle().set("padding", "10");
        parentDiv.getStyle().set("display", "inline-block");

        var dataProvider = new ListDataProvider<>(videoService.videoLists());
        dataProvider.getItems().forEach(videoFile -> {

//            Button title = new Button(new Icon(VaadinIcon.PLAY_CIRCLE));
//            title.addThemeVariants(ButtonVariant.LUMO_ICON);
//            title.setHeight("100px");
//            title.setWidth("10px");
//            title.getStyle().set("transition","all 0.3s ease 0s");
//            title.getStyle().set("cursor","pointer");
//            var name = videoFile.getName();
//            title.setText(name);
//            title.setTooltipText(name);

            Image image = new Image("icons/icon.png", "");

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

        setContent(parentDiv);
        setScrollDirection(ScrollDirection.VERTICAL);
    }

    private static Renderer<VideoFile> createToggleDetailsRenderer(String name) {
        return LitRenderer.<VideoFile>of("<vaadin-button height=\"100px\" width\"10px\" theme=\"secondary\" @click=\"${handleClick}\">" + name + "</vaadin-button>").withFunction("handleClick", movie -> {
            UI.getCurrent().navigate(EmbedVideo.class, movie.getIdentifier().toString());
        });
    }
}
