package nts.uk.nrl.request;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import nts.uk.nrl.Command;

/**
 * Named.
 * 
 * @author manhnd
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Named {
	
	Command value();
	
	boolean decrypt() default false;
}
