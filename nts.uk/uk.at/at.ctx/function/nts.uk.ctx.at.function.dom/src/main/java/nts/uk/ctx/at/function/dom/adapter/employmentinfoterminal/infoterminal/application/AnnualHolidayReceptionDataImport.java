package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * @author ThanhNX
 *
 *         遅刻早退理由
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class AnnualHolidayReceptionDataImport extends ApplicationReceptionDataImport implements DomainValue {

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

	public AnnualHolidayReceptionDataImport(ApplicationReceptionDataImport appData, String annualHolidayType,
			String annualHolidayTime, String typeBeforeAfter, String appYMD, String reason) {
		super(appData.getIdNumber(), appData.getApplicationCategory(), appData.getYmd(), appData.getTime());
		this.annualHolidayType = annualHolidayType;
		this.annualHolidayTime = annualHolidayTime;
		this.typeBeforeAfter = typeBeforeAfter;
		this.appYMD = appYMD;
		this.reason = reason;
	}

}
