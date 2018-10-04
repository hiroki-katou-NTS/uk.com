package nts.uk.ctx.at.record.dom.monthly.agreement;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

/**
 * 月別実績の36協定時間
 * @author shuichi_ishida
 */
@Getter
public class AgreementTimeOfMonthly {

	/** 36協定時間 */
	@Setter
	private AttendanceTimeMonth agreementTime;
	/** 限度エラー時間 */
	private LimitOneMonth limitErrorTime;
	/** 限度アラーム時間 */
	private LimitOneMonth limitAlarmTime;
	/** 特例限度エラー時間 */
	private Optional<LimitOneMonth> exceptionLimitErrorTime;
	/** 特例限度アラーム時間 */
	private Optional<LimitOneMonth> exceptionLimitAlarmTime;
	/** 状態 */
	private AgreementTimeStatusOfMonthly status;

	/** 確定情報用エラーメッセージ */
	@Setter
	private String confirmedErrorMessage;
	
	/**
	 * コンストラクタ
	 */
	public AgreementTimeOfMonthly(){
		
		this.agreementTime = new AttendanceTimeMonth(0);
		this.limitErrorTime = new LimitOneMonth(0);
		this.limitAlarmTime = new LimitOneMonth(0);
		this.exceptionLimitErrorTime = Optional.empty();
		this.exceptionLimitAlarmTime = Optional.empty();
		this.status = AgreementTimeStatusOfMonthly.NORMAL;
		
		this.confirmedErrorMessage = null;
	}
	
	/**	
	 * ファクトリー
	 * @param agreementTime 36協定時間
	 * @param limitErrorTime 限度エラー時間
	 * @param limitAlarmTime 限度アラーム時間
	 * @param exceptionLimitErrorTime 特例限度エラー時間
	 * @param exceptionLimitAlarmTime 特例限度アラーム時間
	 * @param status 状態
	 * @return 月別実績の36協定時間
	 */
	public static AgreementTimeOfMonthly of(
			AttendanceTimeMonth agreementTime,
			LimitOneMonth limitErrorTime,
			LimitOneMonth limitAlarmTime,
			Optional<LimitOneMonth> exceptionLimitErrorTime,
			Optional<LimitOneMonth> exceptionLimitAlarmTime,
			AgreementTimeStatusOfMonthly status){
		
		AgreementTimeOfMonthly domain = new AgreementTimeOfMonthly();
		domain.agreementTime = agreementTime;
		domain.limitErrorTime = limitErrorTime;
		domain.limitAlarmTime = limitAlarmTime;
		domain.exceptionLimitErrorTime = exceptionLimitErrorTime;
		domain.exceptionLimitAlarmTime = exceptionLimitAlarmTime;
		domain.status = status;
		return domain;
	}
	
	/**
	 * エラーアラーム値の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param yearMonth 年月
	 * @param workingSystem 労働制
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void getErrorAlarmValue(
			String companyId,
			String employeeId,
			GeneralDate criteriaDate,
			YearMonth yearMonth,
			WorkingSystem workingSystem,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 初期設定
		this.limitErrorTime = new LimitOneMonth(0);
		this.limitAlarmTime = new LimitOneMonth(0);
		this.exceptionLimitErrorTime = Optional.empty();
		this.exceptionLimitAlarmTime = Optional.empty();
		
		// 「36協定基本設定」を取得する
		val basicAgreementSet = repositories.getAgreementDomainService().getBasicSet(
				companyId, employeeId, criteriaDate, workingSystem);
		this.limitErrorTime = new LimitOneMonth(basicAgreementSet.getErrorOneMonth().v());
		this.limitAlarmTime = new LimitOneMonth(basicAgreementSet.getAlarmOneMonth().v());
		
		// 「36協定年月設定」を取得
		val agreementMonthSetOpt = repositories.getAgreementMonthSet().findByKey(employeeId, yearMonth);
		if (agreementMonthSetOpt.isPresent()){
			val agreementMonthSet = agreementMonthSetOpt.get();
			this.exceptionLimitErrorTime = Optional.of(new LimitOneMonth(agreementMonthSet.getErrorOneMonth().v()));
			this.exceptionLimitAlarmTime = Optional.of(new LimitOneMonth(agreementMonthSet.getAlarmOneMonth().v()));
		}
	}
	
	
	/**
	 * エラーアラーム値の取得　（週用）
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param workingSystem 労働制
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void getErrorAlarmValueForWeek(
			String companyId,
			String employeeId,
			GeneralDate criteriaDate,
			WorkingSystem workingSystem,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 初期設定
		this.limitErrorTime = new LimitOneMonth(0);
		this.limitAlarmTime = new LimitOneMonth(0);
		this.exceptionLimitErrorTime = Optional.empty();
		this.exceptionLimitAlarmTime = Optional.empty();
		
		// 「36協定基本設定」を取得する
		val basicAgreementSet = repositories.getAgreementDomainService().getBasicSet(
				companyId, employeeId, criteriaDate, workingSystem);
		this.limitErrorTime = new LimitOneMonth(basicAgreementSet.getErrorWeek().v());
		this.limitAlarmTime = new LimitOneMonth(basicAgreementSet.getAlarmWeek().v());
	}
	
	/**
	 * エラーチェック
	 */
	public void errorCheck(){
		
		// 特例限度アラーム時間に値が入っているか確認する
		if (!this.exceptionLimitAlarmTime.isPresent()){
			
			// 限度アラーム時間以下
			if (this.agreementTime.lessThanOrEqualTo(this.limitAlarmTime.v())){
				this.status = AgreementTimeStatusOfMonthly.NORMAL;
				return;
			}
			
			// 限度エラー時間以下
			if (this.agreementTime.lessThanOrEqualTo(this.limitErrorTime.v())){
				this.status = AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM;
				return;
			}
			
			this.status = AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR;
			return;
		}
		
		// 限度アラーム時間以下
		if (this.agreementTime.lessThanOrEqualTo(this.limitAlarmTime.v())){
			this.status = AgreementTimeStatusOfMonthly.NORMAL_SPECIAL;
			return;
		}
		
		// 限度エラー時間以下
		if (this.agreementTime.lessThanOrEqualTo(this.limitErrorTime.v())){
			this.status = AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM_SP;
			return;
		}
		
		// 特例限度アラーム時間以下
		if (this.agreementTime.lessThanOrEqualTo(this.exceptionLimitAlarmTime.get().v())){
			this.status = AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR_SP;
			return;
		}
		
		// 特例限度エラー時間以下
		if (this.exceptionLimitErrorTime.isPresent()){
			if (this.agreementTime.lessThanOrEqualTo(this.exceptionLimitErrorTime.get().v())){
				this.status = AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM;
				return;
			}
		}
		else {
			this.status = AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM;
			return;
		}
		
		// 特例限度エラー時間を超える
		this.status = AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR;
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(AgreementTimeOfMonthly target){
		
		this.agreementTime = this.agreementTime.addMinutes(target.agreementTime.v());
		
		// 再エラーチェックする
		this.errorCheck();
	}
}
