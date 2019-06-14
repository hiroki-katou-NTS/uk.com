package nts.uk.ctx.pr.core.dom.laborinsurance;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;


@Stateless
public class OccAccidentInsurService {

    @Inject
    private OccAccInsurBusRepository occAccInsurBusRepository;
    
    @Inject
    private OccAccIsPrRateRepository occAccIsPrRateRepository;

    /*
    * 初期データ取得処理
    * */
    public OccAccIsHis initDataAcquisition(String cId){
        OccAccIsHis getEmpInsurHisByCid = occAccIsPrRateRepository.getOccAccIsHisByCid(cId);
        return getEmpInsurHisByCid;
    }

    public Optional<OccAccInsurBus> getOccAccInsurBus(String companyId){
        Optional<OccAccInsurBus> occAccInsurBus =  occAccInsurBusRepository.getOccAccInsurBus(companyId);
        return occAccInsurBus;
    }
    
    public void historyDeletionProcessing(String hisId, String cId){
    	OccAccIsHis accInsurHis = occAccIsPrRateRepository.getOccAccIsHisByCid(cId);
    	if (accInsurHis.getHistory() == null || accInsurHis.getHistory().isEmpty()) {
    		throw new RuntimeException("invalid employmentHistory"); 
    	}
    	Optional<YearMonthHistoryItem> itemToBeDelete = accInsurHis.getHistory().stream()
                .filter(h -> h.identifier().equals(hisId))
                .findFirst();
    	accInsurHis.remove(itemToBeDelete.get());
    	occAccIsPrRateRepository.remove(cId, hisId);
    	if (accInsurHis.getHistory().size() > 0 ){
    		YearMonthHistoryItem lastestItem = accInsurHis.getHistory().get(0);
    		accInsurHis.exCorrectToRemove(lastestItem);
    		occAccIsPrRateRepository.update(cId, lastestItem);
    	}
    }
    
    public void historyCorrectionProcecessing(String cId, String hisId, YearMonth start, YearMonth end){
    	OccAccIsHis accInsurHis = occAccIsPrRateRepository.getOccAccIsHisByCid(cId);
    	if (accInsurHis.getHistory() == null || accInsurHis.getHistory().isEmpty()) {
    		return;
    	}
    	Optional<YearMonthHistoryItem> itemToBeUpdate = accInsurHis.getHistory().stream()
				.filter(h -> h.identifier().equals(hisId)).findFirst();
    	if (!itemToBeUpdate.isPresent()) {
    		return;
    	}
    	accInsurHis.changeSpan(itemToBeUpdate.get(), new YearMonthPeriod(start, end));
    	this.updateAccInsurHis(itemToBeUpdate.get(), cId);
    	this.updateItemBefore(accInsurHis, itemToBeUpdate.get(), cId);
    }
    
    private void updateAccInsurHis(YearMonthHistoryItem itemToBeUpdated, String cId){
    	occAccIsPrRateRepository.update(cId, itemToBeUpdated);
    }
    
    private void updateItemBefore(OccAccIsHis accIsHis, YearMonthHistoryItem item, String cId){
		Optional<YearMonthHistoryItem> itemToBeUpdated = accIsHis.immediatelyBefore(item);
		if (!itemToBeUpdated.isPresent()){
			return;
		}
		occAccIsPrRateRepository.update(cId, itemToBeUpdated.get());
	}


}
