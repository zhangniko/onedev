package com.turbodev.server.web.page.project.blob.render.renderers.source;

import java.util.List;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;

import com.turbodev.server.web.asset.codemirror.CodeMirrorResourceReference;
import com.turbodev.server.web.asset.cookies.CookiesResourceReference;
import com.turbodev.server.web.asset.diffmatchpatch.DiffMatchPatchResourceReference;
import com.turbodev.server.web.asset.jqueryui.JQueryUIResourceReference;
import com.turbodev.server.web.page.base.BaseDependentCssResourceReference;
import com.turbodev.server.web.page.base.BaseDependentResourceReference;

public class SourceEditResourceReference extends BaseDependentResourceReference {

	private static final long serialVersionUID = 1L;

	public SourceEditResourceReference() {
		super(SourceEditResourceReference.class, "source-edit.js");
	}

	@Override
	public List<HeaderItem> getDependencies() {
		List<HeaderItem> dependencies = super.getDependencies();
		dependencies.add(JavaScriptHeaderItem.forReference(new JQueryUIResourceReference()));
		dependencies.add(JavaScriptHeaderItem.forReference(new DiffMatchPatchResourceReference()));
		dependencies.add(JavaScriptHeaderItem.forReference(new CookiesResourceReference()));
		dependencies.add(JavaScriptHeaderItem.forReference(new CodeMirrorResourceReference()));
		dependencies.add(CssHeaderItem.forReference(
				new BaseDependentCssResourceReference(SourceEditResourceReference.class, "source-edit.css")));
		return dependencies;
	}

}
