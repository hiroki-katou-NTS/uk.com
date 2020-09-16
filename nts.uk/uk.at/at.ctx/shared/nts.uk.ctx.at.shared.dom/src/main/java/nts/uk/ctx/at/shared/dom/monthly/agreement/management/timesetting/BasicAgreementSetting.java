package nts.uk.ctx.at.shared.dom.monthly.agreement.management.timesetting;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.upperset.AgreementMultiMonthAvg;

/**
 * ３６協定基本設定
 * @author nampt
 *
 */
@Getter
public class BasicAgreementSetting extends AggregateRoot {
	
	/** 1ヶ月 */
	private AgreementOneMonth oneMonth;
	/** 1年間 */
	private AgreementOneYear oneYear;
	/** 複数月平均 */
	private AgreementMultiMonthAvg multiMonth;
	/** 超過上限回数 */
	private AgreementOverMaxTimes overMaxTimes;

	public BasicAgreementSetting(AgreementOneMonth oneMonth, AgreementOneYear oneYear,
			AgreementMultiMonthAvg multiMonth, AgreementOverMaxTimes overMaxTimes) {
		super();
		this.oneMonth = oneMonth;
		this.oneYear = oneYear;
		this.multiMonth = multiMonth;
		this.overMaxTimes = overMaxTimes;
	}
}
