package nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters;

import java.util.Optional;
import java.util.List;

/**
* 給与汎用パラメータ選択肢
*/
public interface SalGenParamOptionsRepository
{

    List<SalGenParamOptions> getAllSalGenParamOptions(String paraNo, String cid);

    Optional<SalGenParamOptions> getSalGenParamOptionsById(String paraNo, String cid, int optionNo);

    void add(SalGenParamOptions domain);

    void update(SalGenParamOptions domain);

    void remove(String paraNo, String cid, int optionNo);

}
