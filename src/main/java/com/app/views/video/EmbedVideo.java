package com.app.views.video;

import com.app.backend.service.VAccess;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@PageTitle("HomeFlix")
@Route(value = "play")
public class EmbedVideo extends VerticalLayout implements HasUrlParameter<String> {
    private final VAccess service;

    public EmbedVideo(VAccess service) {
        super();
        this.service = service;
        getStyle().set("background", """
                linear-gradient(black,#501414)
                """);
        setWidthFull();
        setHeightFull();
        setAlignItems(FlexComponent.Alignment.CENTER);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        var horizontalLayout = new HorizontalLayout();
        horizontalLayout.getStyle().set("background", """
                black)
                """);
        var back = new Button(new Icon(VaadinIcon.ARROW_LEFT));
        back.addThemeVariants(ButtonVariant.LUMO_ICON);
        add(back);

        back.addClickListener(event -> UI.getCurrent().navigate(""));
        var name = service.getDisplayName(s);
        var video = new Video(s);
        H1 header = new H1("Now Playing ");
        header.getStyle()
                .set("color", "white");
        H1 movieName = new H1(name);
        movieName
                .getStyle()
                .set("color", "white");
        header.addClassNames(LumoUtility.Margin.Top.MEDIUM, LumoUtility.Margin.Bottom.MEDIUM);
        add(header);
        horizontalLayout.setHeightFull();
        horizontalLayout.setWidthFull();
        horizontalLayout.add(back);
        horizontalLayout.add(video);

        add(horizontalLayout);
    }

    @ControllerAdvice
    static class A extends ResponseEntityExceptionHandler {

        @ExceptionHandler(value = {NotFoundException.class})
        protected ResponseEntity<Object> handleConflict(
                RuntimeException ex, WebRequest request) {
            return null;
        }
    }
}