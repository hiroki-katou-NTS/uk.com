package nts.uk.ctx.exio.dom.monsalabonus.laborinsur;

import java.util.Optional;
import java.util.List;

/**
* 労災保険履歴
*/
public interface EmpInsurHisRepository
{


    Optional<EmpInsurHis> getEmpInsurHisByCid(String cid);

    Optional<EmpInsurHis> getEmpInsurHisById(String cid, String hisId);

    void add(EmpInsurHis domain);

    void update(EmpInsurHis domain);

    void remove(String cid, String hisId);

}
