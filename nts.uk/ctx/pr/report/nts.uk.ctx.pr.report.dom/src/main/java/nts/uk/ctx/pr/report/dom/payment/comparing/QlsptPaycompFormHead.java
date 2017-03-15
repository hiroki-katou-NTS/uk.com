package nts.uk.ctx.pr.report.dom.payment.comparing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class QlsptPaycompFormHead extends AggregateRoot {

	private String companyCode;

	private FormCode formCode;

	private FormName formName;

	public static QlsptPaycompFormHead createFromJavaType(String companyCode, String formCode, String formName) {
		return new QlsptPaycompFormHead(companyCode, new FormCode(formCode), new FormName(formName));
	}

}
