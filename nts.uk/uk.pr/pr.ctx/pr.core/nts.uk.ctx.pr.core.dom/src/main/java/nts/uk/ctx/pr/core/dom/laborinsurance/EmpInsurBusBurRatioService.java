package nts.uk.ctx.pr.core.dom.laborinsurance;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
public class EmpInsurBusBurRatioService {
	
	@Inject
    private EmpInsurHisRepository empInsurHisRepository;
	
	@Inject
	private EmpInsurBusBurRatioRepository empInsurBusBurRatioRepository;
    
    public void addEmpInsurBusBurRatio(List<EmpInsurBusBurRatio> listEmpInsurBusBurRatio, YearMonth start, YearMonth end){
    	String cId = AppContexts.user().companyId();
    	String newHistID = IdentifierUtil.randomUniqueId();
    	YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(newHistID, new YearMonthPeriod(start, end));
    	EmpInsurHis itemtoBeAdded = new EmpInsurHis(newHistID, new ArrayList<>());
    	Optional<EmpInsurHis> empInsurHis = empInsurHisRepository.getEmpInsurHisByCid(cId);
    	if (empInsurHis.isPresent()) {
    		itemtoBeAdded = empInsurHis.get();
    	}
    	itemtoBeAdded.add(yearMonthItem);
    	this.addEmpInsurHis(itemtoBeAdded);
    	this.updateItemBefore(empInsurHis.get(), yearMonthItem, cId);
    	this.addEmpInsurBusBurRatio(listEmpInsurBusBurRatio);
    	
    }
    
    public void updateEmpInsurBusBurRatio(List<EmpInsurBusBurRatio> listEmpInsurBusBurRatio){
    	empInsurBusBurRatioRepository.update(listEmpInsurBusBurRatio);
    }
    
    private void addEmpInsurHis(EmpInsurHis itemtoBeAdded){
    	if(itemtoBeAdded.getHistory().isEmpty()){
    		return;
    	}
    	empInsurHisRepository.add(itemtoBeAdded.getHistory().get(itemtoBeAdded.getHistory().size()), itemtoBeAdded.getCid());
    }
    
    private void addEmpInsurBusBurRatio(List<EmpInsurBusBurRatio> domain){
    	empInsurBusBurRatioRepository.add(domain);
    }
    
    private void updateItemBefore(EmpInsurHis empInsurHis, YearMonthHistoryItem item, String cId){
		Optional<YearMonthHistoryItem> itemToBeUpdated = empInsurHis.immediatelyBefore(item);
		if (!itemToBeUpdated.isPresent()){
			return;
		}
		empInsurHisRepository.update(itemToBeUpdated.get(), cId);
	}
}
