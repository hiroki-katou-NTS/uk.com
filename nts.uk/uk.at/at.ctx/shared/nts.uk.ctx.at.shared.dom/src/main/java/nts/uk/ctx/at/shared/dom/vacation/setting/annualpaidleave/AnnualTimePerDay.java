package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeAnnualRoundProcesCla;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;

/**
 * 時間年休一日の時間
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class AnnualTimePerDay {

	/** 参照先 */
	private AnnualTimePerDayRefer annualTimePerDayRefer;
	
	/** 全社一律の時間 */
	private LaborContractTime laborContractTime;
	
	/** 契約時間丸め */
	private ContractTimeRound contractTimeRound;
	
}
