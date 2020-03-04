package nts.uk.ctx.hr.develop.ws.test.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.hr.shared.dom.dateTerm.DateCaculationTerm;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DateCaculationTermTestDto {

	private Integer calculationTerm;
	
	private Integer dateSettingNum;
	
	private Integer dateSettingDate;
	
	public DateCaculationTerm toDomain() {
		return DateCaculationTerm.createFromJavaType(this.calculationTerm, this.dateSettingNum, this.dateSettingDate);
	}

	public DateCaculationTermTestDto(DateCaculationTerm domain) {
		super();
		this.calculationTerm = domain.getCalculationTerm().value;
		this.dateSettingNum = domain.getDateSettingNum();
		this.dateSettingDate = domain.getDateSettingDate().isPresent()?domain.getDateSettingDate().get().value:null;
	}
	
	
}
