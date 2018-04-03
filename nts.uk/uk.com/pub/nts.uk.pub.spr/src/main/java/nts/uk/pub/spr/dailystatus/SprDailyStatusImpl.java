package nts.uk.pub.spr.dailystatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.spr.EmployeeSprPub;
import nts.uk.ctx.bs.employee.pub.spr.export.EmpSprExport;
import nts.uk.ctx.workflow.pub.spr.SprAppRootStatePub;
import nts.uk.ctx.workflow.pub.spr.export.AppRootStateStatusSprExport;
import nts.uk.pub.spr.dailystatus.output.DailyStatusSpr;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class SprDailyStatusImpl implements SprDailyStatusService {

	@Inject
	private EmployeeSprPub employeeSprPub;
	
	@Inject
	private SprAppRootStatePub sprAppRootStateService;
	
	@Override
	public List<DailyStatusSpr> getStatusOfDaily(String loginEmpCD, String employeeCD, String startDate,
			String endDate) {
		String companyID = "000000000000-0001";
		// アルゴリズム「パラメータチェック」を実行する
		this.checkParam(loginEmpCD, employeeCD, startDate, endDate);
		// （基幹・社員Export）アルゴリズム「「会社ID」「社員コード」より社員基本情報を取得」を実行する　RequestList No.18
		Optional<EmpSprExport> opEmployeeSpr = employeeSprPub.getEmployeeID(companyID, employeeCD);
		if(!opEmployeeSpr.isPresent()){
			throw new BusinessException("Msg_301");
		}
		// アルゴリズム「実績残業報告の有無」を実行する
		return getEmpDailyStatus(
				opEmployeeSpr.get().getEmployeeID(), 
				GeneralDate.fromString(startDate, "yyyy/mm/dd"), 
				GeneralDate.fromString(endDate, "yyyy/mm/dd"));
	}

	@Override
	public void checkParam(String loginEmpCD, String employeeCD, String startDate, String endDate) {
		// フォームデータ「ログイン社員コード(loginemployeeCode)」を取得する
		if(Strings.isBlank(loginEmpCD)){
			throw new BusinessException("Msg_999", "Msg_1026");
		}
		// ログイン社員コード(loginemployeeCode)をチェックする
		employeeSprPub.validateEmpCodeSpr(loginEmpCD);
		// フォームデータ「対象社員コード(employeeCode)」を取得する
		if(Strings.isBlank(employeeCD)){
			throw new BusinessException("Msg_1000", "Msg_1026");
		}
		// 対象社員コード(employeeCode)をチェックする
		employeeSprPub.validateEmpCodeSpr(employeeCD);
		// フォームデータ「取得期間の開始日(startdate)」を取得する
		if(Strings.isBlank(startDate)){
			throw new BusinessException("Msg_1001", "Msg_1026");
		}
		GeneralDate startD;
		// 取得期間の開始日(startdate)の形式をチェックする　日付型（yyyy/mm/dd）
		try {
			startD = GeneralDate.fromString(startDate, "yyyy/mm/dd");
		} catch (Exception e) {
			throw new BusinessException("Msg_1001", startDate);
		}
		// フォームデータ「取得期間の終了日(enddate)」を取得する
		if(Strings.isBlank(endDate)){
			throw new BusinessException("Msg_1002", "Msg_1026");
		}
		GeneralDate endD;
		// 取得期間の終了日(enddate)の形式をチェックする　日付型（yyyy/mm/dd）
		try {
			endD = GeneralDate.fromString(endDate, "yyyy/mm/dd");
		} catch (Exception e) {
			throw new BusinessException("Msg_1002", endDate);
		}
		// 取得期間の開始日、終了日の逆転チェック
		if(startD.after(endD)){
			throw new BusinessException("Msg_1003", startDate, endDate);
		}
		
	}

	@Override
	public List<DailyStatusSpr> getEmpDailyStatus(String employeeID, GeneralDate startDate, GeneralDate endDate) {
		List<DailyStatusSpr> resultList = new ArrayList<>();
		// 取得期間を日単位でループする（開始日～終了日）　MAX 31日
		for(GeneralDate loopDate = startDate; loopDate.beforeOrEquals(endDate); loopDate.addDays(1)){
			// 本人確認状況
			Integer status1 = this.getEmployeeStatus(loopDate, employeeID);
			// 上司承認状況
			Integer status2 = this.getEmployeeStatus(loopDate, employeeID);
			resultList.add(new DailyStatusSpr(loopDate, status1, status2));
		}
		return resultList;
	}

	@Override
	public Integer getEmployeeStatus(GeneralDate appDate, String employeeID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getManagerStatus(GeneralDate appDate, String employeeID) {
		List<AppRootStateStatusSprExport> appRootStateStatusSprList = sprAppRootStateService.getStatusByEmpAndDate(
				employeeID, 
				appDate, 
				appDate,
				1);
		
		return null;
	}

}
