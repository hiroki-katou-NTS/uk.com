package nts.uk.ctx.at.request.dom.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeYear;

/**
 * refactor 5
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム."18.３６時間の上限チェック(新規登録)_NEW".時間外時間.時間外時間の詳細
 * @author Doan Duy Hung
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OvertimeHoursDetails {
	
	/**
	 * 36協定上限複数月平均時間
	 */
	private AgreMaxAverageTimeMulti agreMaxAverageTimeMulti;
	
	/**
	 * 36協定対象時間
	 */
	private AgreementTimeOfMonthly agreementTime;
	
	/**
	 * 36協定年間時間
	 */
	private AgreementTimeYear agreementTimeYear;
	
	/**
	 * 月別実績の36協定時間状態
	 */
	private AgreementTimeStatusOfMonthly status;
	
	/**
	 * 年月
	 */
	private YearMonth yearMonth;
	
	/**
	 * 法定上限対象時間
	 */
	private AgreementTimeOfMonthly legalMaxTime;
	
}
