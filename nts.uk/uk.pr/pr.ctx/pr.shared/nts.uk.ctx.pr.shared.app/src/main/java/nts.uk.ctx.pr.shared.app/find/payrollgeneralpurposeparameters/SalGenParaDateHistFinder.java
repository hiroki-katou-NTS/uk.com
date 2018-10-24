package nts.uk.ctx.pr.shared.app.find.payrollgeneralpurposeparameters;

import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaDateHistRepository;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaDateHistory;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
/**
* 給与汎用パラメータ年月日履歴: Finder
*/
public class SalGenParaDateHistFinder
{

    @Inject
    private SalGenParaDateHistRepository repository;

    private final static String HIS_ID_TEMP = "00000devphuc.tc";

    public List<SalGenParaDateHistDto> getAllSalGenParaDateHist(String paraNo){
        String cId = AppContexts.user().companyId();
        Optional<SalGenParaDateHistory> salGenParaDateHis = repository.getAllSalGenParaDateHist(cId,paraNo);
        List<SalGenParaDateHistDto> salGenParaDateHisDto = new ArrayList<SalGenParaDateHistDto>();
        if (salGenParaDateHis.isPresent() && salGenParaDateHis.get().getDateHistoryItem() != null) {
            salGenParaDateHisDto = SalGenParaDateHistDto.fromDomain(salGenParaDateHis.get());
        }
        return salGenParaDateHisDto;
    }
    public List<SalGenParaDateHistDto> getListHistory(String paraNo,GeneralDate startDate,GeneralDate end){
        Optional<SalGenParaDateHistory> objectHis = repository.getAllSalGenParaDateHist(AppContexts.user().companyId(),paraNo);
        SalGenParaDateHistory salGenParaDateHistory = new SalGenParaDateHistory(paraNo,AppContexts.user().companyId(), objectHis.get().getDateHistoryItem());
        DateHistoryItem dateHistoryItem = new DateHistoryItem(HIS_ID_TEMP, new DatePeriod(startDate,end));
        salGenParaDateHistory.add(dateHistoryItem);
        List<SalGenParaDateHistDto> salGenParaDateHisDto = new ArrayList<SalGenParaDateHistDto>();
        if (salGenParaDateHistory.getDateHistoryItem() != null) {
            salGenParaDateHisDto = SalGenParaDateHistDto.fromDomain(salGenParaDateHistory);
        }
        return salGenParaDateHisDto;
    }

}
