package nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeYear;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AgreementTimeImport {
	
	/**
	 * 36協定年間時間
	 */
	private AgreementTimeYear agreementTimeYear;
	
	/**
	 * 36協定上限複数月平均時間
	 */
	private AgreMaxAverageTimeMulti agreMaxAverageTimeMulti;
}
