package nts.uk.shr.infra.web.component;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.inject.spi.CDI;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletRequest;

import nts.arc.i18n.custom.IInternationalization;
import nts.uk.shr.infra.web.component.env.ViewContextEnvWriter;

@FacesComponent(tagName = "viewcontext", createTag = true)
public class ViewContext extends UIComponentBase {

	@Override
	public String getFamily() {
		return this.getClass().getName();
	}

	/**
	 * Render beginning of component
	 * 
	 * @param context
	 *            FacesContext
	 * @throws IOException
	 *             IOException
	 */
	@Override
	public void encodeBegin(FacesContext context) throws IOException {

		ResponseWriter rw = context.getResponseWriter();
		rw.write("<script type=\"text/javascript\">window.__viewContext = {");

		writeRootPath(context, rw);
		rw.write(",");

		rw.write("};");
		rw.write("__viewContext.primitiveValueConstraints = __viewContext.primitiveValueConstraints || {};");

		CDI.current().select(ViewContextEnvWriter.class).get().write(rw);

		// add i18n to view context
		IInternationalization i18n = CDI.current().select(IInternationalization.class).get();
		String messageObject = createJsObject(i18n.getAllMessage());
		// TODO: temp fix program id
		String codeNameObject = createJsObject(i18n.getCodeNameResourceForProgram("QPP005"));
		rw.write("__viewContext.messages=" + messageObject);
		rw.write(";");
		rw.write("__viewContext.codeNames=" + codeNameObject);

		rw.write("</script>");

	}

	private static void writeRootPath(FacesContext context, ResponseWriter rw) throws IOException {
		// convert "/hoge/fuga/piyo.xhtml" -> "../../"
		String requestedPath = ((HttpServletRequest) context.getExternalContext().getRequest()).getServletPath();
		String rootPath = requestedPath.replaceAll("[^/]", "").substring(1).replaceAll("/", "../");

		rw.write("rootPath: '" + rootPath + "'");
	}

	private String createJsObject(Map<String, String> resource) {
		StringBuilder builder = new StringBuilder();
		builder.append("{");

		builder.append(resource.entrySet().stream().map(e -> e.getKey() + ":\"" + e.getValue() + "\"")
				.collect(Collectors.joining(",")));

		builder.append("}");
		return builder.toString();
	}

}
