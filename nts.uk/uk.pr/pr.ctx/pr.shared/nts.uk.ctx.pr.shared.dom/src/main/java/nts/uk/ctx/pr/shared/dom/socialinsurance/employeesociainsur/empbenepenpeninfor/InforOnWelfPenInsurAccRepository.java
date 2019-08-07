package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

import java.util.List;
import java.util.Optional;

/**
* 厚生年金保険取得時情報
*/
public interface InforOnWelfPenInsurAccRepository
{

    List<InforOnWelfPenInsurAcc> getAllInforOnWelfPenInsurAcc();

    Optional<InforOnWelfPenInsurAcc> getInforOnWelfPenInsurAccById(String employeeId);

    void add(InforOnWelfPenInsurAcc domain);

    void update(InforOnWelfPenInsurAcc domain);

    void remove(String employeeId);

}
