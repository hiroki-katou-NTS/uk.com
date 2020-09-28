package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;

@Getter
/** 集計枠に紐づく対象 */
public abstract class TargetLinkAggregateFrame implements DomainObject {

	/** 集計対象か (確認値: 日別勤怠の応援作業時間帯) */
	public abstract boolean canAggregate(OuenWorkTimeSheetOfDailyAttendance target); 
	
	/** 集計グループコードを取得する (): String */
	public abstract String aggregateGroupCode();
}
