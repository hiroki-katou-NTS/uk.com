/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.person.pub.person;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * The Class PersonInfoExport.
 */
// 個人情報
// Output Class of RequestList #86
@Data
@Builder
public class PersonInfoExport {

	/** The p id. */
	// 個人ID
	private String personId;
	
	/** The p name. */
	// 個人名
	private String personName;
	
	/** The birth day. */
	// 生年月日
	private GeneralDate birthDay;
	
	/** The p mail addr. */
	// 個人メールアドレス
	private String pMailAddr;
	
	/** The p mail addr. */
	// 個人メールアドレス
	private int gender;

	public PersonInfoExport(String personId, String personName, GeneralDate birthDay, String pMailAddr, int gender) {
		super();
		this.personId = personId;
		this.personName = personName;
		this.birthDay = birthDay;
		this.pMailAddr = pMailAddr;
		this.gender = gender;
	}
	
	
}
