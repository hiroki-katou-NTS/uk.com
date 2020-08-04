package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.dom.standardtime.export.GetAgreementPeriodFromYear;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;

/**
 * 36協定期間を取得
 * @author shuichi_ishida
 */
@Stateless
public class GetAgreementPeriodImpl implements GetAgreementPeriod {

	/** ドメインサービス：締め */
	@Inject
	private ClosureService closureService;
	/** 年度から集計期間を取得 */
	@Inject
	private GetAgreementPeriodFromYear getAgreementPeriodFromYear;
	/** 36協定運用設定の取得 */
	@Inject
	private AgreementOperationSettingRepository agreementOpeSetRepo;
	
	/** 年度を指定して36協定期間を取得 */
	@Override
	public Optional<DatePeriod> byYear(String companyId, String employeeId, GeneralDate criteria, Year year) {
		
		// 社員に対応する処理締めを取得する
		val closure = this.closureService.getClosureDataByEmployee(employeeId, criteria);
		if (closure == null) return Optional.empty();
		
		// 年度から集計期間を取得
		val resultOpt = this.getAgreementPeriodFromYear.algorithm(year, closure);
		
		// 期間を返す
		return resultOpt;
	}
	
	/** 指定日を含む年期間を取得 */
	@Override
	public Optional<YearMonthPeriod> containsDate(String companyId, GeneralDate criteria,
			Optional<AgreementOperationSetting> agreementOperationSetOpt, Closure closure) {
		
		// 「36協定運用設定」を取得する
		AgreementOperationSetting agreementOpeSet = null;
		if (agreementOperationSetOpt.isPresent()) {
			agreementOpeSet = agreementOperationSetOpt.get();
		}
		else {
			val dbAgreOpeSetOpt = this.agreementOpeSetRepo.find(companyId);
			if (dbAgreOpeSetOpt.isPresent()) agreementOpeSet = dbAgreOpeSetOpt.get(); 
		}
		if (agreementOpeSet == null) return Optional.empty(); 
		
		// 年月期間を返す
		return Optional.of(agreementOpeSet.getPeriodYear(criteria, closure, this.getAgreementPeriodFromYear));
	}
}
