package nts.uk.ctx.at.record.dom.workrecord.actualsituation.approvalsituationmanagement.export;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

/**
 * @author thanhnx
 * 承認処理が必要な職位かチェックする
 */
@Stateless
public class CheckPosstionApproval {
    public boolean checkPossitionApproval(String jobTitle, List<String> positions){
    	if(!positions.stream().filter(x -> x.equals(jobTitle)).collect(Collectors.toList()).isEmpty()) return true;
    	return false;
    }
}
