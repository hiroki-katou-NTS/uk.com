package nts.uk.ctx.at.request.pub.application.infoterminal;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * @author ThanhNX
 *
 *         遅刻早退取消申請受信データ
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class AppLateReceptionDataExport extends ApplicationReceptionDataExport implements DomainValue {

	/**
	 * 事前事後
	 */
	private final String typeBeforeAfter;

	/**
	 * 申請年月日
	 */
	private final String appYMD;

	/**
	 * 遅刻早退理由
	 */
	private final String reasonLeave;

	/**
	 * 理由
	 */
	private final String reason;

	public AppLateReceptionDataExport(ApplicationReceptionDataExport appData, String typeBeforeAfter, String appYMD,
			String reasonLeave, String reason) {
		super(appData.getIdNumber(), appData.getApplicationCategory(), appData.getYmd(), appData.getTime());
		this.typeBeforeAfter = typeBeforeAfter;
		this.appYMD = appYMD;
		this.reasonLeave = reasonLeave;
		this.reason = reason;
	}

}
