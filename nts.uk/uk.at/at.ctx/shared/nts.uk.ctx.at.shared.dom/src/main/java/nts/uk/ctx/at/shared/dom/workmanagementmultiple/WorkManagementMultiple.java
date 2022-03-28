package nts.uk.ctx.at.shared.dom.workmanagementmultiple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.shr.com.license.option.OptionLicense;

/**
 * 複数回勤務管理
 *
 */
@Getter
public class WorkManagementMultiple {

	/** 会社ID */
	String companyID;
	/** 使用区分 */
	UseATR useATR;

	/**
	 * Create domain from java data
	 * 
	 * @param companyID
	 * @param useATR
	 * @return
	 */
	public static WorkManagementMultiple createFromJavaType(String companyID, int useATR) {

		return new WorkManagementMultiple(companyID, EnumAdaptor.valueOf(useATR, UseATR.class));
	}

	/**
	 * [C-0] 複数回勤務管理
	 */
	public WorkManagementMultiple(String companyID, UseATR useATR) {
		super();
		this.companyID = companyID;
		this.useATR = useATR;
	}

	/**
	 * [1] 複数回勤務を利用するか
	 * 
	 */
	public boolean checkUseMultiWork(Require require) {
		// $アプリケーションコンテキスト
		OptionLicense license = require.getOptionLicense();

		// $アプリケーションコンテキスト．オプションライセンス．複数回・臨時勤務
		if (!license.attendance().multipleWork() || this.useATR == UseATR.notUse)
			return false;

		return true;
	}

	/**
	 * [2] 複数回勤務に対応す日次の勤怠項目を取得する
	 */
	public List<Integer> getDailyAttdItemsCorrespondMultiWork() {
		// 複数回勤務に対応する日次の勤怠項目
		return Arrays.asList(5, 6, 40, 41, 43, 44, 46, 47, 48, 49, 461, 598, 599, 600, 601, 602, 603, 610, 611, 612,
				613, 614, 615, 796, 797, 862, 864, 866, 868, 1127, 1128, 1129, 1130, 1135, 1136, 1137, 1138, 1152, 1153,
				1154, 1155, 1290, 1291);
	}

	/**
	 * [3] 複数回勤務に対応す月次の勤怠項目を取得する
	 */
	public List<Integer> getMonthAttdItemsCorrespondMultiWork() {
		// 複数回勤務に対応する月次の勤怠項目
		return Arrays.asList(1396);
	}

	/**
	 * [4] 利用できない日次の勤怠項目を取得する
	 */
	public List<Integer> getDailyAttendanceItems(Require require) {
		if (!this.checkUseMultiWork(require)) {
			return this.getDailyAttdItemsCorrespondMultiWork();
		}
		return new ArrayList<>();
	}

	/**
	 * [5] 利用できない月次の勤怠項目を取得する
	 *
	 */
	public List<Integer> getMonthlyAttendanceItems(Require require) {
		if (!this.checkUseMultiWork(require)) {
			return this.getMonthAttdItemsCorrespondMultiWork();
		}
		return new ArrayList<>();
	}

	/**
	 * [R-1] アプリケーションコンテキストを取得する
	 *
	 */
	public static interface Require {
		OptionLicense getOptionLicense();
	}

}
