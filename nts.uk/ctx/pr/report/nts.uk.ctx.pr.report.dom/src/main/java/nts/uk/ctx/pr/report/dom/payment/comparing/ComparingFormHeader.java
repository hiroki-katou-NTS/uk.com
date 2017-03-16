package nts.uk.ctx.pr.report.dom.payment.comparing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ComparingFormHeader extends AggregateRoot {

	private String companyCode;

	private FormCode formCode;

	private FormName formName;

	public static ComparingFormHeader createFromJavaType(String companyCode, String formCode, String formName) {
		return new ComparingFormHeader(companyCode, new FormCode(formCode), new FormName(formName));
	}

}
