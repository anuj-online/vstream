package com.app;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.*;
import jakarta.servlet.http.HttpServletResponse;

@ParentLayout(MainLayout.class)
public class CustomNotFoundTarget
        extends RouteNotFoundError {

    @Override
    public int setErrorParameter(BeforeEnterEvent event,
                                 ErrorParameter<NotFoundException> parameter) {

        UI.getCurrent().navigateToClient("");
        return HttpServletResponse.SC_TEMPORARY_REDIRECT;
    }
}
class MainLayout extends Div
        implements RouterLayout {
    @Override
    public Element getElement() {
        return super.getElement();
    }
}