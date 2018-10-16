package nts.uk.ctx.pr.shared.app.find.payrollgeneralpurposeparameters;

import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaYMHistRepository;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaYearMonthHistory;
import nts.uk.shr.com.context.AppContexts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 給与汎用パラメータ年月履歴: Finder
*/
public class SalGenParaYMHistFinder
{

    @Inject
    private SalGenParaYMHistRepository finder;

    public List<SalGenParaYMHistDto> getAllSalGenParaYMHist(String paraNo){
        String cId = AppContexts.user().companyId();
        Optional<SalGenParaYearMonthHistory> monthHistories = finder.getAllSalGenParaYMHist(cId,paraNo);
        List<SalGenParaYMHistDto> genParaYearMonthHistories = new ArrayList<SalGenParaYMHistDto>();
        if (monthHistories.isPresent() && monthHistories.get().getHistory() != null) {
            genParaYearMonthHistories = SalGenParaYMHistDto.fromDomain(monthHistories.get());
        }
        return genParaYearMonthHistories;
    }

}
