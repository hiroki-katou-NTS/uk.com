package nts.uk.ctx.pr.core.dom.laborinsurance;

import java.util.ArrayList;
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
    private OccAccIsHisRepository occAccIsHisRepository;
    
    @Inject
    private OccAccIsPrRateRepository occAccIsPrRateRepository;



    /*
    * 初期データ取得処理
    * */
    public Optional<OccAccIsHis> initDataAcquisition(String cId){

        /*
        *ドメインモデル「労災保険履歴」を全て取得する
        * */
        Optional<OccAccIsHis> getEmpInsurHisByCid = occAccIsHisRepository.getAllOccAccIsHisByCid(cId);
        if(getEmpInsurHisByCid.get().getHistory().isEmpty() ){
            /*選択処理*/
          return Optional.ofNullable(new OccAccIsHis(cId,new ArrayList<>()));
        }
        return getEmpInsurHisByCid;


    }

    public Optional<OccAccInsurBus> getOccAccInsurBus(String companyId){
        Optional<OccAccInsurBus> occAccInsurBus =  occAccInsurBusRepository.getOccAccInsurBus(companyId);
        return occAccInsurBus;
    }
    
    public void historyDeletionProcessing(String hisId, String cId){
    	Optional<OccAccIsHis> accInsurHis = occAccIsHisRepository.getAllOccAccIsHisByCid(cId);
    	if (!accInsurHis.isPresent()) {
    		throw new RuntimeException("invalid employmentHistory"); 
    	}
    	Optional<YearMonthHistoryItem> itemToBeDelete = accInsurHis.get().getHistory().stream()
                .filter(h -> h.identifier().equals(hisId))
                .findFirst();
    	accInsurHis.get().remove(itemToBeDelete.get());
    	occAccIsPrRateRepository.remove(hisId);
    	occAccIsHisRepository.remove(cId, hisId);
    	if (accInsurHis.get().getHistory().size() > 0 ){
    		YearMonthHistoryItem lastestItem = accInsurHis.get().getHistory().get(0);
    		accInsurHis.get().exCorrectToRemove(lastestItem);
    		occAccIsHisRepository.update(lastestItem, cId);
    	}
    }
    
    public void historyCorrectionProcecessing(String cId, String hisId, YearMonth start, YearMonth end){
    	Optional<OccAccIsHis> accInsurHis = occAccIsHisRepository.getAllOccAccIsHisByCid(cId);
    	if (!accInsurHis.isPresent()) {
    		return;
    	}
    	Optional<YearMonthHistoryItem> itemToBeUpdate = accInsurHis.get().getHistory().stream()
				.filter(h -> h.identifier().equals(hisId)).findFirst();
    	if (!itemToBeUpdate.isPresent()) {
    		return;
    	}
    	accInsurHis.get().changeSpan(itemToBeUpdate.get(), new YearMonthPeriod(start, end));
    	this.updateAccInsurHis(itemToBeUpdate.get(), cId);
    	this.updateItemBefore(accInsurHis.get(), itemToBeUpdate.get(), cId);
    }
    
    private void updateAccInsurHis(YearMonthHistoryItem itemToBeUpdated, String cId){
    	occAccIsHisRepository.update(itemToBeUpdated, cId);
    }
    
    private void updateItemBefore(OccAccIsHis accIsHis, YearMonthHistoryItem item, String cId){
		Optional<YearMonthHistoryItem> itemToBeUpdated = accIsHis.immediatelyBefore(item);
		if (!itemToBeUpdated.isPresent()){
			return;
		}
		occAccIsHisRepository.update(itemToBeUpdated.get(), cId);
	}


}
