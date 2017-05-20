package nts.uk.ctx.pr.report.app.payment.comparing.settingoutputitem.find;

import lombok.Value;
import nts.uk.ctx.pr.report.dom.payment.comparing.settingoutputitem.ComparingFormHeader;

@Value
public class ComparingFormHeaderDto {

	private String formCode;
	private String formName;

	public static ComparingFormHeaderDto fromDomain(ComparingFormHeader domain) {
		return new ComparingFormHeaderDto(domain.getFormCode().v(), domain.getFormName().v());
	}
}
