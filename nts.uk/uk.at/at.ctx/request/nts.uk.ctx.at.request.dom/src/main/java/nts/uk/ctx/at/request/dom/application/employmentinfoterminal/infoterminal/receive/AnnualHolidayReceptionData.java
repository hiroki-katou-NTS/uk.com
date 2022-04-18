package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * @author ThanhNX
 *
 *         時間年休申請受信データ
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class AnnualHolidayReceptionData extends ApplicationReceptionData implements DomainValue {

	/**
	 * 年休種類
	 */
	private final String annualHolidayType;

	/**
	 * 年休時間
	 */
	private final String annualHolidayTime;

	/**
	 * 事前事後
	 */
	private final String typeBeforeAfter;

	/**
	 * 申請年月日
	 */
	private final String appYMD;

	/**
	 * 理由
	 */
	private final String reason;

	public AnnualHolidayReceptionData(ApplicationReceptionData appData, String annualHolidayType,
			String annualHolidayTime, String typeBeforeAfter, String appYMD, String reason) {
		super(appData.getIdNumber(), appData.getApplicationCategory(), appData.getYmd(), appData.getTime());
		this.annualHolidayType = annualHolidayType;
		this.annualHolidayTime = annualHolidayTime;
		this.typeBeforeAfter = typeBeforeAfter;
		this.appYMD = appYMD;
		this.reason = reason;
	}

}
