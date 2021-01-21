package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.remarks;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainObject;

/**
 * 日別勤怠の備考
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.備考.日別勤怠の備考
 * @author tutk
 *
 */
@Getter
public class RemarksOfDailyAttd implements DomainObject{
	/** 備考: 日別実績の備考 */
	@Setter
	private RecordRemarks remarks;
	
	/** 備考欄NO: int */
	private int remarkNo;

	public RemarksOfDailyAttd(RecordRemarks remarks, int remarkNo) {
		super();
		this.remarks = remarks;
		this.remarkNo = remarkNo;
	}
	
}
