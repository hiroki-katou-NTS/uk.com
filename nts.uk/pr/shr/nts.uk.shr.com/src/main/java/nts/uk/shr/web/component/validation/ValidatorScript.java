package nts.uk.shr.web.component.validation;

import java.io.IOException;
import java.util.Arrays;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import lombok.val;
import nts.arc.primitive.constraint.PrimitiveValueConstraintPackage;

/**
 * ValidatorScript
 * 
 * @author hoanld
 */
@FacesComponent(tagName = "ValidatorScript", createTag = true)
public class ValidatorScript extends UIComponentBase {

	@Override
	public String getFamily() {
		return this.getClass().getName();
	}
	
	@Override
    public void encodeBegin(FacesContext context) throws IOException {
		
        val rw = context.getResponseWriter();
        
        rw.write("<script>");
        rw.write("\t__viewContext.primitiveValueConstraint = __viewContext.primitiveValueConstraint || {};");

        val primitives = Helper.getPrimitiveValueNames(this.getChildren().get(0).toString());
        for(String fqnOfPrimitiveValueClass : primitives) {
        	writePrimitiveValueConstraints(rw, fqnOfPrimitiveValueClass);
        }

        rw.write("</script>");
        
        // if not cleared, content strings is shown on browser...
        this.getChildren().clear();
    }
	
	private static void writePrimitiveValueConstraints(ResponseWriter rw, String fqnOfPrimitiveValueClass) throws IOException {
		
		val pvClass = Helper.findClass(fqnOfPrimitiveValueClass);
		String pvName = pvClass.getSimpleName();
		
		rw.append("\n\t__viewContext.primitiveValueConstraint.");
		rw.append(pvName);
		rw.append(" = {");
		
		rw.append("\n\t\tvalueType: '");
		rw.append(Helper.getValueType(pvClass));
		rw.append("',");
		
		Arrays.asList(pvClass.getDeclaredAnnotations()).stream()
			.map(a -> a.toString())
			.filter(r -> r.contains(PrimitiveValueConstraintPackage.NAME))
	        .forEach(representationOfAnnotation -> {
	        	String constraintName = Helper.getAnnotationName(representationOfAnnotation);
	        	String parametersString = Helper.getAnnotationParametersString(representationOfAnnotation);
				writeConstraints(rw, constraintName, parametersString);
	        });
		
		rw.append("\n\t};");
	}
	
	private static void writeConstraints(ResponseWriter rw, String constraintName, String parametersString) {
		
		if (Helper.CONSTRAINTS_SIGNLE_PARAM.containsKey(constraintName)) {
			String jsName = Helper.CONSTRAINTS_SIGNLE_PARAM.get(constraintName);
			String jsValue = Helper.parseSingleParameterValue(constraintName, parametersString);
			
			writeConstraintParameter(rw, jsName, jsValue);
			
		} else if (Helper.CONSTRAINTS_MAX_MIN_PARAM.contains(constraintName)) {
			val paramsMap = Helper.parseMultipleParametersString(parametersString);

			writeConstraintParameter(rw, "max", paramsMap.get("max"));
			writeConstraintParameter(rw, "min", paramsMap.get("min"));
		}
	}

	private static void writeConstraintParameter(ResponseWriter rw, String jsName, String jsValue) {
		
		try {
			rw.write("\n\t\t");
			rw.write(jsName);
			rw.write(": ");
			rw.write(jsValue);
			rw.write(",");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
