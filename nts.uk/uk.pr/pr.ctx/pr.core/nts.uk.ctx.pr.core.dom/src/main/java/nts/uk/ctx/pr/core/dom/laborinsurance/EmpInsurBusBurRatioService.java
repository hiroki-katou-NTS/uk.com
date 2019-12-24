package nts.uk.ctx.pr.core.dom.laborinsurance;

import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
public class EmpInsurBusBurRatioService {
	
	@Inject
	private EmpInsurBusBurRatioRepository empInsurBusBurRatioRepository;
    
    public void addEmpInsurBusBurRatio(EmpInsurPreRate empInsurPreRate, YearMonth start, YearMonth end){
    	String cId = AppContexts.user().companyId();
		YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(empInsurPreRate.getHisId(), new YearMonthPeriod(start, end));
		EmpInsurHis itemtoBeAdded = empInsurBusBurRatioRepository.getEmpInsurHisByCid(cId);
		itemtoBeAdded.add(yearMonthItem);
		this.addEmpInsurBusBurRatio(empInsurPreRate, cId, yearMonthItem);
		this.updateItemBefore(itemtoBeAdded, yearMonthItem, cId);
		
	}
    
    public void updateEmpInsurBusBurRatio(EmpInsurPreRate domain, YearMonthHistoryItem item){
    	String cId = AppContexts.user().companyId();
    	empInsurBusBurRatioRepository.update(domain, cId, item);
    }
    
    private void addEmpInsurBusBurRatio(EmpInsurPreRate domain, String cId, YearMonthHistoryItem item){
    	empInsurBusBurRatioRepository.add(domain, cId, item);
    }
    
    private void updateItemBefore(EmpInsurHis empInsurHis, YearMonthHistoryItem item, String cId){
		Optional<YearMonthHistoryItem> itemToBeUpdated = empInsurHis.immediatelyBefore(item);
		if (!itemToBeUpdated.isPresent()){
			return;
		}
		empInsurBusBurRatioRepository.update(itemToBeUpdated.get(), cId);
	}
    
    public void historyDeletionProcessing(String hisId, String cId){
    	EmpInsurHis empInsurHis = empInsurBusBurRatioRepository.getEmpInsurHisByCid(cId);
    	Optional<YearMonthHistoryItem> itemToBeDelete = empInsurHis.getHistory().stream()
                .filter(h -> h.identifier().equals(hisId))
                .findFirst();
    	if (!itemToBeDelete.isPresent()) {
    		return;
    	}
    	empInsurHis.remove(itemToBeDelete.get());
    	empInsurBusBurRatioRepository.remove(cId, hisId);
    	if (empInsurHis.getHistory().size() > 0 ){
    		YearMonthHistoryItem lastestItem = empInsurHis.getHistory().get(0);
    		empInsurHis.exCorrectToRemove(lastestItem);
    		empInsurBusBurRatioRepository.update(lastestItem, cId);
    	}
    }
    
    public void historyCorrectionProcecessing(String cId, String hisId, YearMonth start, YearMonth end){
    	EmpInsurHis empInsurHis = empInsurBusBurRatioRepository.getEmpInsurHisByCid(cId);
    	Optional<YearMonthHistoryItem> itemToBeUpdate = empInsurHis.getHistory().stream()
				.filter(h -> h.identifier().equals(hisId)).findFirst();
    	if (!itemToBeUpdate.isPresent()) {
    		return;
    	}
    	empInsurHis.changeSpan(itemToBeUpdate.get(), new YearMonthPeriod(start, end));
    	this.updateEmpInsurHis(itemToBeUpdate.get(), cId);
    	this.updateItemBefore(empInsurHis, itemToBeUpdate.get(), cId);
    }
    
    private void updateEmpInsurHis(YearMonthHistoryItem itemToBeUpdated, String cId){
    	empInsurBusBurRatioRepository.update(itemToBeUpdated, cId);
    }
}
