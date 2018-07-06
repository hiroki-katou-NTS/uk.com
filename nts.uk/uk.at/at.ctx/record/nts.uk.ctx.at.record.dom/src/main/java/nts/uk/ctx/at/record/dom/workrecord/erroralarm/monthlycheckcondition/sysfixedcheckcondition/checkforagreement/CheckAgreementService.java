package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.sysfixedcheckcondition.checkforagreement;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkprincipalunconfirm.ValueExtractAlarmWR;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;

/**
 * 5:36協定のチェック
 * @author tutk
 *
 */
public interface CheckAgreementService {
	Optional<ValueExtractAlarmWR> checkAgreement(String employeeID,int yearMonth,int closureId,ClosureDate closureDate);
	
	
}
