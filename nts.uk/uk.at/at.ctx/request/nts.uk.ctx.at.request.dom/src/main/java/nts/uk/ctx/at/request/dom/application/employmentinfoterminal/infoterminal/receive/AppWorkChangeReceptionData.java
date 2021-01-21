package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * @author ThanhNX
 *
 *         勤務変更申請受信データ
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class AppWorkChangeReceptionData extends ApplicationReceptionData implements DomainValue {

	/**
	 * 事前事後
	 */
	private final String typeBeforeAfter;

	/**
	 * 申請年月日（開始）
	 */
	private final String startDate;

	/**
	 * 申請年月日（終了
	 */
	private final String endDate;

	/**
	 * 就業時間帯種類
	 */
	private final String workTime;

	/**
	 * 理由
	 */
	private final String reason;

	public AppWorkChangeReceptionData(ApplicationReceptionData appData, String typeBeforeAfter, String startDate,
			String endDate, String workTime, String reason) {
		super(appData.getIdNumber(), appData.getApplicationCategory(), appData.getYmd(), appData.getTime());
		this.typeBeforeAfter = typeBeforeAfter;
		this.startDate = startDate;
		this.endDate = endDate;
		this.workTime = workTime;
		this.reason = reason;
	}

}
