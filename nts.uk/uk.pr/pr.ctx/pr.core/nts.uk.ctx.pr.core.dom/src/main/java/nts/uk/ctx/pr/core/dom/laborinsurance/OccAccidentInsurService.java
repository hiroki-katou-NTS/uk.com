package nts.uk.ctx.pr.core.dom.laborinsurance;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;

import java.util.List;
import java.util.Optional;


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
        * ドメインモデル「労災保険事業」を全て取得する
        * */
        Optional<OccAccInsurBus> acceptCode = occAccInsurBusRepository.getOccAccInsurBus(cId);
        /*
        *ドメインモデル「労災保険履歴」を全て取得する
        * */
        Optional<OccAccIsHis> getEmpInsurHisByCid = occAccIsHisRepository.getAllOccAccIsHisByCid(cId);
        if(acceptCode.get().getEachBusiness() == null || getEmpInsurHisByCid == null ){
            /*選択処理*/
          return null;


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
    	this.updateAccInsurHis(accInsurHis.get());
    	this.updateItemBefore(accInsurHis.get(), itemToBeUpdate.get(), cId);
    }
    
    private void updateAccInsurHis(OccAccIsHis itemToBeUpdated){
    	occAccIsHisRepository.update(itemToBeUpdated.getHistory().get(itemToBeUpdated.getHistory().size() - 1), itemToBeUpdated.getCid());
    }
    
    private void updateItemBefore(OccAccIsHis accIsHis, YearMonthHistoryItem item, String cId){
		Optional<YearMonthHistoryItem> itemToBeUpdated = accIsHis.immediatelyBefore(item);
		if (!itemToBeUpdated.isPresent()){
			return;
		}
		occAccIsHisRepository.update(itemToBeUpdated.get(), cId);
	}


}
