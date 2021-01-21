package nts.uk.ctx.at.record.pub.monthlyprocess.agreement;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeYear;

/**
 * 36協定時間Output
 * @author shuichi_ishida
 */
@Getter
public class AgreementTimeExport {

	/** 36協定年間時間 */
	private Optional<AgreementTimeYear> agreementTimeYear;
	/** 36協定上限複数月平均時間 */
	private Optional<AgreMaxAverageTimeMulti> agreMaxAverageTimeMulti;
	
	public AgreementTimeExport(){
		this.agreementTimeYear = Optional.empty();
		this.agreMaxAverageTimeMulti = Optional.empty();
	}

	public AgreementTimeExport(AgreementTimeYear agreementTimeYear, AgreMaxAverageTimeMulti agreMaxAverageTimeMulti) {
		this.agreementTimeYear = Optional.ofNullable(agreementTimeYear);
		this.agreMaxAverageTimeMulti = Optional.ofNullable(agreMaxAverageTimeMulti);
	}
}
