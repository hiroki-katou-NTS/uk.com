package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregQueryByListEmp;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainBySidDto;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainDto;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
@Stateless
public class EmpHealInsQualifiInfoFinder implements PeregFinder<EmpHealInsQualifiInfoDto> {
    @Inject
    private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

    @Inject
    private HealInsurNumberInforRepository healInsurNumberInforRepository;

    @Override
    public String targetCategoryCode() {
        return "CS00082";
    }

    @Override
    public Class<EmpHealInsQualifiInfoDto> dtoClass() {
        return EmpHealInsQualifiInfoDto.class;
    }

    @Override
    public DataClassification dataType() {
        return DataClassification.EMPLOYEE;
    }

    @Override
    public PeregDomainDto getSingleData(PeregQuery peregQuery) {
        if (peregQuery.getInfoId() == null) {
            val getEmpId = emplHealInsurQualifiInforRepository.getByEmpIdAndBaseDate(peregQuery.getEmployeeId(), peregQuery.getStandardDate());
            if (getEmpId.isPresent()){
                val firstItem = getEmpId.get().getMourPeriod().get(0);
                val firstItemInfo = healInsurNumberInforRepository.getHealInsurNumberInforById(peregQuery.getEmployeeId(), firstItem.identifier());
                return EmpHealInsQualifiInfoDto.createFromDomain(getEmpId.get(), firstItemInfo.get());
            } else {
                return null;
            }
        }
        val item = emplHealInsurQualifiInforRepository.getEmpHealInsQualifiinfoById(peregQuery.getEmployeeId(), peregQuery.getInfoId());
        val itemInfo = healInsurNumberInforRepository.getHealInsurNumberInforById(peregQuery.getEmployeeId(), peregQuery.getInfoId());
        if (item.isPresent() && itemInfo.isPresent()){
            EmpHealInsQualifiInfoDto exportItem = EmpHealInsQualifiInfoDto.createFromDomain(item.get(), itemInfo.get());
            return exportItem;
        }
        return null;
    }

    @Override
    public List<PeregDomainDto> getListData(PeregQuery peregQuery) {
        return null;
    }

    @Override
    public List<ComboBoxObject> getListFirstItems(PeregQuery peregQuery) {
        Optional<EmplHealInsurQualifiInfor> qualifiInfor = emplHealInsurQualifiInforRepository.getEmpHealInsQualifiinfoById(peregQuery.getEmployeeId());
        if (qualifiInfor.isPresent()){
            return qualifiInfor.get().getMourPeriod().stream()
                    .filter(e->healInsurNumberInforRepository.getHealInsurNumberInforById(peregQuery.getEmployeeId(), e.identifier()) != null)
                    .map(x->ComboBoxObject.toComboBoxObject(x.identifier(), x.start().toString(), x.end().equals(GeneralDate.max()) ? "" :x.end().toString()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<GridPeregDomainDto> getAllData(PeregQueryByListEmp peregQueryByListEmp) {
    	
        String cId = AppContexts.user().companyId();
        
        List<GridPeregDomainDto> result = new ArrayList<>();
        
        List<String> empIds = peregQueryByListEmp.getEmpInfos().stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
        
        peregQueryByListEmp.getEmpInfos().forEach(c -> {
        	
            result.add(new GridPeregDomainDto(c.getEmployeeId(), c.getPersonId(), null));
            
        });
        
        //histid, List<EmplHealInsurQualifiInfor>
        Map<String, List<EmplHealInsurQualifiInfor>> qualifiInforList  = 
        		
        		emplHealInsurQualifiInforRepository.getEmplHealInsurQualifiInfor(cId, empIds, peregQueryByListEmp.getStandardDate())
        		
                .stream().collect(Collectors.groupingBy(c-> c.getEmployeeId()));
        
        List<String> hisIds = new ArrayList<>();
        
        qualifiInforList.forEach((k, v) ->{
        	
        	if(!CollectionUtil.isEmpty(v)) {
        		
        		hisIds.add(v.get(0).getMourPeriod().get(0).identifier());
        		
        	}
        	
        });

        Map<String, List<HealInsurNumberInfor>> numberInfors = healInsurNumberInforRepository.findByHistoryId(hisIds)
        		
                .stream().collect(Collectors.groupingBy(c->c.getHistoryId()));

        result.stream().forEach(c->{
        	
        	List<EmplHealInsurQualifiInfor> emplHealInsurQualifiInfor = qualifiInforList.get(c.getEmployeeId());
        	
            if (!CollectionUtil.isEmpty(emplHealInsurQualifiInfor)){
            	
            	List<HealInsurNumberInfor> numberInfor = numberInfors.get(emplHealInsurQualifiInfor.get(0).getMourPeriod().get(0).identifier());
            	
                HealInsurNumberInfor healInsurNumberInfor = CollectionUtil.isEmpty(numberInfor) == true? null: numberInfor.get(0);
                
                c.setPeregDomainDto(EmpHealInsQualifiInfoDto.createFromDomain(emplHealInsurQualifiInfor.get(0), healInsurNumberInfor));
                
            }
            
        });
        
        return result;
    }

    @Override
    public List<GridPeregDomainBySidDto> getListData(PeregQueryByListEmp peregQueryByListEmp) {
        return null;
    }

}
