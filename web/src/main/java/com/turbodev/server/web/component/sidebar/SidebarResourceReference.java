package com.turbodev.server.web.component.sidebar;

import java.util.List;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;

import com.turbodev.server.web.asset.cookies.CookiesResourceReference;
import com.turbodev.server.web.asset.perfectscrollbar.PerfectScrollbarResourceReference;
import com.turbodev.server.web.page.base.BaseDependentCssResourceReference;
import com.turbodev.server.web.page.base.BaseDependentResourceReference;

public class SidebarResourceReference extends BaseDependentResourceReference {

	private static final long serialVersionUID = 1L;
	
	public SidebarResourceReference() {
		super(SidebarResourceReference.class, "sidebar.js");
	}

	@Override
	public List<HeaderItem> getDependencies() {
		List<HeaderItem> dependencies = super.getDependencies();
		dependencies.add(JavaScriptHeaderItem.forReference(new CookiesResourceReference()));
		dependencies.add(JavaScriptHeaderItem.forReference(new PerfectScrollbarResourceReference()));
		dependencies.add(CssHeaderItem.forReference(new BaseDependentCssResourceReference(SidebarResourceReference.class, "sidebar.css")));
		return dependencies;
	}

}
