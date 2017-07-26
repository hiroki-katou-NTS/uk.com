package nts.uk.shr.infra.web.component;

import java.io.IOException;
import java.util.Arrays;

import javax.enterprise.inject.spi.CDI;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletRequest;

import nts.arc.i18n.custom.IInternationalization;
import nts.uk.shr.com.program.ProgramsManager;
import nts.uk.shr.com.program.WebAppId;
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

		String requestedPath = ((HttpServletRequest) context.getExternalContext().getRequest()).getServletPath();
		String applicationContextPath = context.getExternalContext().getApplicationContextPath();
		
		ResponseWriter rw = context.getResponseWriter();
		rw.write("<script type=\"text/javascript\">window.__viewContext = {");

		writeRootPath(requestedPath, rw);
		rw.write(",");

		writeProgramInfo(requestedPath, rw, applicationContextPath);
		rw.write(",");
		
		rw.write("};");
		rw.write("__viewContext.primitiveValueConstraints = __viewContext.primitiveValueConstraints || {};");

		CDI.current().select(ViewContextEnvWriter.class).get().write(rw);
		rw.write("</script>");
		rw.write("<script src='/nts.uk.com.web/webapi/loadresource'></script>");

	}
	
	private void writeProgramInfo (String requestedPath, ResponseWriter rw, String applicationContextPath) throws IOException {
		WebAppId webApi = Arrays.asList(WebAppId.values()).stream()
				.filter(w -> applicationContextPath.indexOf(w.name) >= 0).findFirst().orElse(WebAppId.COM);
		
		StringBuilder builder = new StringBuilder();
		ProgramsManager.find(webApi, requestedPath).ifPresent(pr -> {
			builder.append("webapi: '" + pr.getAppId().name + "', ");
			builder.append("programId: '" + pr.getPId() + "', ");
			IInternationalization internationalization = CDI.current().select(IInternationalization.class).get();
			String programName = internationalization.getItemName(pr.getPName()).orElse(pr.getPName());
			builder.append("programName: '" + programName + "', ");
			builder.append("path: '" + pr.getPPath() + "'");
		});
		
		rw.write("program: {" + builder.toString() + "}");
	}

	private static void writeRootPath(String requestedPath, ResponseWriter rw) throws IOException {
		// convert "/hoge/fuga/piyo.xhtml" -> "../../"
//		String requestedPath = ((HttpServletRequest) context.getExternalContext().getRequest()).getServletPath();
		String rootPath = requestedPath.replaceAll("[^/]", "").substring(1).replaceAll("/", "../");

		rw.write("rootPath: '" + rootPath + "'");
	}
}
