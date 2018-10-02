package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriodRepository;
import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * 実装：年間超過回数の取得
 * @author shuichi_ishida
 */
@Stateless
public class GetExcessTimesYearImpl implements GetExcessTimesYear {

	/** 管理期間の36協定時間 */
	@Inject
	private AgreementTimeOfManagePeriodRepository agreementTimeOfMngPrdRepo;
	
	/** 年間超過回数の取得 */
	@Override
	public AgreementExcessInfo algorithm(String employeeId, Year year) {

		List<YearMonth> yearMonths = new ArrayList<>();
		
		// 36協定時間を取得
		val agreementTimeList =  this.agreementTimeOfMngPrdRepo.findByYearOrderByYearMonth(employeeId, year);
		
		// 状態が「超過」判定の件数をカウント
		int excessCount = 0;
		for (val agreementTime : agreementTimeList){
			if (agreementTime.getAgreementTime().getStatus().value > 0){
				excessCount++;
				yearMonths.add(agreementTime.getYearMonth());
			}
		}
		
		// 36協定釣果情報を返す
		return AgreementExcessInfo.of(excessCount, yearMonths);
	}
}
