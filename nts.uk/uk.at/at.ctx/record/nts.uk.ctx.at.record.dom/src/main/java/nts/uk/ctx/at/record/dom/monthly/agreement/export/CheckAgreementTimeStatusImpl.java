package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreMaxTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxAverageTime;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreTimeYearStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;

/**
 * 実装：36協定時間の状態チェック
 * @author shuichi_ishida
 */
@Stateless
public class CheckAgreementTimeStatusImpl implements CheckAgreementTimeStatus {

	/** 36協定運用設定の取得 */
//	@Inject
//	private AgreementOperationSettingRepository agreementOpeSetRepo;
	
	/** 36協定時間の状態チェック */
	@Override
	public AgreementTimeStatusOfMonthly algorithm(AttendanceTimeMonth agreementTime, LimitOneMonth limitAlarmTime,
			LimitOneMonth limitErrorTime, Optional<LimitOneMonth> exceptionLimitAlarmTime,
			Optional<LimitOneMonth> exceptionLimitErrorTime) {
		
		// 月別実績の36協定時間をパラメータから作成
		int paramlimitAlarmTime = 0;
		if (limitAlarmTime != null) paramlimitAlarmTime = limitAlarmTime.v();
		int paramlimitErrorTime = 0;
		if (limitErrorTime != null) paramlimitErrorTime = limitErrorTime.v();
		nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneMonth paramExceptionLimitAlarmTime = null;
		if (exceptionLimitAlarmTime.isPresent()){
			paramExceptionLimitAlarmTime = new nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneMonth(
					exceptionLimitAlarmTime.get().v());
		}
		nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneMonth paramExceptionLimitErrorTime = null;
		if (exceptionLimitErrorTime.isPresent()){
			paramExceptionLimitErrorTime = new nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneMonth(
					exceptionLimitErrorTime.get().v());
		}
		AgreementTimeOfMonthly agreementTimeOfMonthly = AgreementTimeOfMonthly.of(
				(agreementTime == null ? new AttendanceTimeMonth(0) : agreementTime),
				new nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneMonth(paramlimitErrorTime),
				new nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneMonth(paramlimitAlarmTime),
				Optional.ofNullable(paramExceptionLimitErrorTime),
				Optional.ofNullable(paramExceptionLimitAlarmTime),
				AgreementTimeStatusOfMonthly.NORMAL);
		
		// チェック処理
		agreementTimeOfMonthly.errorCheck();
		
		// 「状態」を返す
		return agreementTimeOfMonthly.getStatus();
	}
	
	/** 36協定上限時間の状態チェック */
	@Override
	public AgreMaxTimeStatusOfMonthly maxTime(AttendanceTimeMonth agreementTime, LimitOneMonth maxTime,
			Optional<AttendanceTimeMonth> requestTimeOpt) {

		// 申請時間を36協定時間に加算
		int checkAgreementMinutes = agreementTime.v();
		if (requestTimeOpt.isPresent()) {
			checkAgreementMinutes += requestTimeOpt.get().v();
		}
		AttendanceTimeMonth checkAgreementTime = new AttendanceTimeMonth(checkAgreementMinutes);
		
		// 月別実績の36協定上限時間をパラメータから作成
		AgreMaxTimeOfMonthly agreMaxTimeOfMonthly = AgreMaxTimeOfMonthly.of(
				checkAgreementTime,
				new nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneMonth(maxTime.v()),
				AgreMaxTimeStatusOfMonthly.NORMAL);
		
		// チェック処理
		agreMaxTimeOfMonthly.errorCheck();
		
		// 月別実績の36協定上限時間状態を返す
		return agreMaxTimeOfMonthly.getStatus();
	}
	
	/** 36協定上限複数月平均時間の状態チェック */
	@Override
	public AgreMaxAverageTimeMulti maxAverageTimeMulti(String companyId, AgreMaxAverageTimeMulti sourceTime,
			Optional<AttendanceTime> requestTimeOpt, Optional<GeneralDate> requestDateOpt) {

		List<AgreMaxAverageTime> checkedTimeList = new ArrayList<>();
		
		// 36協定運用設定を取得
//		val agreementOpeSetOpt = this.agreementOpeSetRepo.find(companyId);
		
		// 36協定上限複数月平均時間．上限時間をチェック
		if (sourceTime.getMaxTime().v() <= 0) return sourceTime;
		
		// 36協定上限複数月平均時間を取得　→　取得した件数分ループ
		for (val averageTime : sourceTime.getAverageTimeList()) {
			int checkTotalMinutes = averageTime.getTotalTime().v();
			
// 2019.5.28 DEL shuichi_ishida Redmine #106940　（未反映申請対応）
//			// 申請時間、申請年月日を確認
//			if (requestTimeOpt.isPresent() && requestDateOpt.isPresent()) {
//				
//				// 申請時間を合計時間に加算する　（36協定運用設定が存在する時）
//				if (agreementOpeSetOpt.isPresent()) {
//					val agreementOpeSet = agreementOpeSetOpt.get();
//					
//					// 年月期間から36協定期間を取得する
//					val periodOpt = agreementOpeSet.getAgreementPeriodByYMPeriod(averageTime.getPeriod());
//					if (periodOpt.isPresent()) {
//						
//						// 追加年月日が期間に含まれているか確認
//						if (periodOpt.get().contains(requestDateOpt.get())) {
//							
//							// 追加時間を合計時間に加算する
//							checkTotalMinutes += requestTimeOpt.get().v();
//						}
//					}
//				}
//			}
				
			// 36協定上限各月平均時間を作成
			AgreMaxAverageTime agreMaxAverageTime = AgreMaxAverageTime.of(
					averageTime.getPeriod(), new AttendanceTimeYear(checkTotalMinutes),
					AgreMaxTimeStatusOfMonthly.NORMAL);
			
			// エラーチェック
			agreMaxAverageTime.errorCheck(sourceTime.getMaxTime());
			
			// 36協定上限各月平均時間を返す
			checkedTimeList.add(agreMaxAverageTime);
		}
		
		// 36協定上限複数月平均時間を返す
		return AgreMaxAverageTimeMulti.of(new LimitOneMonth(sourceTime.getMaxTime().v()), checkedTimeList);
	}
	
	/** 36協定年間時間の状態チェック */
	@Override
	public AgreTimeYearStatusOfMonthly timeYear(AgreementTimeYear agreementTimeYear,
			Optional<AttendanceTimeYear> requestTimeOpt) {

		AgreementTimeYear checkAgreementTimeYear = agreementTimeYear.clone();
		
		// 申請時間を実績時間に加算する
		if (requestTimeOpt.isPresent()) {
			checkAgreementTimeYear.addRecordTime(requestTimeOpt.get().v());
		}
		
		// エラーチェック
		checkAgreementTimeYear.errorCheck();
		
		// 月別実績の36協定年間時間状態を返す
		return checkAgreementTimeYear.getStatus();
	}
}
