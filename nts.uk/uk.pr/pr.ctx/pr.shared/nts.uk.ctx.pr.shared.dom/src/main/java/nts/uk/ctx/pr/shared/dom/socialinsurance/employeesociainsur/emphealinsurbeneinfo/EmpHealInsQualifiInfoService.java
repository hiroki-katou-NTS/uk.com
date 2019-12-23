package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

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

    public void update(EmplHealInsurQualifiInfor domain, EmpHealthInsurBenefits itemToBeUpdate){
        emplHealInsurQualifiInforRepository.update(itemToBeUpdate);
        updateItemBefore(domain, itemToBeUpdate);
    }

    public void updateAll(List<HealInsQualifiAndNumber> qualifiAndNumbers){
        emplHealInsurQualifiInforRepository.updateAll(qualifiAndNumbers.stream().map(c -> c.getItem()).collect(Collectors.toList()));
        updateAllItem(qualifiAndNumbers);
    }

    private void updateAllItem(List<HealInsQualifiAndNumber> qualifiAndNumbers){
        List<EmpHealthInsurBenefits> items = new ArrayList<>();
        qualifiAndNumbers.stream().forEach(c -> {
            Optional<EmpHealthInsurBenefits> itemUpdate = c.getDomain().immediatelyBefore(c.getItem());
            if (itemUpdate.isPresent()){
                items.add(itemUpdate.get());
            }
        });
        if (items.isEmpty()){
            emplHealInsurQualifiInforRepository.updateAll(items);
        }
    }

    public void add(EmplHealInsurQualifiInfor domain){
        EmpHealthInsurBenefits itemToBeAdded = domain.getMourPeriod().get(domain.getMourPeriod().size()-1);
        emplHealInsurQualifiInforRepository.add(domain.getEmployeeId(), itemToBeAdded);
    }

    public void addAll (List<HealInsQualifiAndNumber> domains){
        Map<String, EmpHealthInsurBenefits> healthInsMap = new HashMap<>();
        domains.stream().forEach(c->{
            EmplHealInsurQualifiInfor qualifiInfor = c.getDomain();
            if (qualifiInfor.getMourPeriod().isEmpty()){
                return;
            }
            EmpHealthInsurBenefits lastItem = qualifiInfor.getMourPeriod().get(qualifiInfor.getMourPeriod().size()-1);
            healthInsMap.put(qualifiInfor.getEmployeeId(), lastItem);
        });
        emplHealInsurQualifiInforRepository.addAll(healthInsMap);
        updateItemBefore(domains);

    }

    private void updateItemBefore(EmplHealInsurQualifiInfor domain, EmpHealthInsurBenefits item){
        Optional<EmpHealthInsurBenefits> itemToBeUpdated = domain.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        emplHealInsurQualifiInforRepository.update(itemToBeUpdated.get());
    }

    private void updateItemBefore(List<HealInsQualifiAndNumber> domains){
        List<EmpHealthInsurBenefits> itemToBeUpdateLst = new ArrayList<>();
        domains.stream().forEach(c->{
            Optional<EmpHealthInsurBenefits> itemToBeUpdate = c.getDomain().immediatelyBefore(c.getItem());
            if (itemToBeUpdate.isPresent()){
                itemToBeUpdateLst.add(itemToBeUpdate.get());
            }
        });
        if (!itemToBeUpdateLst.isEmpty()){
            return;
        }
        emplHealInsurQualifiInforRepository.updateAll(itemToBeUpdateLst);
    }

    public void delete(EmplHealInsurQualifiInfor domain, EmpHealthInsurBenefits itemToBeDeleted){
        emplHealInsurQualifiInforRepository.remove(domain.getEmployeeId(), itemToBeDeleted.identifier());
    }
}
