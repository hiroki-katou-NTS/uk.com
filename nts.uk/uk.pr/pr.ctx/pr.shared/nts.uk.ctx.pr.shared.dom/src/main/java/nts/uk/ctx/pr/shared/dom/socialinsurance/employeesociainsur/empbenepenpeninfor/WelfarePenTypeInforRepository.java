package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

import java.util.List;
import java.util.Optional;

/**
* 厚生年金種別情報
*/
public interface WelfarePenTypeInforRepository
{

    Optional<WelfarePenTypeInfor> getWelfarePenTypeInforById(String historyId);
    Optional<WelfarePenTypeInfor> getWelfarePenTypeInforById(String cid, String empID, String historyId);

}
