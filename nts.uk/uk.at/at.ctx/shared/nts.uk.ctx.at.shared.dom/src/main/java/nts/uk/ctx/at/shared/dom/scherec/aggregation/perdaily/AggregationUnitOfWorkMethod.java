package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;

/**
 * 勤務方法の集計単位
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).集計処理.日単位集計.勤務方法の集計単位
 * @author dan_pv
 *
 */
@Getter
@RequiredArgsConstructor
public enum AggregationUnitOfWorkMethod {

	/** 就業時間帯 **/
	WORK_TIME( 0 ),
	/** シフト**/
	SHIFT( 1 );

	private final int value;
	
	/**
	 * 勤務方法を取得する
	 * @param require
	 * @param workInfo 勤務情報
	 * @return
	 */
	public Optional<String> getWorkMethod(
			Require require,
			WorkInfoOfDailyAttendance workInfo) {
		
		switch ( this ) {
		case WORK_TIME:
			val workTimeCode = workInfo.getRecordInfo().getWorkTimeCodeNotNull();
			return workTimeCode.isPresent() ? 
					Optional.of( workTimeCode.get().v() ) : 
					Optional.empty();
		case SHIFT:
			val shiftMaster = require.getShiftMaster( workInfo.getRecordInfo() );
			return shiftMaster.isPresent() ?
					Optional.of( shiftMaster.get().getShiftMasterCode().v() ) :
					Optional.empty();
		default:
			throw new RuntimeException("Enum value is invalid");
		}
	}

	public static interface Require {
		
		/**
		 * シフトマスタを取得する
		 * @param workInformation 勤務情報
		 * @return
		 */
		Optional<ShiftMaster> getShiftMaster(WorkInformation workInformation);
		
	}
	
}
