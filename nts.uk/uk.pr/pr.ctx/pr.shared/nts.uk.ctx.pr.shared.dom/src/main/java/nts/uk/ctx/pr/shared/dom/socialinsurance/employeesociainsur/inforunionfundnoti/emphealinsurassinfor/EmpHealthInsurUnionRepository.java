package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emphealinsurassinfor;

import java.util.Optional;
import java.util.List;

/**
* emphealinsurassinfor
*/
public interface EmpHealthInsurUnionRepository
{

    List<EmpHealthInsurUnion> getAllEmpHealthInsurUnion();

    Optional<EmpHealthInsurUnion> getEmpHealthInsurUnionById(String employeeId);

    void add(EmpHealthInsurUnion domain);

    void update(EmpHealthInsurUnion domain);

    void remove(String employeeId);

}
