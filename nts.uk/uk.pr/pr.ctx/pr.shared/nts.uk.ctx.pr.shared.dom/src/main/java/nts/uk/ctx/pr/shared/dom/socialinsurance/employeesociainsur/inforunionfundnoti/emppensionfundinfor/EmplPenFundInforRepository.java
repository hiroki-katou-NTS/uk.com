package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emppensionfundinfor;

import java.util.List;
import java.util.Optional;

/**
* 社員厚生年金基金情報
*/
public interface EmplPenFundInforRepository
{

    List<EmplPenFundInfor> getAllEmplPenFundInfor();

    Optional<EmplPenFundInfor> getEmplPenFundInforById();

    void add(EmplPenFundInfor domain);

    void update(EmplPenFundInfor domain);

    void remove();

}
