package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor;

import java.util.List;
import java.util.Optional;

/**
* 厚生年金基金加入期間情報
*/
public interface EmPensionFundPartiPeriodInforRepository
{

    List<EmPensionFundPartiPeriodInfor> getAllEmPensionFundPartiPeriodInfor();

    Optional<EmPensionFundPartiPeriodInfor> getEmPensionFundPartiPeriodInforById(String employeeId, String historyId);

    List<EmPensionFundPartiPeriodInfor> getEmPensionFundPartiPeriodInforByEmpId(String employeeId);

    List<FundMembership> getFundMembershipByEmpId(String employeeId);

    void add(EmPensionFundPartiPeriodInfor domain);

    void update(EmPensionFundPartiPeriodInfor domain);

    void remove(String employeeId, String historyId);

}
