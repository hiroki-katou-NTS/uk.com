package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.error.BusinessException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;

@Stateless
public class EmpHealInsQualifiInfoService {

    @Inject
    private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

    public void add(EmplHealInsurQualifiInfor domain, EmpHealthInsurBenefits itemAdded, HealInsurNumberInfor hisItem){
        EmpHealthInsurBenefits lastItem = domain.getMourPeriod().get(domain.getMourPeriod().size()-1);
        emplHealInsurQualifiInforRepository.add(domain, lastItem, hisItem);
        updateItem(domain, lastItem);
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
}
