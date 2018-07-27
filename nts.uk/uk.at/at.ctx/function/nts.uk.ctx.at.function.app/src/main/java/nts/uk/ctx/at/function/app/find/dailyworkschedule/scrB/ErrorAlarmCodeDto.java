/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.find.dailyworkschedule.scrB;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The Class ErrorAlarmCodeDto.
 * @author HoangDD
 */
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class ErrorAlarmCodeDto implements Comparable<ErrorAlarmCodeDto>{
	
	/** The code. */
	private String code;
	
	/** The name. */
	private String name;

	/**
	 * Instantiates a new error alarm code dto.
	 *
	 * @param code the code
	 * @param name the name
	 */
	public ErrorAlarmCodeDto(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ErrorAlarmCodeDto o) {
		return code.compareTo(o.getCode());
	}
}
