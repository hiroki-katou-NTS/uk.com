package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.standardtime.export.GetAgreementPeriodFromYear;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
}
