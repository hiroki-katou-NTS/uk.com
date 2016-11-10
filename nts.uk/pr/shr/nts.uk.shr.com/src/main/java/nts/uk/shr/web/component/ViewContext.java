package nts.uk.shr.web.component;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletRequest;

@FacesComponent(tagName = "viewcontext", createTag = true)
public class ViewContext extends UIComponentBase {

	@Override
	public String getFamily() {
        return this.getClass().getName();
	}

    /**
     * Render beginning of component
     * 
     * @param context FacesContext
     * @throws IOException IOException
     */
    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        
    	ResponseWriter rw = context.getResponseWriter();
        rw.write("<script type=\"text/javascript\">window.__viewContext = {");
        
        writeRootPath(context, rw);
        rw.write(",");
        
        rw.write("};</script>");
        
    }
    
    private static void writeRootPath(FacesContext context, ResponseWriter rw) throws IOException {
        // convert "/hoge/fuga/piyo.xhtml" -> "../../"
        String requestedPath = ((HttpServletRequest) context.getExternalContext().getRequest())
                .getServletPath();
        String rootPath = requestedPath.replaceAll("[^/]", "")
                .substring(1)
                .replaceAll("/", "../");
        
        rw.write("rootPath: '" + rootPath + "'");
    }
    
    
}
