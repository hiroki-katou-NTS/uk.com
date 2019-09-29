package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

import java.util.List;
import java.util.Optional;

/**
* 厚生年金番号情報
*/
public interface WelfPenNumInformationRepository
{

    List<WelfPenNumInformation> getAllWelfPenNumInformation();

    Optional<WelfPenNumInformation> getWelfPenNumInformationById(String affMourPeriodHisid);


}
