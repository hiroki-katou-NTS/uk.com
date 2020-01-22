package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.shr.pereg.app.command.MyCustomizeException;

@Stateless
public class EmpHealInsQualifiInfoService {

    @Inject
    private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

    public void add(EmplHealInsurQualifiInfor domain, EmpHealthInsurBenefits itemAdded, HealInsurNumberInfor hisItem){
        EmpHealthInsurBenefits lastItem = domain.getMourPeriod().get(domain.getMourPeriod().size()-1);
        emplHealInsurQualifiInforRepository.add(domain, lastItem, hisItem);
        updateItem(domain, lastItem);
    }
    
    
    public void addAll(List<EmplHealInsurQualifiInforParams> params) {
    	
    	List<EmplHealInsurQualifiInforParams> lastItems = new ArrayList<>();
    	
    	params.stream().forEach(c ->{
    		
    		EmpHealthInsurBenefits lastItem = c.getDomain().getMourPeriod().get(c.getDomain().getMourPeriod().size()-1);
    		
    		lastItems.add(new EmplHealInsurQualifiInforParams(c.getCid(), lastItem, c.getHisItem(), c.getDomain()));
    	
    	});
    	
    	emplHealInsurQualifiInforRepository.addAll(lastItems);
    
    	updateItemAll(lastItems);
    	
    	 
    }

    public void update(EmplHealInsurQualifiInfor domain, EmpHealthInsurBenefits itemToBeUpdate, HealInsurNumberInfor updateInfo){
        List<EmpHealthInsurBenefits> listHist = domain.getMourPeriod();
        if (listHist.size() >= 1) {
            emplHealInsurQualifiInforRepository.update(itemToBeUpdate, updateInfo,domain.getEmployeeId());
            // Update Before
            updateItem(domain,itemToBeUpdate);
            // Update After
            updateItemAfter(domain,itemToBeUpdate);

        }
    }
    
    public List<MyCustomizeException> updateAll(List<EmplHealInsurQualifiInforParams> params){
    	
    	List<EmplHealInsurQualifiInforParams> itemToBeUpdates = new ArrayList<>();
    	
    	List<MyCustomizeException> result = new ArrayList<>();
    	
    	params.stream().forEach(c ->{
    		
    		List<EmpHealthInsurBenefits> listHist = c.getDomain().getMourPeriod();
    		
            if (listHist.size() >= 1) {
            	
            	itemToBeUpdates.add(c);

            }
            
    	});
    	
    	if(!itemToBeUpdates.isEmpty()) {
    		
    		emplHealInsurQualifiInforRepository.updateAllDomain(itemToBeUpdates);
    		
    		updateItemAll(itemToBeUpdates);
    		
    		List<MyCustomizeException> ex = updateItemAfterAll(itemToBeUpdates);
    		
    		if(!ex.isEmpty()) {
    			
    			result.addAll(ex);
    			
    		}
    		
    	}
    	
    	return result;
    }
    
    public void updateItemAfter(EmplHealInsurQualifiInfor domain, EmpHealthInsurBenefits item){
        Optional<EmpHealthInsurBenefits> itemToBeUpdate = domain.immediatelyAfter(item);
        if (!itemToBeUpdate.isPresent()){
            return;
        }
        // 終了日は直後の履歴の終了日より以降になっている→エラーMsg_538
        boolean validEnd = item.span().isEnd().before(itemToBeUpdate.get().end());
        if (!validEnd) {
            throw new BusinessException("Msg_538");
        }
        itemToBeUpdate.get().shortenStartToAccept(item.getDatePeriod());
        emplHealInsurQualifiInforRepository.update(itemToBeUpdate.get());
    }
    
	public List<MyCustomizeException> updateItemAfterAll(List<EmplHealInsurQualifiInforParams> params) {

		List<MyCustomizeException> exceptions = new ArrayList<>();

		List<EmpHealthInsurBenefits> itemToBeUpdates = new ArrayList<>();

		params.stream().forEach(c -> {

			Optional<EmpHealthInsurBenefits> itemToBeUpdate = c.getDomain().immediatelyAfter(c.getItemAdded());

			if (itemToBeUpdate.isPresent()) {

				// 終了日は直後の履歴の終了日より以降になっている→エラーMsg_538
				boolean validEnd = c.getItemAdded().span().isEnd().before(itemToBeUpdate.get().end());

				if (!validEnd) {

					exceptions.add(new MyCustomizeException("Msg_538", Arrays.asList(c.getDomain().getEmployeeId())));

				} else {

					itemToBeUpdate.get().shortenStartToAccept(c.getItemAdded().getDatePeriod());

					itemToBeUpdates.add(itemToBeUpdate.get());
				}
			}

		});

		if (!itemToBeUpdates.isEmpty()) {

			emplHealInsurQualifiInforRepository.updateAllStartEndDate(itemToBeUpdates);

		}

		return exceptions;
	}
    
    
    public void delete(EmplHealInsurQualifiInfor domain, EmpHealthInsurBenefits itemToBeDeleted){
        emplHealInsurQualifiInforRepository.remove(domain.getEmployeeId(), itemToBeDeleted.identifier());
    }

    public void updateItem(EmplHealInsurQualifiInfor domain, EmpHealthInsurBenefits item) {
        Optional<EmpHealthInsurBenefits> itemToBeUpdate = domain.immediatelyBefore(item);
        if (!itemToBeUpdate.isPresent()) {
            return;
        }
        emplHealInsurQualifiInforRepository.update(itemToBeUpdate.get());
    }
    
	public void updateItemAll(List<EmplHealInsurQualifiInforParams> lastItems) {

		List<EmpHealthInsurBenefits> itemToBeUpdates = new ArrayList<>();

		lastItems.stream().forEach(c -> {

			Optional<EmpHealthInsurBenefits> itemToBeUpdate = c.getDomain().immediatelyBefore(c.getItemAdded());

			if (itemToBeUpdate.isPresent()) {

				itemToBeUpdates.add(itemToBeUpdate.get());

			}

		});

		if (!itemToBeUpdates.isEmpty()) {

			emplHealInsurQualifiInforRepository.updateAllStartEndDate(itemToBeUpdates);

		}
	}
}
