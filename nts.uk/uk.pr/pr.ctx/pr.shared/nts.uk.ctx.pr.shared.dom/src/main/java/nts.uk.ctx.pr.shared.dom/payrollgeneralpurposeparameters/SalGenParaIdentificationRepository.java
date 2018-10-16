package nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters;

import java.util.Optional;
import java.util.List;

/**
* 給与汎用パラメータ識別
*/
public interface SalGenParaIdentificationRepository
{

    List<SalGenParaIdentification> getAllSalGenParaIdentification(String cId);

    Optional<SalGenParaIdentification> getSalGenParaIdentificationById(String paraNo, String cid);

    void add(SalGenParaIdentification domain);

    void update(SalGenParaIdentification domain);

    void remove(String paraNo, String cid);

}
