package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkGroup;

@Getter
/** 集計枠対象の職場 */
public class AggregateFrameTargetWork extends TargetLinkAggregateFrame {

	private static WorkCode DEFAULT_WORKCODE = new WorkCode("-1");
	
	/** 作業グループコード: 作業グループコード */
	private WorkGroupCode workGroupCD;
	
	/**  対象作業: 作業グループ */
	private WorkGroup workTarget;

	private AggregateFrameTargetWork(WorkGroupCode workGroupCD, WorkGroup workTarget) {
		super();
		this.workGroupCD = workGroupCD;
		this.workTarget = workTarget;
	}
	
	public static AggregateFrameTargetWork create(WorkGroupCode workGroupCD, WorkGroup workTarget) {
		return new AggregateFrameTargetWork(workGroupCD, workTarget);
	}
	
	public static AggregateFrameTargetWork create(String workGroupCD, String workCd1, String workCd2, 
			String workCd3, String workCd4, String workCd5) {
		
		return new AggregateFrameTargetWork(new WorkGroupCode(workGroupCD), 
											WorkGroup.create(workCd1, workCd2, workCd3, workCd4, workCd5));
	}
	
	@Override
	/** 集計対象か (確認値: 日別勤怠の応援作業時間帯): boolean */
	public boolean canAggregate(OuenWorkTimeSheetOfDailyAttendance target) {
		
		return target.getWorkContent().getWork()
				.map(t -> {
					return t.getWorkCD1().equals(this.workTarget.getWorkCD1())
							&& t.getWorkCD2().orElse(DEFAULT_WORKCODE).equals(this.workTarget.getWorkCD2().orElse(DEFAULT_WORKCODE))
							&& t.getWorkCD3().orElse(DEFAULT_WORKCODE).equals(this.workTarget.getWorkCD3().orElse(DEFAULT_WORKCODE))
							&& t.getWorkCD4().orElse(DEFAULT_WORKCODE).equals(this.workTarget.getWorkCD4().orElse(DEFAULT_WORKCODE))
							&& t.getWorkCD5().orElse(DEFAULT_WORKCODE).equals(this.workTarget.getWorkCD5().orElse(DEFAULT_WORKCODE)); 
				})
				.orElse(false);
	}

	@Override
	/** 集計グループコードを取得する (): String */
	public String aggregateGroupCode() {
		return this.workGroupCD.v();
	}
	
	/** 同一作業階層数を確認する (対象作業: 作業グループ): int */
	public int sameWorkLayerNumber(WorkGroup target) {

		return internalCheck(target, 0);
	}
	
	private int internalCheck(WorkGroup target, int layer) {
		
		if (isSame(getWorkCD(this.workTarget, layer), getWorkCD(target, layer))) {
			layer++;
			
			return internalCheck(target, layer);
		}
		
		return layer;
	}
	
	private WorkCode getWorkCD(WorkGroup source, int order) {
		switch (order) {
		case 0:
			return source.getWorkCD1();
		case 1:
			return source.getWorkCD2().orElse(DEFAULT_WORKCODE);
		case 2:
			return source.getWorkCD3().orElse(DEFAULT_WORKCODE);
		case 3:
			return source.getWorkCD4().orElse(DEFAULT_WORKCODE);
		case 4:
			return source.getWorkCD5().orElse(DEFAULT_WORKCODE);
		default:
			return DEFAULT_WORKCODE;
		}
	}
	
	private boolean isSame(WorkCode source, WorkCode target) {
		if (target.equals(DEFAULT_WORKCODE)) {
			return false;
		}
		
		return source.equals(target);
	}
}
