package nts.uk.ctx.at.record.dom.adapter.person;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
@Data
@Builder
public class PersonInfoImport {
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
	
	private String employeeCode;

	public PersonInfoImport(String personId, 
			String personName, 
			GeneralDate birthDay, 
			String pMailAddr, 
			int gender,
			String employeeCode) {
		super();
		this.personId = personId;
		this.personName = personName;
		this.birthDay = birthDay;
		this.pMailAddr = pMailAddr;
		this.gender = gender;
		this.employeeCode = employeeCode;
	}
	
}
