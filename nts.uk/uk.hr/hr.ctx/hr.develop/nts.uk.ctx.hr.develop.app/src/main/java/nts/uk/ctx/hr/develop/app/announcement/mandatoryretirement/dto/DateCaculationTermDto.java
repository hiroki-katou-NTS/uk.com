package nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.hr.shared.dom.dateTerm.DateCaculationTerm;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DateCaculationTermDto {

	private Integer calculationTerm;
	
	private Integer dateSettingNum;
	
	private Integer dateSettingDate;
	
	public DateCaculationTerm toDomain() {
		return DateCaculationTerm.createFromJavaType(this.calculationTerm, this.dateSettingNum, this.dateSettingDate);
	}

	public DateCaculationTermDto(DateCaculationTerm domain) {
		super();
		this.calculationTerm = domain.getCalculationTerm().value;
		this.dateSettingNum = domain.getDateSettingNum();
		this.dateSettingDate = domain.getDateSettingDate().isPresent()?domain.getDateSettingDate().get().value:null;
	}
}
