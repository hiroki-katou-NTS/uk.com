package nts.uk.ctx.pr.shared.app.find.payrollgeneralpurposeparameters;

import nts.uk.ctx.pr.shared.app.find.payrollgeneralpurposeparameters.SalGenParaDateHistDto;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaDateHistRepository;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaDateHistory;
import nts.uk.shr.com.context.AppContexts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 給与汎用パラメータ年月日履歴: Finder
*/
public class SalGenParaDateHistFinder
{

    @Inject
    private SalGenParaDateHistRepository repository;

    public List<SalGenParaDateHistDto> getAllSalGenParaDateHist(String paraNo){
        String cId = AppContexts.user().companyId();
        Optional<SalGenParaDateHistory> salGenParaDateHis = repository.getAllSalGenParaDateHist(cId,paraNo);
        List<SalGenParaDateHistDto> salGenParaDateHisDto = new ArrayList<SalGenParaDateHistDto>();
        if (salGenParaDateHis.isPresent() && salGenParaDateHis.get().getDateHistoryItem() != null) {
            salGenParaDateHisDto = SalGenParaDateHistDto.fromDomain(salGenParaDateHis.get());
        }
        return salGenParaDateHisDto;
    }

}
