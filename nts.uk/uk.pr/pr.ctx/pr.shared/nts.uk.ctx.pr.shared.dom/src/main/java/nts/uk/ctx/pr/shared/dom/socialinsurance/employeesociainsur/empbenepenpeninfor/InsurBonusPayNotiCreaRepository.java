package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

import java.util.List;
import java.util.Optional;

/**
* 厚生年金保険賞与支払届作成時情報
*/
public interface InsurBonusPayNotiCreaRepository
{

    List<InsurBonusPayNotiCrea> getAllInsurBonusPayNotiCrea();

    Optional<InsurBonusPayNotiCrea> getInsurBonusPayNotiCreaById();

    void add(InsurBonusPayNotiCrea domain);

    void update(InsurBonusPayNotiCrea domain);

    void remove();

}
