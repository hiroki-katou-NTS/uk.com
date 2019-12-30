package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
        /*int current = listHist.indexOf(itemToBeUpdate);*/
        if (listHist.size() >= 1) {
            emplHealInsurQualifiInforRepository.update(itemToBeUpdate, updateInfo);
            /**//*if (current <= 0) {*/
               /* EmpHealthInsurBenefits itemBefore = listHist.get(current+1);
                itemBefore.changeSpan(new DatePeriod(itemBefore.start(), itemToBeUpdate.start().addDays(-1)));
                emplHealInsurQualifiInforRepository.update(itemBefore);*/
               // Update before
                updateItem(domain,itemToBeUpdate);
            /*} else {*/
                updateItemAfter(domain,itemToBeUpdate);
                /*EmpHealthInsurBenefits itemBefore = listHist.get(current-1);
                itemBefore.changeSpan(new DatePeriod(itemToBeUpdate.end().addDays(+1), itemBefore.end()));
                emplHealInsurQualifiInforRepository.update(itemBefore);*/
            /*}*/
        }
    }
    public void updateItemAfter(EmplHealInsurQualifiInfor domain, EmpHealthInsurBenefits item){
        Optional<EmpHealthInsurBenefits> itemToBeUpdate = domain.immediatelyAfter(item);
        /*itemToBeUpdate.changeSpan(new DatePeriod(itemToBeUpdate.end().addDays(+1), itemBefore.end())*/
        if (!itemToBeUpdate.isPresent()){
            return;
        }
        itemToBeUpdate.get().shortenStartToAccept(item.getDatePeriod());
        emplHealInsurQualifiInforRepository.update(itemToBeUpdate.get());
    }
    public void delete(EmplHealInsurQualifiInfor domain, EmpHealthInsurBenefits itemToBeDeleted){
        emplHealInsurQualifiInforRepository.remove(domain.getEmployeeId(), itemToBeDeleted.identifier());
    }

    public void updateItem(EmplHealInsurQualifiInfor domain, EmpHealthInsurBenefits item){
    Optional<EmpHealthInsurBenefits> itemToBeUpdate = domain.immediatelyBefore(item);
    if (!itemToBeUpdate.isPresent()){
        return;
    }
    emplHealInsurQualifiInforRepository.update(itemToBeUpdate.get());
}
}
