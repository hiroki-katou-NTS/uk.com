package nts.uk.pub.spr.login.paramcheck;

import java.util.Optional;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.spr.EmployeeSprPub;
import nts.uk.ctx.bs.employee.pub.spr.export.EmpSprExport;
import nts.uk.shr.com.time.AttendanceClock;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class LoginParamCheckImpl implements LoginParamCheck {

	private static final String DATE_FORMAT1 = "yyyy/MM/dd";
	
	private static final String DATE_FORMAT2 = "yyyy/MM/d";
	
	private static final String DATE_FORMAT3 = "yyyy/M/dd";
	
	private static final String DATE_FORMAT4 = "yyyy/M/d";
	
	@Inject
	private EmployeeSprPub employeeSprPub;
	
	@Override
	public String checkParamPreApp(String employeeCD, String startTime, String date, String reason) {
		// 契約コード固定：　000000000000
		// 会社コード固定：　0001
		// 会社ID固定：　000000000000-0001
		String companyID = "000000000000-0001";
		// フォームデータ「対象社員コード(employeeCode)」を取得する
		if(Strings.isBlank(employeeCD)){
			throw new BusinessException("Msg_1000", "Msg_1026");
		}
		// 対象社員コード(employeeCode)をチェックする
		employeeSprPub.validateEmpCodeSpr(employeeCD.trim());
		// （基幹・社員Export）アルゴリズム「「会社ID」「社員コード」より社員基本情報を取得」を実行する　RequestList No.18
		Optional<EmpSprExport> opEmployeeSpr = employeeSprPub.getEmployeeID(companyID, employeeCD.trim());
		if(!opEmployeeSpr.isPresent()){
			throw new BusinessException("Msg_1000", "Msg_1027");
		}
		// フォームデータ「対象日(date)」を取得する
		if(Strings.isBlank(date)){
			throw new BusinessException("Msg_1009", "Msg_1026");
		}
		// 対象日(date)の形式をチェックする　日付型
		if(this.getDate(date)==null){
			throw new BusinessException("Msg_1009", date);
		}
		// フォームデータ「出勤時刻(starttime)」を取得する
		if(startTime==null){
			throw new BusinessException("Msg_1012", "Msg_1026");
		}
		if(Strings.isNotBlank(startTime)){
			// 出勤時刻(starttime)をチェックする
			try {
				Integer startTimeValue = Integer.valueOf(startTime);
				new AttendanceClock(startTimeValue);
			} catch (Exception e) {
				throw new BusinessException("Msg_1012", startTime.toString());
			}
		}
		// フォームデータ「申請理由(reason)」を取得する　※仕様追加　2018/03/28
		return opEmployeeSpr.get().getEmployeeID();
	}

	@Override
	public String checkParamOvertime(String employeeCD, String endTime, String date, String reason) {
		// 契約コード固定：　000000000000
		// 会社コード固定：　0001
		// 会社ID固定：　000000000000-0001
		String companyID = "000000000000-0001";
		// フォームデータ「対象社員コード(employeeCode)」を取得する
		if(Strings.isBlank(employeeCD)){
			throw new BusinessException("Msg_1000", "Msg_1026");
		}
		// 対象社員コード(employeeCode)をチェックする
		employeeSprPub.validateEmpCodeSpr(employeeCD.trim());
		// （基幹・社員Export）アルゴリズム「「会社ID」「社員コード」より社員基本情報を取得」を実行する　RequestList No.18
		Optional<EmpSprExport> opEmployeeSpr = employeeSprPub.getEmployeeID(companyID, employeeCD.trim());
		if(!opEmployeeSpr.isPresent()){
			throw new BusinessException("Msg_1000", "Msg_1027");
		}
		// フォームデータ「対象日(date)」を取得する
		if(Strings.isBlank(date)){
			throw new BusinessException("Msg_1009", "Msg_1026");
		}
		// 対象日(date)の形式をチェックする　日付型
		if(this.getDate(date)==null){
			throw new BusinessException("Msg_1009", date);
		}
		// フォームデータ「退勤時刻(endtime)」を取得する
		if(endTime==null){
			throw new BusinessException("Msg_1013", "Msg_1026");
		}
		if(Strings.isNotBlank(endTime)){
			// 退勤時刻(endtime)をチェックする
			try {
				Integer endTimeValue = Integer.valueOf(endTime);
				new AttendanceClock(endTimeValue);
			} catch (Exception e) {
				throw new BusinessException("Msg_1013", endTime.toString());
			}
		}
		// フォームデータ「申請理由(reason)」を取得する　※仕様追加　2018/03/28
		return opEmployeeSpr.get().getEmployeeID();
	}

	@Override
	public String checkParamAdjustDaily(String employeeCD, String startTime, String endTime, String date, String reason, String stampFlg) {
		// 契約コード固定：　000000000000
		// 会社コード固定：　0001
		// 会社ID固定：　000000000000-0001
		String companyID = "000000000000-0001";
		// フォームデータ「対象社員コード(employeeCode)」を取得する
		if(Strings.isBlank(employeeCD)){
			throw new BusinessException("Msg_1000", "Msg_1026");
		}
		// 対象社員コード(employeeCode)をチェックする
		employeeSprPub.validateEmpCodeSpr(employeeCD.trim());
		// （基幹・社員Export）アルゴリズム「「会社ID」「社員コード」より社員基本情報を取得」を実行する　RequestList No.18
		Optional<EmpSprExport> opEmployeeSpr = employeeSprPub.getEmployeeID(companyID, employeeCD.trim());
		if(!opEmployeeSpr.isPresent()){
			throw new BusinessException("Msg_1000", "Msg_1027");
		}
		// フォームデータ「対象日(date)」を取得する
		if(Strings.isBlank(date)){
			throw new BusinessException("Msg_1009", "Msg_1026");
		}
		// 対象日(date)の形式をチェックする　日付型
		if(this.getDate(date)==null){
			throw new BusinessException("Msg_1009", date);
		}
		// フォームデータ「出勤時刻(starttime)」を取得する
		if(startTime==null){
			throw new BusinessException("Msg_1012", "Msg_1026");
		}
		if(Strings.isNotBlank(startTime)){
			// 出勤時刻(starttime)をチェックする
			try {
				Integer startTimeValue = Integer.valueOf(startTime);
				new AttendanceClock(startTimeValue);
			} catch (Exception e) {
				throw new BusinessException("Msg_1012", startTime.toString());
			}
		}
		// フォームデータ「退勤時刻(endtime)」を取得する
		if(endTime==null){
			throw new BusinessException("Msg_1013", "Msg_1026");
		}
		if(Strings.isNotBlank(endTime)){
			// 退勤時刻(endtime)をチェックする
			try {
				Integer endTimeValue = Integer.valueOf(endTime);
				new AttendanceClock(endTimeValue);
			} catch (Exception e) {
				throw new BusinessException("Msg_1013", endTime.toString());
			}
		}
		// フォームデータ「打刻保護区分(stampProtection)」を取得する
		try {
			Integer stampFlgValue = Integer.valueOf(stampFlg);
			// 打刻保護区分(stampProtection)をチェックする
			if(stampFlgValue != 0 && stampFlgValue != 1){
				throw new BusinessException("Msg_1194", stampFlg);
			}
		} catch (NumberFormatException e) {
			throw new BusinessException("Msg_1194", "Msg_1026");
		}
		return opEmployeeSpr.get().getEmployeeID();
	}

	@Override
	public void checkParamApprovalList(String date, String selectType) {
		// フォームデータ「対象日(date)」を取得する
		if(Strings.isBlank(date)){
			throw new BusinessException("Msg_1009", "Msg_1026");
		}
		// 対象日(date)の形式をチェックする　日付型
		if(this.getDate(date)==null){
			throw new BusinessException("Msg_1009", date);
		}
		// フォームデータ「抽出対象(selecttype)」を取得する
		try {
			Integer selectTypeValue = Integer.valueOf(selectType.trim());
			// 抽出対象(selecttype)をチェックする
			if(selectTypeValue != 0 && selectTypeValue != 1){
				throw new BusinessException("Msg_1014", selectType.toString());
			}
		} catch (NumberFormatException e) {
			throw new BusinessException("Msg_1014", "Msg_1026");
		}
	}

	@Override
	public void checkParamConfirmDaily(String date) {
		// フォームデータ「対象日(date)」を取得する
		if(Strings.isBlank(date)){
			throw new BusinessException("Msg_1009", "Msg_1026");
		}
		// 対象日(date)の形式をチェックする　日付型
		if(this.getDate(date)==null){
			throw new BusinessException("Msg_1009", date);
		}
	}

	@Override
	public void checkParamConfirmOvertime(String appID) {
		// フォームデータ「申請ID(applicationID)」を取得する
		if(Strings.isBlank(appID)){
			throw new BusinessException("Msg_1025", "Msg_1026");
		}
		// 申請ID(applicationID)の形式をチェックする　UUID
		try {
			UUID.fromString(appID);
		} catch (Exception e) {
			throw new BusinessException("Msg_1025", appID);
		}
	}
	
	private boolean checkDateFormat(String date, String dateFormat){
		try {
			GeneralDate.fromString(date, dateFormat);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public GeneralDate getDate(String date) {
		if(checkDateFormat(date, DATE_FORMAT1)){
			return GeneralDate.fromString(date, DATE_FORMAT1);
		}
		if(checkDateFormat(date, DATE_FORMAT2)){
			return GeneralDate.fromString(date, DATE_FORMAT2);
		}
		if(checkDateFormat(date, DATE_FORMAT3)){
			return GeneralDate.fromString(date, DATE_FORMAT3);
		}
		if(checkDateFormat(date, DATE_FORMAT4)){
			return GeneralDate.fromString(date, DATE_FORMAT4);
		}
		return null;
	}

}
