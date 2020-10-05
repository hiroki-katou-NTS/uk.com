package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;

@Getter
/** 集計枠対象の場所 */
public class AggregateFrameTargetWorkLocation extends TargetLinkAggregateFrame {

	/** 場所コード: 勤務場所コード */
	private WorkLocationCD workLocationCD;
	
	private AggregateFrameTargetWorkLocation(WorkLocationCD workLocationCD) {
		this.workLocationCD = workLocationCD;
	}

	@Override
	/** 集計対象か (確認値: 日別勤怠の応援作業時間帯): boolean */
	public boolean canAggregate(OuenWorkTimeSheetOfDailyAttendance target) {
		return workLocationCD.equals(target.getWorkContent().getWorkplace().getWorkLocationCD());
	}

	@Override
	/** 集計グループコードを取得する (): String */
	public String aggregateGroupCode() {
		return workLocationCD.v();
	}

	public static AggregateFrameTargetWorkLocation create(String workLocationCD) {
		
		return AggregateFrameTargetWorkLocation.create(new WorkLocationCD(workLocationCD));
	}

	public static AggregateFrameTargetWorkLocation create(WorkLocationCD workLocationCD) {
		
		return new AggregateFrameTargetWorkLocation(workLocationCD);
	}
}
