package nts.uk.ctx.at.shared.dom.monthly.agreement;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

/**
 * 36協定時間Output
 * @author shuichi_ishida
 */
@Getter
@Setter
public class AgreementTimeOutput {

	/** 36協定年間時間 */
	private Optional<AgreementTimeYear> agreementTimeYear;
	/** 36協定上限複数月平均時間 */
	private Optional<AgreMaxAverageTimeMulti> agreMaxAverageTimeMulti;
	
	public AgreementTimeOutput(){
		this.agreementTimeYear = Optional.empty();
		this.agreMaxAverageTimeMulti = Optional.empty();
	}
}
