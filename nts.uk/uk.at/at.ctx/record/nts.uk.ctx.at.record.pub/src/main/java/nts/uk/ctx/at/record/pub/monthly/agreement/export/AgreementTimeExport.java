package nts.uk.ctx.at.record.pub.monthly.agreement.export;

import java.util.Optional;

import lombok.Getter;

/**
 * 36協定時間Output
 * @author shuichi_ishida
 */
@Getter
public class AgreementTimeExport {

	/** 36協定年間時間 */
	private Optional<AgreementTimeYearExport> agreementTimeYear;
	/** 36協定上限複数月平均時間 */
	private Optional<AgreMaxAverageTimeMultiExport> agreMaxAverageTimeMulti;
	
	public AgreementTimeExport(){
		this.agreementTimeYear = Optional.empty();
		this.agreMaxAverageTimeMulti = Optional.empty();
	}

	public AgreementTimeExport(AgreementTimeYearExport agreementTimeYear, AgreMaxAverageTimeMultiExport agreMaxAverageTimeMulti) {
		this.agreementTimeYear = Optional.ofNullable(agreementTimeYear);
		this.agreMaxAverageTimeMulti = Optional.ofNullable(agreMaxAverageTimeMulti);
	}
}
