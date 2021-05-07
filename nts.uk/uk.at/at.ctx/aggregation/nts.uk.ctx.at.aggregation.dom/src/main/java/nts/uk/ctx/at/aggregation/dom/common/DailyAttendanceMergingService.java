package nts.uk.ctx.at.aggregation.dom.common;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.util.OptionalUtil;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.DailyAttendanceGroupingUtil;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * 予定と実績をマージする
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.共通処理.予定と実績をマージする
 * @author kumiko_otake
 */
@Stateless
public class DailyAttendanceMergingService {

	/**
	 * 社員と年月日ごとにマージする
	 * @param empIds 社員IDリスト
	 * @param period 期間
	 * @param schduleList 予定リスト
	 * @param recordList 実績リスト
	 * @return マージされた日別勤怠リスト
	 */
	public static Map<EmployeeId, Map<GeneralDate, Optional<IntegrationOfDaily>>> mergeToDateMapByEmployee(
				List<EmployeeId> empIds, DatePeriod period
			,	List<IntegrationOfDaily> scheduleList, List<IntegrationOfDaily> recordList
	) {

		// 予実の優先度を判定する
		val results = DailyAttendanceMergingService.getPriorities(empIds, period
								, DailyAttendanceGroupingUtil.byEmployeeIdWithAnyItem( scheduleList, IntegrationOfDaily::getYmd )
								, DailyAttendanceGroupingUtil.byEmployeeIdWithAnyItem( recordList, IntegrationOfDaily::getYmd )
							);

		// 日別勤怠をマッピングする
		return DailyAttendanceMergingService.mapping(
									results
								,	DailyAttendanceGroupingUtil.byEmployeeId( scheduleList )
								,	DailyAttendanceGroupingUtil.byEmployeeId( recordList )
							);

	}

	/**
	 * 社員ごとにマージする
	 * @param empIds 社員IDリスト
	 * @param period 期間
	 * @param schduleList 予定リスト
	 * @param recordList 実績リスト
	 * @return マージされた日別勤怠リスト
	 */
	public static Map<EmployeeId, List<IntegrationOfDaily>> mergeToListByEmployee(
				List<EmployeeId> empIds, DatePeriod period
			,	List<IntegrationOfDaily> scheduleList, List<IntegrationOfDaily> recordList
	) {

		return DailyAttendanceMergingService.mergeToDateMapByEmployee( empIds, period, scheduleList, recordList )
					.entrySet().stream()
						.collect(Collectors.toMap( Map.Entry::getKey, byEmpId -> {
							return byEmpId.getValue().entrySet().stream()
								.map(Map.Entry::getValue)
								.flatMap(OptionalUtil::stream)
								.collect(Collectors.toList());
						} ));

	}

	/**
	 * マージする
	 * @param empIds 社員IDリスト
	 * @param period 期間
	 * @param schduleList 予定リスト
	 * @param recordList 実績リスト
	 * @return マージされた日別勤怠リスト
	 */
	public static List<IntegrationOfDaily> mergeToFlatList(
				List<EmployeeId> empIds, DatePeriod period
			,	List<IntegrationOfDaily> scheduleList, List<IntegrationOfDaily> recordList
	) {

		return DailyAttendanceMergingService.mergeToListByEmployee( empIds, period, scheduleList, recordList )
					.values().stream()
						.flatMap( List::stream )
						.collect(Collectors.toList());

	}


	/**
	 * 予実の優先度を判定する
	 * @param empIds 社員IDリスト
	 * @param period 期間
	 * @param scheDays 予定がある日リスト
	 * @param existsRecDays 実績がある日リスト
	 * @return
	 */
	private static Map<EmployeeId, Map<GeneralDate, Optional<ScheRecAtr>>> getPriorities(
				List<EmployeeId> empIds, DatePeriod period
			,	Map<EmployeeId, List<GeneralDate>> scheDays, Map<EmployeeId, List<GeneralDate>> recDays
	) {

		return empIds.stream()
				.collect(Collectors.toMap( empId -> empId
						, empId -> {
							return period.stream()
								.collect(Collectors.toMap( day -> day
										, day -> {
											return DailyAttendanceMergingService.getPriorityByDay( day
													, scheDays.containsKey( empId ) ? scheDays.get(empId).contains(day) : false
													, recDays.containsKey( empId ) ? recDays.get(empId).contains(day) : false );
										} ));
						} ));

	}


	/**
	 * 年月日単位で優先度を判定する
	 * @param ymd 年月日
	 * @param existsSchedule 予定が存在するか
	 * @param existsRecord 実績が存在するか
	 * @return 優先する予実
	 */
	private static Optional<ScheRecAtr> getPriorityByDay(GeneralDate ymd, boolean existsSchedule, boolean existsRecord) {

		// システム日付より前は実績を優先
		if( ymd.before( GeneralDate.today() ) && existsRecord ) {
			return Optional.of( ScheRecAtr.RECORD );
		}

		// 予定があれば予定を返す
		return (existsSchedule)
					? Optional.of( ScheRecAtr.SCHEDULE )
					: Optional.empty();

	}

	/**
	 * 日別勤怠をマッピングする
	 * @param results 判定結果
	 * @param schduleList 社員別予定リスト
	 * @param recordList 社員別実績リスト
	 * @return マッピング結果(社員・年月日別)
	 */
	private static Map<EmployeeId, Map<GeneralDate, Optional<IntegrationOfDaily>>> mapping(
			Map<EmployeeId, Map<GeneralDate, Optional<ScheRecAtr>>> results
		,	Map<EmployeeId, List<IntegrationOfDaily>> scheListByEmp
		,	Map<EmployeeId, List<IntegrationOfDaily>> recListByEmp
	) {

		return results.entrySet().stream().collect(Collectors.toMap(
				Map.Entry::getKey
			,	byEmp -> {
					return byEmp.getValue().entrySet().stream().collect(Collectors.toMap(
							Map.Entry::getKey
						,	byYmd -> DailyAttendanceMergingService.getMappingTarget( byYmd
												, scheListByEmp.get( byEmp.getKey() )
												, recListByEmp.get( byEmp.getKey() ) )
					));
				}
			));

	}

	/**
	 * マッピング対象の日別勤怠を取得する
	 * @param scheRecAtrByYmd 年月日ごとの予実区分
	 * @param scheList 予定リスト
	 * @param recList 実績リスト
	 * @return マッピング対象の日別勤怠
	 */
	private static Optional<IntegrationOfDaily> getMappingTarget(
				Map.Entry<GeneralDate, Optional<ScheRecAtr>> scheRecAtrByYmd
			,	List<IntegrationOfDaily> scheList, List<IntegrationOfDaily> recList
	) {

		return scheRecAtrByYmd.getValue().flatMap( scheRecAtr -> {
			val dlyAtdList = ( scheRecAtr == ScheRecAtr.RECORD ) ? recList : scheList;
			return dlyAtdList.stream()
					.filter( e -> e.getYmd().equals( scheRecAtrByYmd.getKey() ) )
					.findFirst();
		} );

	}

}
