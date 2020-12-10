package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * UKDesign.UniversalK.就業.KDM_残数管理 (Quản lý số dư).KDM001_残数管理データの登録 (Đăng ký dữ liệu quản lý số dư).Ａ：振休管理.アルゴリズム.Ａ：振休管理データ抽出処理.Ａ：振休管理データ抽出処理
 */
@Getter
@Setter
@AllArgsConstructor
public class RemainingNumberDataInformation {
	/**
	 * 使用期限日数
	 */
	private double expirationDate;
	/**
	 * 使用期限時間数
	 */
	private int expirationDateHours;
	/**
	 * 残数日数
	 */
	private double numberOfDaysLeft;
	/**
	 * 期限日
	 */
	Optional<GeneralDate> deadLine;
	/**
	 * 残数時間数
	 */
	Optional<Integer> remainingHours;
	/**
	 * 法定区分
	 */
	Optional<Integer> legalClassification;
	/**
	 * 消化日
	 */
	Optional<GeneralDate> digestionDate;
	/**
	 * 消化日数
	 */
	Optional<Double> digestionDays;
	/**
	 * 消化時間数
	 */
	Optional<Integer> digestionTime;
	/**
	 * 消化管理データID
	 */
	Optional<Character> digestionManagementDataId; 
	/**
	 * 発生日
	 */
	Optional<GeneralDate> accrualDate;
	/**
	 * 発生日数
	 */
	Optional<Double> numberOfDaysOfOccurrence;
	/**
	 * 発生時間数
	 */
	Optional<Integer> occurrenceTime;
	/**
	 * 発生管理データID
	 */
	Optional<Character> occurrenceManagementDataId;
}
