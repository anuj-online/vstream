package com.app.views.list;

import com.app.backend.VideoFile;
import com.app.backend.VideoService;
import com.app.views.video.EmbedVideo;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.*;
import org.springframework.context.annotation.Lazy;

@PageTitle("HomeFlix")
@Route("list")
@Lazy
public class ListVideo extends VerticalLayout {
    private final Grid<VideoFile> grid = new Grid<>(VideoFile.class, false);
    public ListVideo(VideoService videoService) {
        super();
        setAlignItems(FlexComponent.Alignment.CENTER);
        grid.setColumns("name");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setAllRowsVisible(true);
        grid.addColumn(createToggleDetailsRenderer(grid));
        add(grid);

        grid.setItems(new ListDataProvider<>(videoService.videoLists()));
    }
    private static Renderer<VideoFile> createToggleDetailsRenderer(
            Grid<VideoFile> grid) {
        return LitRenderer.<VideoFile> of(
                        "<vaadin-button theme=\"tertiary\" @click=\"${handleClick}\">Play</vaadin-button>")
                .withFunction("handleClick",
                        movie -> {
                            UI.getCurrent().navigate(EmbedVideo.class, movie.getIdentifier().toString());
                        });
    }
}
