package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;

@Getter
/** 集計枠対象の職場 */
public class AggregateFrameTargetWorkplace extends TargetLinkAggregateFrame {

	/** 職場ID: 職場ID */
	private String workplaceID;
	
	private AggregateFrameTargetWorkplace(String workplaceId) {
		this.workplaceID = workplaceId;
	}

	@Override
	/** 集計対象か (確認値: 日別勤怠の応援作業時間帯): boolean */
	public boolean canAggregate(OuenWorkTimeSheetOfDailyAttendance target) {
		return this.workplaceID.equals(target.getWorkContent().getWorkplace().getWorkplaceId());
	}

	@Override
	/** 集計グループコードを取得する (): String */
	public String aggregateGroupCode() {
		return workplaceID;
	}
	
	public static AggregateFrameTargetWorkplace create(String workplaceId) {
		
		return new AggregateFrameTargetWorkplace(workplaceId);
	}

}
