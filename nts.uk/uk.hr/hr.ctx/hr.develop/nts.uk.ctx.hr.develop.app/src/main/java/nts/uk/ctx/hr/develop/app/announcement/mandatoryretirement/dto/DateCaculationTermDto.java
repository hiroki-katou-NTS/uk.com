package nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto;

import lombok.Data;

@Data
public class DateCaculationTermDto {
	/** 算出条件 */
	private int calculationTerm;
	
	/** 指定数 */
	private Integer dateSettingNum;
	
	/** 指定日 */
	private Integer dateSettingDate;

	public DateCaculationTermDto(int calculationTerm, Integer dateSettingNum, Integer dateSettingDate) {
		super();
		this.calculationTerm = calculationTerm;
		this.dateSettingNum = dateSettingNum;
		this.dateSettingDate = dateSettingDate;
	}
}
