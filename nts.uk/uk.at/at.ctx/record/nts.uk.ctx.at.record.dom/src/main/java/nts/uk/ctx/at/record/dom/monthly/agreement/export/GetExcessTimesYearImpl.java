package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriodRepository;
import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * 実装：年間超過回数の取得
 * @author shuichu_ishida
 */
@Stateless
public class GetExcessTimesYearImpl implements GetExcessTimesYear {

	/** 管理期間の36協定時間 */
	@Inject
	private AgreementTimeOfManagePeriodRepository agreementTimeOfMngPrdRepo;
	
	/** 年間超過回数の取得 */
	@Override
	public int algorithm(String employeeId, Year year) {
		
		// 36協定時間を取得
		val agreementTimeList =  this.agreementTimeOfMngPrdRepo.findByYearOrderByYearMonth(employeeId, year);
		
		// 状態が「超過」判定の件数をカウント
		int excessCount = 0;
		for (val agreementTime : agreementTimeList){
			switch (agreementTime.getAgreementTime().getStatus()){
			case EXCESS_EXCEPTION_LIMIT_ALARM:
			case EXCESS_EXCEPTION_LIMIT_ERROR:
			case EXCESS_LIMIT_ERROR_SP:
				excessCount++;
				break;
			default:
				break;
			}
		}
		
		// 件数を返す
		return excessCount;
	}
}
