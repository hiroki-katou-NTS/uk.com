package nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto;

import lombok.Data;

@Data
public class DateTermDto {
	
	/** 退職日条件 */
	private int retireDateTerm;
	
	/** 退職日指定日 */
	private Integer retireDateSettingDate;

	public DateTermDto(int retireDateTerm, Integer retireDateSettingDate) {
		super();
		this.retireDateTerm = retireDateTerm;
		this.retireDateSettingDate = retireDateSettingDate;
	}
}
