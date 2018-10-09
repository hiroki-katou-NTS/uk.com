package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriodRepository;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneYear;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.monthly.agreement.PeriodAtrOfAgreement;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;

/**
 * 実装：指定期間36協定時間の取得
 * @author shuichu_ishida
 */
@Stateless
public class GetAgreTimeByPeriodImpl implements GetAgreTimeByPeriod {

	/** 管理期間の36協定時間 */
	@Inject
	private AgreementTimeOfManagePeriodRepository agreementTimeOfMngPrdRepo;
	/** 労働条件項目の取得 */
	@Inject
	public WorkingConditionItemRepository workingConditionItem;
	/** ドメインサービス：36協定 */
	@Inject
	public AgreementDomainService agreementDomainService;
	
	/** 指定期間36協定時間の取得 */
	@Override
	public List<AgreementTimeByPeriod> algorithm(String companyId, String employeeId, GeneralDate criteria,
			Month startMonth, Year year, PeriodAtrOfAgreement periodAtr) {
		
		List<AgreementTimeByPeriod> results = new ArrayList<>();

		List<String> employeeIds = new ArrayList<>();
		employeeIds.add(employeeId);
		
		// ループする期間を判断
		int stepMon = 2;
		if (periodAtr == PeriodAtrOfAgreement.THREE_MONTHS) stepMon = 3;
		if (periodAtr == PeriodAtrOfAgreement.ONE_MONTH) stepMon = 1;
		if (periodAtr == PeriodAtrOfAgreement.ONE_YEAR) stepMon = 12;
		YearMonth idxYm = YearMonth.of(year.v(), startMonth.v());
		for (int i = 0; i < 12; i += stepMon){
			List<YearMonth> periodYmList = new ArrayList<>();
			for (int ii = 0; ii < stepMon; ii++){
				periodYmList.add(idxYm.addMonths(i + ii));
			}
			
			// 36協定時間を取得
			val agreementTimeList =
					this.agreementTimeOfMngPrdRepo.findBySidsAndYearMonths(employeeIds, periodYmList);
			if (agreementTimeList.size() == 0) continue;
			
			// 期間をセット
			AgreementTimeByPeriod result = new AgreementTimeByPeriod(
					periodYmList.get(0), periodYmList.get(periodYmList.size() - 1));
			
			// 36協定時間を合計
			for (val agreeemntTime : agreementTimeList){
				result.addMinutesToAgreementTime(agreeemntTime.getAgreementTime().getAgreementTime().v());
			}
			
			// 状態チェック
			{
				// 「労働条件項目」を取得
				val workingConditionItemOpt =
						this.workingConditionItem.getBySidAndStandardDate(employeeId, criteria);
				if (!workingConditionItemOpt.isPresent()){
					return results;
				}
				
				// 労働制を確認する
				val workingSystem = workingConditionItemOpt.get().getLaborSystem();
				
				// 36協定基本設定を取得する
				val basicAgreementSet = this.agreementDomainService.getBasicSet(
						companyId, employeeId, criteria, workingSystem);
				
				// 取得した限度時間をセット
				switch (periodAtr){
				case TWO_MONTHS:
					result.setLimitAlarmTime(new LimitOneYear(basicAgreementSet.getAlarmTwoMonths().v()));
					result.setLimitErrorTime(new LimitOneYear(basicAgreementSet.getErrorTwoMonths().v()));
					break;
				case THREE_MONTHS:
					result.setLimitAlarmTime(new LimitOneYear(basicAgreementSet.getAlarmThreeMonths().v()));
					result.setLimitErrorTime(new LimitOneYear(basicAgreementSet.getErrorThreeMonths().v()));
					break;
				case ONE_YEAR:
					result.setLimitAlarmTime(new LimitOneYear(basicAgreementSet.getAlarmOneYear().v()));
					result.setLimitErrorTime(new LimitOneYear(basicAgreementSet.getErrorOneYear().v()));
					break;
				default:
					result.setLimitAlarmTime(new LimitOneYear(basicAgreementSet.getAlarmOneMonth().v()));
					result.setLimitErrorTime(new LimitOneYear(basicAgreementSet.getErrorOneMonth().v()));
					break;
				}
				
				// チェック処理
				result.errorCheck();
			}
			
			results.add(result);
		}
		
		// 年間36協定時間を返す
		return results;
	}
}
