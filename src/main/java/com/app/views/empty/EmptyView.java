//package com.app.views.empty;
//
//import com.app.views.AppLayoutBottomNavBar;
//import com.app.views.list.ListVideo;
//import com.vaadin.flow.component.UI;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.html.Image;
//import com.vaadin.flow.component.orderedlayout.FlexComponent;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.router.PageTitle;
//import com.vaadin.flow.router.Route;
//import com.vaadin.flow.router.RouteAlias;
//import lombok.SneakyThrows;
//
//@PageTitle("HomeFlix")
//@Route(value = "empty", layout = AppLayoutBottomNavBar.class)
//@RouteAlias(value = "")
//public class EmptyView extends VerticalLayout {
//    private final Button button = new Button("Get started");
//    @SneakyThrows
//    public EmptyView() {
//        super();
//        Image img = new Image("images/icon.png", "Home Flix");
//        img.setSizeFull();
//        add(img);
//        setAlignItems(FlexComponent.Alignment.CENTER);
//        this.getStyle().set("background-color","rgba(176,0,0,255)");
//        UI.getCurrent().navigateToClient(getRoute());
////        add(button);
////        button.addClickListener(buttonClickEvent -> {
////            UI.getCurrent().navigate(ListVideo.class);
////        });
//    }
//
//    @SneakyThrows
//    private static String getRoute() {
//        Thread.sleep(2000L);
//        return "list";
//    }
//
//}
