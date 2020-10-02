package nts.uk.ctx.at.function.app.nrl.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import nts.uk.ctx.at.function.app.nrl.Command;

/**
 * Request data.
 * 
 * @author manhnd
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface RequestData {
	
	Command[] value();
}
