package nts.uk.pr.web.component;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.PrimitiveValueConstraintPackage;
import nts.gul.reflection.ReflectionUtil;

/**
 * 
 * @author hoanld
 *
 */
@FacesComponent(tagName = "ValidatorScript", createTag = true)
public class ValidatorScript extends UIComponentBase {

	@Override
	public String getFamily() {
		// TODO Auto-generated method stub
		return this.getClass().getName();
	}
	
	private String getPartInfo(String className) {
		StringBuilder result =  new StringBuilder("");
		String constraint = className.substring(className.lastIndexOf(".")+1);
		result.append("\n\t__viewContext.primitiveValueConstraint."+ constraint + "= {");
		Class<?> inputClass = null;
		try {
			inputClass = Class.forName(className);
		} catch (ClassNotFoundException ex) {
			//throw RuntimeException if needed
		}
		/**begin valueType section**/
		//here for String Primitive
		if(StringPrimitiveValue.class.isAssignableFrom(inputClass)) {
			result.append("\n\t\tvalueType: \"String\",");
		}
		//here for IntegerPrimitive
		else if(IntegerPrimitiveValue.class.isAssignableFrom(inputClass)) {
			result.append("\n\t\tvalueType: \"Integer\",");
		}
		//here for Decimal Primitive
		else if(DecimalPrimitiveValue.class.isAssignableFrom(inputClass)) {
			result.append("\n\t\tvalueType: \"Decimal\",");
		}
		/**end valueType section**/
		Arrays.asList(inputClass.getDeclaredAnnotations()).stream()
        .filter(a -> a.toString().contains(PrimitiveValueConstraintPackage.NAME))
        .forEach(a -> {
        	String validationInfo = a.toString();
        	int index1 = validationInfo.lastIndexOf(".") + 1;
        	int index2 = validationInfo.indexOf("(");
        	String validationClass = validationInfo.substring(index1, index2);
        	String targetValue = validationInfo.substring(index2+1, validationInfo.length()-1).replaceAll("value=","");
        	result.append("\n\t\t" + validationClass + ": " + targetValue + ",");
        });
		result.setLength(result.length()-1);
		result.append("\n\t}");
		/*
		 * Coppy area
		 Arrays.asList(this.getClass().getDeclaredAnnotations()).stream()
        .filter(a -> a.toString().contains(PrimitiveValueConstraintPackage.NAME))
        .forEach(a -> {

            Object constraintValue = Arrays.asList(a.annotationType().getDeclaredMethods()).stream()
            .collect(Collectors.toMap(c -> c.getName(),
                            c -> ReflectionUtil.invoke(a.annotationType(), a, c.getName())));
        });
		*/
		//result.append(str)
		result.append("\n");
		return result.toString();
	}
	@Override
    public void encodeBegin(FacesContext context) throws IOException {
        ResponseWriter rw = context.getResponseWriter();
        rw.write("<script>");
        String classAttribute = (String) this.getAttributes().get("inputclass");
        String[] classNames = classAttribute.replaceAll("\\s","").split(",");
        for(String className: classNames) {
        	rw.write(getPartInfo(className));
        }
        rw.write("</script>");
        //Class<?> inputClass = Class.forName(className);
        //rw.write("<script>" + name + "</script>");
    }
}
