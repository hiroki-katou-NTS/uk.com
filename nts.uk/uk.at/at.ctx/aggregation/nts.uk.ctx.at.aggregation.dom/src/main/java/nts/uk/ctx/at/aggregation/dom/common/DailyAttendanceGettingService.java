package nts.uk.ctx.at.aggregation.dom.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * 日別勤怠を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.共通処理.日別勤怠を取得する
 * @author kumiko_otake
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DailyAttendanceGettingService {

	/**
	 * 取得する
	 * @param require require
	 * @param empIds 社員IDリスト
	 * @param period 期間
	 * @param target 取得対象
	 * @return
	 */
	public static Map<ScheRecGettingAtr, List<IntegrationOfDaily>> get(Require require
			, List<EmployeeId> empIds, DatePeriod period, ScheRecGettingAtr target
	) {

		val results = new HashMap<ScheRecGettingAtr, List<IntegrationOfDaily>>();

		// 予定
		if ( target.isNeedSchedule() ) {
			results.put( ScheRecGettingAtr.ONLY_SCHEDULE
					, DailyAttendanceGettingService.getSchedule(require, empIds, period) );
		}

		// 実績
		if ( target.isNeedRecord() ) {
			results.put( ScheRecGettingAtr.ONLY_RECORD
					, DailyAttendanceGettingService.getRecord(require, empIds, period) );
		}

		// 予定＋実績
		if ( target == ScheRecGettingAtr.SCHEDULE_WITH_RECORD ) {
			val merged = DailyAttendanceMergingService.mergeToFlatList(empIds, period
								, results.get( ScheRecGettingAtr.ONLY_SCHEDULE )
								, results.get( ScheRecGettingAtr.ONLY_RECORD )
							);
			results.put( ScheRecGettingAtr.SCHEDULE_WITH_RECORD, merged );
		}

		return results;

	}

	/**
	 * 予定を取得する
	 * @param require require
	 * @param empIds 社員IDリスト
	 * @param period 期間
	 * @return 勤務予定リスト
	 */
	public static List<IntegrationOfDaily> getSchedule(Require require, List<EmployeeId> empIds, DatePeriod period) {
		// 勤務予定を取得
		return require.getSchduleList(empIds, period);
	}

	/**
	 * 実績を取得する
	 * @param require require
	 * @param empIds 社員IDリスト
	 * @param period 期間
	 * @return 勤務予定リスト
	 */
	public static List<IntegrationOfDaily> getRecord(Require require, List<EmployeeId> empIds, DatePeriod period) {

		// 開始日～前日までの日別実績を取得
		if( period.start().afterOrEquals( GeneralDate.today() ) ) {
			return Collections.emptyList();
		}

		val recPeriod = ( period.end().before( GeneralDate.today() ) )
				? period : new DatePeriod( period.start(), GeneralDate.today().decrease() );

		return require.getRecordList(empIds, recPeriod);

	}


	public static interface Require {

		/**
		 * 勤務予定を取得する
		 * @param empIds 社員IDリスト
		 * @param period 期間
		 * @return 勤務予定リスト
		 */
		public List<IntegrationOfDaily> getSchduleList( List<EmployeeId> empIds, DatePeriod period );

		/**
		 * 日別実績を取得する
		 * @param empIds 社員IDリスト
		 * @param period 期間
		 * @return 日別実績リスト
		 */
		public List<IntegrationOfDaily> getRecordList( List<EmployeeId> empIds, DatePeriod period );

	}

}
