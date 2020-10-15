package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 勤務時刻情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.Common.勤怠打刻.勤務時刻情報
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class WorkTimeInformation implements DomainObject {
	//時刻変更理由
	private ReasonTimeChange reasonTimeChange;

	@Setter
	//時刻
	private Optional<TimeWithDayAttr> timeWithDay;
	public WorkTimeInformation(ReasonTimeChange reasonTimeChange, TimeWithDayAttr timeWithDay) {
		super();
		this.reasonTimeChange = reasonTimeChange;
		this.timeWithDay = Optional.ofNullable(timeWithDay);
	}
	
	public void setReasonTimeChange(ReasonTimeChange reasonTimeChange) {
		this.reasonTimeChange = reasonTimeChange;
	}
	
	

}
