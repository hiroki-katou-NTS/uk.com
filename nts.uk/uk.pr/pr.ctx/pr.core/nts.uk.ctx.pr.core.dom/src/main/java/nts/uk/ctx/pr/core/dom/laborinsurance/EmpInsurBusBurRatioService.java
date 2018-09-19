package nts.uk.ctx.pr.core.dom.laborinsurance;

import java.util.ArrayList;
import java.util.List;
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
    private EmpInsurHisRepository empInsurHisRepository;
	
	@Inject
	private EmpInsurBusBurRatioRepository empInsurBusBurRatioRepository;
    
    public void addEmpInsurBusBurRatio(String newHistID, List<EmpInsurBusBurRatio> listEmpInsurBusBurRatio, YearMonth start, YearMonth end){
    	String cId = AppContexts.user().companyId();
		YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(newHistID, new YearMonthPeriod(start, end));
		EmpInsurHis itemtoBeAdded = new EmpInsurHis(cId, new ArrayList<>());
		Optional<EmpInsurHis> empInsurHis = empInsurHisRepository.getEmpInsurHisByCid(cId);
		if (empInsurHis.isPresent()) {
			itemtoBeAdded = empInsurHis.get();
		}
		itemtoBeAdded.add(yearMonthItem);
		this.addEmpInsurHis(yearMonthItem, cId);
		this.updateItemBefore(empInsurHis.get(), yearMonthItem, cId);
		this.addEmpInsurBusBurRatio(listEmpInsurBusBurRatio);
	}
    
    public void updateEmpInsurBusBurRatio(List<EmpInsurBusBurRatio> listEmpInsurBusBurRatio){
    	empInsurBusBurRatioRepository.update(listEmpInsurBusBurRatio);
    }
    
    private void addEmpInsurHis(YearMonthHistoryItem itemtoBeAdded, String cId){
    	if(itemtoBeAdded == null){
    		return;
    	}
    	empInsurHisRepository.add(itemtoBeAdded, cId);
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
    
    public void historyDeletionProcessing(String hisId, String cId){
    	Optional<EmpInsurHis> empInsurHis = empInsurHisRepository.getEmpInsurHisByCid(cId);
    	if (!empInsurHis.isPresent()) {
    		throw new RuntimeException("invalid YearMonthHistory"); 
    	}
    	Optional<YearMonthHistoryItem> itemToBeDelete = empInsurHis.get().getHistory().stream()
                .filter(h -> h.identifier().equals(hisId))
                .findFirst();
    	if (!itemToBeDelete.isPresent()) {
    		return;
    	}
    	empInsurHis.get().remove(itemToBeDelete.get());
    	empInsurBusBurRatioRepository.remove(hisId);
    	empInsurHisRepository.remove(cId, hisId);
    	if (empInsurHis.get().getHistory().size() > 0 ){
    		YearMonthHistoryItem lastestItem = empInsurHis.get().getHistory().get(0);
    		empInsurHis.get().exCorrectToRemove(lastestItem);
    		empInsurHisRepository.update(lastestItem, cId);
    	}
    }
    
    public void historyCorrectionProcecessing(String cId, String hisId, YearMonth start, YearMonth end){
    	Optional<EmpInsurHis> empInsurHis = empInsurHisRepository.getEmpInsurHisByCid(cId);
    	if (!empInsurHis.isPresent()) {
    		return;
    	}
    	Optional<YearMonthHistoryItem> itemToBeUpdate = empInsurHis.get().getHistory().stream()
				.filter(h -> h.identifier().equals(hisId)).findFirst();
    	if (!itemToBeUpdate.isPresent()) {
    		return;
    	}
    	empInsurHis.get().changeSpan(itemToBeUpdate.get(), new YearMonthPeriod(start, end));
    	this.updateEmpInsurHis(itemToBeUpdate.get(), cId);
    	this.updateItemBefore(empInsurHis.get(), itemToBeUpdate.get(), cId);
    }
    
    private void updateEmpInsurHis(YearMonthHistoryItem itemToBeUpdated, String cId){
    	empInsurHisRepository.update(itemToBeUpdated, cId);
    }
}
