package nts.uk.pr.web.component;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
/**
 * 
 * @author hoanld
 *
 */
@FacesComponent(tagName = "ValidatorInitScript", createTag = true)
public class ValidatorInitRender extends UIComponentBase {
	
	@Override
	public String getFamily() {
		// TODO Auto-generated method stub
		return this.getClass().getName();
	}
	
	@Override
    public void encodeBegin(FacesContext context) throws IOException {
        ResponseWriter rw = context.getResponseWriter();
        rw.write("<script>");
        rw.write("\n\tvar __viewContext = {");
        rw.write("\n\t\tprimitiveValueConstraint: {");
        rw.write("\n\t\t}");
        rw.write("\n\t};");
        rw.write("\n</script>");
        //Class<?> inputClass = Class.forName(className);
        //rw.write("<script>" + name + "</script>");
    }
}
