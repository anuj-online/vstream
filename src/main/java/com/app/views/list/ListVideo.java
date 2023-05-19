package com.app.views.list;

import com.app.backend.VideoFile;
import com.app.backend.VideoService;
import com.app.views.AppLayoutBottomNavBar;
import com.app.views.video.EmbedVideo;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.*;
import org.springframework.context.annotation.Lazy;

@PageTitle("HomeFlix")
//@RouteAlias("")
@Route(value = "", layout = AppLayoutBottomNavBar.class)
@Lazy
//@Tag(value = "div")
public class ListVideo extends HorizontalLayout {
    private final Grid<VideoFile> grid = new Grid<>(VideoFile.class, false);
    public ListVideo(VideoService videoService) {
        super();
        var div = new Div();
        div.getStyle().set("margin", "10");
        div.getStyle().set("padding", "10");
//        setAlignItems(FlexComponent.Alignment.CENTER);
        grid.setColumns("name");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setAllRowsVisible(true);
        grid.addColumn(createToggleDetailsRenderer(grid));
        grid.getStyle().set("background-image","linear-gradient(grey, black)");
        grid.setTabIndex(2);
        grid.getElement().getStyle().set("background-image","linear-gradient(grey, black)");
//        add(grid);

        var dataProvider = new ListDataProvider<VideoFile>(videoService.videoLists());
        dataProvider.getItems().forEach(videoFile -> {

            Button title = new Button(new Icon(VaadinIcon.PLAY_CIRCLE));
            title.addThemeVariants(ButtonVariant.LUMO_ICON);
            title.setHeight("100px");
            title.setWidth("10px");
            title.setText(videoFile.getName());
            div.add(title);
            title.addClickListener(clickEvent -> {
                UI.getCurrent().navigate(EmbedVideo.class, videoFile.getIdentifier().toString());
            });
        });
        grid.setItems(dataProvider);
        setPadding(true);
        setMargin(true);
        setAlignItems(Alignment.CENTER);
        setWidthFull();
        add(div);

    }
    private static Renderer<VideoFile> createToggleDetailsRenderer(
            Grid<VideoFile> grid) {
        return LitRenderer.<VideoFile> of(
                        "<vaadin-button theme=\"secondary\" @click=\"${handleClick}\">Play</vaadin-button>")
                .withFunction("handleClick",
                        movie -> {
                            UI.getCurrent().navigate(EmbedVideo.class, movie.getIdentifier().toString());
                        });
    }
}
