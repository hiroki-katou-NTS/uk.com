package nts.uk.ctx.at.record.pub.workinformation.export;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainObject;

/**
 * 勤務時刻情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.Common.勤怠打刻.勤務時刻情報
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class WrWorkTimeInformationExport implements DomainObject {
	//時刻変更理由
	private WrReasonTimeChangeExport reasonTimeChange;

	@Setter
	//時刻 TimeWithDayAttr
	private Integer timeWithDay;
	public WrWorkTimeInformationExport(WrReasonTimeChangeExport reasonTimeChange, Integer timeWithDay) {
		super();
		this.reasonTimeChange = reasonTimeChange;
		this.timeWithDay = timeWithDay;
	}
	

}
