package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.gul.util.OptionalUtil;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * 日別勤怠グループ化Utility
 * @author kumiko_otake
 */
public class DailyAttendanceGroupingUtil {

	/** Mapper: 日別勤怠(Work) **/
	private static final Function<IntegrationOfDaily, IntegrationOfDaily> defMapper = e -> e;
	/** Mapper: 社員ID **/
	private static final Function<IntegrationOfDaily, EmployeeId> empIdMapper = e -> new EmployeeId( e.getEmployeeId() );
	/** Mapper: 年月日 **/
	private static final Function<IntegrationOfDaily, GeneralDate> ymdMapper = IntegrationOfDaily::getYmd;


	/**
	 * 社員IDでグループ化する
	 * @param dlyAtdList 日別勤怠リスト
	 * @return
	 */
	public static Map<EmployeeId, List<IntegrationOfDaily>> byEmployeeId(List<IntegrationOfDaily> dlyAtdList) {
		return DailyAttendanceGroupingUtil.groupingBy( dlyAtdList, empIdMapper, defMapper );
	}

	/**
	 * 社員IDでグループ化して任意の項目を取得する
	 * @param dlyAtdList 日別勤怠リスト
	 * @param mapper valueに割り当てたい項目の取得処理
	 * @return
	 */
	public static <T> Map<EmployeeId, List<T>> byEmployeeIdWithAnyItem(List<IntegrationOfDaily> dlyAtdList, Function<IntegrationOfDaily, T> mapper) {
		return DailyAttendanceGroupingUtil.groupingBy( dlyAtdList, empIdMapper, mapper );
	}

	/**
	 * 社員IDでグループ化してEmptyの項目を除外する
	 * @param dlyAtdList 日別勤怠リスト
	 * @param mapper valueに割り当てたい項目の取得処理
	 * @return
	 */
	public static <T> Map<EmployeeId, List<T>> byEmployeeIdWithoutEmpty(List<IntegrationOfDaily> dlyAtdList, Function<IntegrationOfDaily, Optional<T>> mapper) {
		return DailyAttendanceGroupingUtil.groupingAndTruncate( dlyAtdList, empIdMapper, mapper );
	}


	/**
	 * 年月日でグループ化する
	 * @param dlyAtdList 日別勤怠リスト
	 * @return
	 */
	public static Map<GeneralDate, List<IntegrationOfDaily>> byDate(List<IntegrationOfDaily> dlyAtdList) {
		return DailyAttendanceGroupingUtil.groupingBy( dlyAtdList, ymdMapper, defMapper );
	}

	/**
	 * 年月日でグループ化して任意の項目を取得する
	 * @param dlyAtdList 日別勤怠リスト
	 * @param mapper valueに割り当てたい項目の取得処理
	 * @return
	 */
	public static <T> Map<GeneralDate, List<T>> byDateWithAnyItem(List<IntegrationOfDaily> dlyAtdList, Function<IntegrationOfDaily, T> mapper) {
		return DailyAttendanceGroupingUtil.groupingBy( dlyAtdList, ymdMapper, mapper );
	}

	/**
	 * 年月日でグループ化してEmptyの項目を除外する
	 * @param dlyAtdList 日別勤怠リスト
	 * @param mapper valueに割り当てたい項目の取得処理
	 * @return
	 */
	public static <T> Map<GeneralDate, List<T>> byDateWithoutEmpty(List<IntegrationOfDaily> dlyAtdList, Function<IntegrationOfDaily, Optional<T>> mapper) {
		return DailyAttendanceGroupingUtil.groupingAndTruncate( dlyAtdList, ymdMapper, mapper );
	}


	/**
	 * グループ化する
	 * @param dlyAtdList 日別勤怠リスト
	 * @param keyMapper keyとしたい項目の取得関数
	 * @param valueMapper valueに割り当てたい項目の取得処理
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	private static <K, V> Map<K, List<V>> groupingBy(List<IntegrationOfDaily> dlyAtdList
			, Function<IntegrationOfDaily, K> keyMapper
			, Function<IntegrationOfDaily, V> valueMapper
	) {

		return dlyAtdList.stream()
				.collect(Collectors.groupingBy( keyMapper, Collectors.mapping( valueMapper, Collectors.toList() ) ));
	}

	/**
	 * グループ化してEmptyを除外する
	 * @param dlyAtdList 日別勤怠リスト
	 * @param keyMapper keyとしたい項目の取得関数
	 * @param valueMapper valueに割り当てたい項目の取得処理
	 * @return
	 */
	private static <K, V> Map<K, List<V>> groupingAndTruncate(List<IntegrationOfDaily> dlyAtdList
			, Function<IntegrationOfDaily, K> keyMapper
			, Function<IntegrationOfDaily, Optional<V>> valueMapper
	) {

		return dlyAtdList.stream()
				.collect(Collectors.groupingBy(
							keyMapper
						,	Collectors.collectingAndThen(
									Collectors.toList()
								, 	e -> e.stream().map(valueMapper).flatMap(OptionalUtil::stream).collect(Collectors.toList()) )
					));

	}

}
