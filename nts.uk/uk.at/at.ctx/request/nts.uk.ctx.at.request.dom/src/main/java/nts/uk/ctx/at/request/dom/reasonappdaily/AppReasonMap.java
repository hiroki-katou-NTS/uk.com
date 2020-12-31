package nts.uk.ctx.at.request.dom.reasonappdaily;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;

/**
 * @author thanh_nx
 *
 */
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface AppReasonMap {

	ApplicationType type();

	PrePostAtr before();

	boolean fix() default false;

	OvertimeAppAtr overType() default OvertimeAppAtr.EARLY_OVERTIME;

}
