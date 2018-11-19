package nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters;

import java.util.Optional;
import java.util.List;

/**
* 給与汎用パラメータ値
*/
public interface SalGenParaValueRepository
{

    List<SalGenParaValue> getAllSalGenParaValue();

    Optional<SalGenParaValue> getSalGenParaValueById(String hisId);

    void add(SalGenParaValue domain);

    void update(SalGenParaValue domain);

    void remove(String hisId);

}
