package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class EmpHealInsQualifiInfoService {

    @Inject
    private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

    @Inject
    private HealInsurNumberInforRepository healInsurNumberInforRepository;


    public void update(EmplHealInsurQualifiInfor domain, EmpHealthInsurBenefits itemToBeUpdate, HealInsurNumberInfor updateInfo){
        List<EmpHealthInsurBenefits> listHist = domain.getMourPeriod();
        int current = listHist.indexOf(itemToBeUpdate);
        emplHealInsurQualifiInforRepository.update(itemToBeUpdate, updateInfo);
        if (listHist.size() >= 1) {
            if (current > 0) {
                EmpHealthInsurBenefits itemBefore = listHist.get(current-1);
                itemBefore.changeSpan(new DatePeriod(itemToBeUpdate.end().addDays(+1), itemBefore.end()));
                emplHealInsurQualifiInforRepository.update(itemBefore);
            } else {
                EmpHealthInsurBenefits itemBefore = listHist.get(current+1);
                itemBefore.changeSpan(new DatePeriod(itemBefore.start(), itemToBeUpdate.start().addDays(-1)));
                emplHealInsurQualifiInforRepository.update(itemBefore);
            }
        }
    }

    public void add(EmplHealInsurQualifiInfor domain, EmpHealthInsurBenefits itemAdded, HealInsurNumberInfor hisItem){
        if (domain.getMourPeriod().isEmpty()){
            emplHealInsurQualifiInforRepository.add(domain, itemAdded, hisItem);
        } else {
            val item = domain.getMourPeriod().get(0);
            item.changeSpan(new DatePeriod(item.start(), item.end()));
            emplHealInsurQualifiInforRepository.add(domain, itemAdded, hisItem);
            emplHealInsurQualifiInforRepository.update(itemAdded);
        }
        update(domain, itemAdded, hisItem);
    }

    public void delete(EmplHealInsurQualifiInfor domain, EmpHealthInsurBenefits itemToBeDeleted){
        emplHealInsurQualifiInforRepository.remove(domain.getEmployeeId(), itemToBeDeleted.identifier());
    }
}
