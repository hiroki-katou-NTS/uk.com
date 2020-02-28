package nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetireDateTerm;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RetireDateTermDto {

	/** 退職日条件 */
	private int retireDateTerm;

	/** 退職日指定日 */
	private Integer retireDateSettingDate;

	public RetireDateTermDto(RetireDateTerm domain) {
		this.retireDateTerm = domain.getRetireDateTerm().value;
		this.retireDateSettingDate = domain.getRetireDateSettingDate().isPresent()
				? domain.getRetireDateSettingDate().get().value : null;
	}

}
