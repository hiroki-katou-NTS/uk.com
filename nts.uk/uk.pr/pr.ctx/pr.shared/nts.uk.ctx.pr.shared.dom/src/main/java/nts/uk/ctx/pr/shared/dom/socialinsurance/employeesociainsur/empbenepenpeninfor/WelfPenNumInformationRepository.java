package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

import java.util.Optional;

/**
* 厚生年金番号情報
*/
public interface WelfPenNumInformationRepository{

    Optional<WelfPenNumInformation> getWelfPenNumInformationById(String affMourPeriodHisid);

    Optional<WelfPenNumInformation> getWelfPenNumInformationById(String cid, String affMourPeriodHisid,String empId);
}
