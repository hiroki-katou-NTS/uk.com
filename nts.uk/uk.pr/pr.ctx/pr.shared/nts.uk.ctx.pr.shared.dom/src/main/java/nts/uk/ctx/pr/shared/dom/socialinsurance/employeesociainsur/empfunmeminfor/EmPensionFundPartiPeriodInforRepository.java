package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor;

import nts.arc.time.GeneralDate;

import java.util.List;
import java.util.Optional;

/**
* 厚生年金基金加入期間情報
*/
public interface EmPensionFundPartiPeriodInforRepository
{

    List<EmPensionFundPartiPeriodInfor> getAllEmPensionFundPartiPeriodInfor();

    Optional<EmPensionFundPartiPeriodInfor> getEmPensionFundPartiPeriodInforById(String employeeId, String cid);

    List<EmPensionFundPartiPeriodInfor> getEmPensionFundPartiPeriodInforByEmpId(String employeeId);

    Optional<FundMembership> getEmPensionFundPartiPeriodInfor(String cid, String employeeId, GeneralDate baseDate);

    Optional<FundMembership> getFundMembershipByEmpId(String employeeId, String hisId);

    Optional<FundMembership> getFundMembershipByEmpId(String cid, String employeeId, String hisId);

    void add(EmPensionFundPartiPeriodInfor domain);

    void update(EmPensionFundPartiPeriodInfor domain);

    void remove(String employeeId, String historyId);

}
