package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor;

import java.util.Optional;
import java.util.List;

/**
* 基金加入員情報
*/
public interface FundMembershipRepository
{

    List<FundMembership> getAllFundMembership();

    Optional<FundMembership> getFundMembershipById();

    void add(FundMembership domain);

    void update(FundMembership domain);

    void remove();

}
