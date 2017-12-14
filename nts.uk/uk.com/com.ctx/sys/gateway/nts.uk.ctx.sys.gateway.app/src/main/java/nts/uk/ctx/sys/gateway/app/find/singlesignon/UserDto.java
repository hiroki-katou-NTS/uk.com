/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.find.singlesignon;

import lombok.Getter;
import lombok.Setter;

/**
 * Gets the person name.
 *
 * @return the person name
 */
@Getter

/**
 * Sets the person name.
 *
 * @param personName the new person name
 */
@Setter
public class UserDto {

	/** The person id. */
	private String personId;
	
	/** The user id. */
	private String userId;
	
	/** The employee code. */
	private String employeeCode;
	
	/** The login id. */
	private String loginId;
	
	/** The person name. */
	private String personName;
	
	/** The employee id. */
	private String employeeId;
	
	private Boolean isSetting;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDto other = (UserDto) obj;
		
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} 
		return true;
	}
	
	
}
