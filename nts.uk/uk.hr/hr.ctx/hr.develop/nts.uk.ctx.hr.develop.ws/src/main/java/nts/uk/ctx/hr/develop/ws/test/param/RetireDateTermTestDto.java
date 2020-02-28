package nts.uk.ctx.hr.develop.ws.test.param;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetireDateTerm;

@NoArgsConstructor
@Setter
@Getter
public class RetireDateTermTestDto {

	private Integer retireDateTerm;
	
	private Integer retireDateSettingDate;
	
	public RetireDateTerm toDomain() {
		return RetireDateTerm.createFromJavaType(this.retireDateTerm, this.retireDateSettingDate);
	}

	public RetireDateTermTestDto(RetireDateTerm domain) {
		super();
		this.retireDateTerm = domain.getRetireDateTerm().value;
		this.retireDateSettingDate = domain.getRetireDateSettingDate().isPresent()?domain.getRetireDateSettingDate().get().value:null;
	}
	
	
}
