package nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract;

import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHisRepository;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 給与個人別金額履歴: Finder
*/
public class SalIndAmountHisFinder
{

    @Inject
    private SalIndAmountHisRepository finder;

    public List<SalIndAmountHisDto> getAllSalIndAmountHis(){
        return null;
    }

}
