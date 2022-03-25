package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SEmpHistoryImport;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * ドメインサービス実装：代休を管理する年月日かどうかを判断する
 * @author shuichi_ishida
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CheckDateForManageCmpLeaveService {

	public static interface Require extends
		CompensatoryLeaveComSetting.Require, CompensatoryLeaveEmSetting.Require,
		SEmpHistoryImport.Require {
		
	}

	/**
	 * 判断する
	 * @param require Require
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param ymd 年月日
	 * @return 代休管理するかどうか
	 */
	public static boolean check(Require require, String companyId, String employeeId, GeneralDate ymd){
		boolean cmpStatus = checkCompanyStatus(require, companyId);
		if (!cmpStatus) return false;
		return checkEmployeeStatus(require, companyId, employeeId, ymd);
	}
	
	/**
	 * 会社単位の管理状態を確認する
	 * @param require Require
	 * @param companyId 会社ID
	 * @return 代休管理するかどうか
	 */
	private static boolean checkCompanyStatus(Require require, String companyId){
		Optional<CompensatoryLeaveComSetting> setting = require.compensatoryLeaveComSetting(companyId);
		if (!setting.isPresent()) return false;
		if (setting.get().getIsManaged() == ManageDistinct.NO) return false;
		return true;
	}
	
	/**
	 * 社員単位の管理状態を確認する
	 * @param require Require
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param ymd 年月日
	 * @return 代休管理するかどうか
	 */
	private static boolean checkEmployeeStatus(Require require, String companyId, String employeeId, GeneralDate ymd){
		Optional<SEmpHistoryImport> empHist = require.getSEmpHistoryImport(employeeId, ymd);
		if (!empHist.isPresent()) return false;
		Optional<CompensatoryLeaveEmSetting> setting = require.compensatoryLeaveEmSetting(companyId, empHist.get().getEmploymentCode());
		if (!setting.isPresent()) return true;
		if (setting.get().getIsManaged() == ManageDistinct.YES) return true;
		return false;
	}
}
