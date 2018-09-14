/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.service;

import java.lang.reflect.Field;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.validate.Validatable;
import nts.gul.reflection.ReflectionUtil;

/**
 * The Class WorkTimeValidationSevice.
 */
public class WorkTimeValidationSevice {
	
	/**
	 * Validate.
	 *
	 * @param obj the obj
	 */
	public static void validate(WorkTimeDomainObject obj) {
		// Create new Bundled business exception.
		WorkTimeDomainObject.BundledBusinessExceptionBuffer bundledBusinessException = obj.getBundledBusinessExceptions();
		
		for (Field field : obj.getClass().getDeclaredFields()) {
			Object fieldValue = ReflectionUtil.getFieldValue(field, obj);
			if (fieldValue instanceof Validatable) {
				
				// Try catch business exception.
				try {
					((Validatable) fieldValue).validate();
				} catch (BusinessException e) {
					bundledBusinessException.add(e);
					continue;
				} catch (BundledBusinessException e) {
					bundledBusinessException.addAll(e.cloneExceptions());
					continue;
				}
			} else {
				
				// Try catch Bundled Business Exception.
				try {
					validateCollectionIfItIsCollection(fieldValue);
				} catch (BundledBusinessException e) {
					bundledBusinessException.addAll(e.cloneExceptions());
					continue;
				} catch (BusinessException e) {
					bundledBusinessException.add(e);
					continue;
				}
			}
		}
		
		// Throw BundledBusinessException.
		if (!bundledBusinessException.isEmpty()) {
			throw bundledBusinessException.bundle();
		}
	}

	/**
	 * Validate collection if it is collection.
	 *
	 * @param maybeCollection the maybe collection
	 */
	private static void validateCollectionIfItIsCollection(Object maybeCollection) {

		WorkTimeCollectionEnumerator.enumerate(maybeCollection, Validatable.class).ifSet(v -> v.validate())
				.ifList((v, i) -> v.validate()).ifMap(e -> e.getValue().validate());
	}
}
