package nts.uk.ctx.exio.dom.monsalabonus.laborinsur;

import java.util.Optional;
import java.util.List;

/**
* 雇用保険料率
*/
public interface EmpInsurPreRateRepository
{

    List<EmpInsurPreRate> getAllEmpInsurPreRate();
    
    List<EmpInsurPreRate> getEmpInsurPreRateByCid(String cId);

    Optional<EmpInsurPreRate> getEmpInsurPreRateById(String hisId, String empPreRateId);

    void add(EmpInsurPreRate domain);

    void update(EmpInsurPreRate domain);

    void remove(String hisId, String empPreRateId);

}
