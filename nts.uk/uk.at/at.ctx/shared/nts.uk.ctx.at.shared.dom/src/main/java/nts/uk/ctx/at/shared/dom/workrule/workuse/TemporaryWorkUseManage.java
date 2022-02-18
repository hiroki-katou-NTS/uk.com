package nts.uk.ctx.at.shared.dom.workrule.workuse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.shr.com.license.option.OptionLicense;

/**
 * The domain temporary work use management 臨時勤務利用管理
 * 
 * @author HoangNDH
 *
 */
@Data
@Builder
public class TemporaryWorkUseManage {
	/** 会社ID */
	private CompanyId companyId;
	/** 使用区分 */
	private UseAtr useClassification;

	/**
	 * Creates domain from java type.
	 *
	 * @param companyId         the company id
	 * @param useClassification the use classification
	 * @return the temporary work use manage
	 */
	public static TemporaryWorkUseManage createFromJavaType(String companyId, int useClassification) {
		return new TemporaryWorkUseManage(new CompanyId(companyId),
				EnumAdaptor.valueOf(useClassification, UseAtr.class));
	}

	/**
	 * [C-0] 臨時勤務利用管理
	 * 
	 * @param companyId
	 * @param useClassification
	 */
	public TemporaryWorkUseManage(CompanyId companyId, UseAtr useClassification) {
		super();
		this.companyId = companyId;
		this.useClassification = useClassification;
	}

	/**
	 * [1] 臨時勤務を利用するか
	 * 
	 */
	public boolean checkUseTemporaryWork(Require require) {
		// $アプリケーションコンテキスト
		OptionLicense license = require.getOptionLicense();

		// $アプリケーションコンテキスト．オプションライセンス．複数回・臨時勤務
		if (!license.attendance().multipleWork() || this.useClassification == UseAtr.NOTUSE)
			return false;

		return true;
	}

	/**
	 * [2] 臨時勤務に対応す日次の勤怠項目を取得する
	 */
	public List<Integer> getDailyAttdItemsCorrespondTemporaryWork() {
		// 臨時勤務に対応する日次の勤怠項目
		return Arrays.asList(50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72,
				73, 616, 617, 618, 619, 620, 621, 622, 1236, 1237, 1238, 1239, 1240, 1241, 1242, 1243, 1244, 1245, 1246,
				1247);
	}
	
	/**
	 * [3] 臨時勤務に対応す月次の勤怠項目を取得する
	 */
	public List<Integer> getMonthAttdItemsCorrespondTemporaryWork() {
		// 臨時勤務に対応する月次の勤怠項目
		return Arrays.asList(1397, 1849, 1850, 1851, 1852, 1853, 1854, 2079);
	}
	
	/**
	 * [4] 利用できない日次の勤怠項目を取得する
	 */
	public List<Integer> getDailyAttendanceItems(Require require) {
		if (!this.checkUseTemporaryWork(require)) {
			return this.getDailyAttdItemsCorrespondTemporaryWork();
		}
		return new ArrayList<>();
	}
	
	/**
	 * [5] 利用できない月次の勤怠項目を取得する
	 *
	 */
	public List<Integer> getMonthlyAttendanceItems(Require require) {
		if (!this.checkUseTemporaryWork(require)) {
			return this.getMonthAttdItemsCorrespondTemporaryWork();
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
