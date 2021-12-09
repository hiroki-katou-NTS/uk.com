package nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanDuplication;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSetting;

/**
 * 応援可能な社員を登録できるかチェックする
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.応援管理.応援可能な社員.応援可能な社員を登録できるかチェックする
 * @author kumiko_otake
 */
public class SupportableEmployeeCheckService {

	/**
	 * チェックする
	 * @param require Require
	 * @param target チェック対象
	 * @return チェック結果
	 */
	public static CheckResult isRegistrable(Require require, SupportableEmployee target) {

		// 登録済みの内容を取得する
		val registered = require.getByPeriod( target.getEmployeeId(), target.getPeriod()).stream()
				.filter( e -> !e.getId().equals( target.getId() ) )
				.collect(Collectors.toList());


		// 応援同士の整合性をチェックする
		switch( target.getSupportType() ) {
			case ALLDAY: {		// チェック対象: 終日応援

				if ( !registered.isEmpty() ) {
					// 登録不可: 期間中に応援可能な社員として登録されている
					return CheckResult.DUPLICATED_PERIOD;
				}

			} break;
			case TIMEZONE: {	// チェック対象: 時間帯応援

				if ( registered.stream().anyMatch( e -> e.getSupportType() == SupportType.ALLDAY ) ) {
					// 登録不可: 期間中に『終日』応援可能な社員として登録されている
					return CheckResult.DUPLICATED_PERIOD;
				}

				if ( registered.stream().anyMatch( e -> e.getTimespan().get().checkDuplication( target.getTimespan().get() ) != TimeSpanDuplication.NOT_DUPLICATE )) {
					// 登録不可: 時間帯が重複している
					return CheckResult.DUPLICATED_TIMEZONE;
				}


				if ( require.getSetting().getMaxNumberOfSupportOfDay().v() <= registered.size() ) {
					// 登録不可: すでに応援回数の上限に達している
					return CheckResult.UPPER_LIMIT;
				}

			} break;
			default: throw new RuntimeException("OutOfRange: SupportType");
		}


		return CheckResult.REGISTABLE;

	}



	/**
	 * チェック結果
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.応援管理.応援可能な社員.応援可能な社員を登録できるかチェックする.チェック結果
	 * @author kumiko_otake
	 */
	@Getter
	@AllArgsConstructor
	public enum CheckResult {

		/** 登録可能 **/
		REGISTABLE( 0 ),
		/** 期間が重複している **/
		DUPLICATED_PERIOD( 1 ),
		/** 時間帯が重複している **/
		DUPLICATED_TIMEZONE( 2 ),
		/** 応援可能な回数が上限に達している **/
		UPPER_LIMIT( 3 ),
		;

		private final int value;

	}



	public interface Require {

		/**
		 * 期間を指定して取得する
		 * @param employeeId 社員ID
		 * @param period 期間
		 * @return 応援可能な社員(List)
		 */
		public List<SupportableEmployee> getByPeriod(EmployeeId employeeId, DatePeriod period);
		/**
		 * 応援の設定を取得する
		 * @return 応援運用利用設定
		 */
		public SupportOperationSetting getSetting();

	}

}
