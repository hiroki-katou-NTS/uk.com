/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.adapter.employee;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class EmployeeInfoDtoImport.
 */

/**
 * Gets the person id.
 *
 * @return the person id
 */
@Getter

/**
 * Sets the person id.
 *
 * @param personId the new person id
 */
@Setter

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Builder
public class EmployeeInfoDtoImport {

	/** The company id. */
	private String companyId;

	/** The employee code. */
	private String employeeCode;

	/** The employee id. */
	private String employeeId;

	/** The person id. */
	private String personId;

	/**
	 * Instantiates a new employee info dto import.
	 *
	 * @param companyId the company id
	 * @param employeeCode the employee code
	 * @param employeeId the employee id
	 * @param personId the person id
	 */
	public EmployeeInfoDtoImport(String companyId, String employeeCode, String employeeId, String personId) {
		super();
		this.companyId = companyId;
		this.employeeCode = employeeCode;
		this.employeeId = employeeId;
		this.personId = personId;
	}

	/**
	 * Distinct by key.
	 *
	 * @param <T> the generic type
	 * @param keyExtractor the key extractor
	 * @return the predicate
	 */
	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
	    Set<Object> seen = ConcurrentHashMap.newKeySet();
	    return t -> seen.add(keyExtractor.apply(t));
	}
}
