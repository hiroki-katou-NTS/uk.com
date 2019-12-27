package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empcomofficehis;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformationRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisService;
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
public class EmpCorpHealthOffHisFinder implements PeregFinder<EmpCorpHealthOffHisDto> {

    @Inject
    private EmpCorpHealthOffHisRepository empCorpHealthOffHisRepository;

    @Inject
    private AffOfficeInformationRepository affOfficeInformationRepository;

    @Inject
    private EmpCorpHealthOffHisService empCorpHealthOffHisService;

    @Override
    public String targetCategoryCode() {
        return "CS00075";
    }

    @Override
    public Class<EmpCorpHealthOffHisDto> dtoClass() {
        return EmpCorpHealthOffHisDto.class;
    }

    @Override
    public DataClassification dataType() {
        return DataClassification.EMPLOYEE;
    }

    @Override
    public PeregDomainDto getSingleData(PeregQuery peregQuery) {
        if (peregQuery.getInfoId() == null) {
            val getBySid = empCorpHealthOffHisRepository.getBySidAndBaseDate(peregQuery.getEmployeeId(), peregQuery.getStandardDate());
            if (getBySid.isPresent()){
                val firstItem = getBySid.get().getPeriod().get(0);
                val firstItemInfo = affOfficeInformationRepository.getAffOfficeInformationById(peregQuery.getEmployeeId(), firstItem.identifier());
                return EmpCorpHealthOffHisDto.createFromDomain(getBySid.get(), firstItemInfo.get());
            } else {
                return null;
            }
        }
        val item = empCorpHealthOffHisRepository.getEmpCorpHealthOffHisById(peregQuery.getEmployeeId(), peregQuery.getInfoId());
        val itemInfo = affOfficeInformationRepository.getAffOfficeInformationById(peregQuery.getEmployeeId(), peregQuery.getInfoId());
        if (item.isPresent() && itemInfo.isPresent()){
            EmpCorpHealthOffHisDto exportItem = EmpCorpHealthOffHisDto.createFromDomain(item.get(), itemInfo.get());
            return exportItem;
        }
        return null;
    }

    @Override
    public List<PeregDomainDto> getListData(PeregQuery peregQuery) {
//        val listItem = empCorpHealthOffHisRepository.getEmpCorpHealthOffHisById(peregQuery.getEmployeeId());
//        List<PeregDomainDto> result = new ArrayList<>();
//        if (listItem.isPresent()) {
//            List<String> listHisIds = listItem.get().getPeriod().stream().map(e->e.identifier()).collect(Collectors.toList());
//            Map<String, String> mapInfo = affOfficeInformationRepository.getByHistIds(listHisIds).stream().collect(Collectors.toMap(e->e.getHistoryId(), e -> e.getSocialInsurOfficeCode().v()));
//
//            listItem.get().getPeriod().forEach( e-> {
//                if (mapInfo.containsKey(e.identifier())){
//                    val itemInfo = mapInfo.get(e.identifier());
//                    EmpCorpHealthOffHisDto item = new EmpCorpHealthOffHisDto(
//                            peregQuery.getEmployeeId(),
//                            e.start(), e.end(), itemInfo
//                    );
//                    result.add(item);
//                }
//            });
//        }
        return null;
    }

    @Override
    public List<ComboBoxObject> getListFirstItems(PeregQuery peregQuery) {
        Optional<EmpCorpHealthOffHis> empCorp = empCorpHealthOffHisRepository.getBySidDesc(peregQuery.getEmployeeId());
        if (empCorp.isPresent()) {
            return empCorp.get().getPeriod().stream()
                    .filter(x -> affOfficeInformationRepository.getAffOfficeInformationById(peregQuery.getEmployeeId(),x.identifier()).isPresent())
                    .map(x -> ComboBoxObject.toComboBoxObject(x.identifier(), x.start().toString(),
                            x.end().equals(GeneralDate.max())
                                    //&& query.getCtgType() == 3
                                    ? "" :x.end().toString()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<GridPeregDomainDto> getAllData(PeregQueryByListEmp query) {

//        List<GridPeregDomainDto> result = new ArrayList<>();
//        List<String> sids = query.getEmpInfos().stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
//        val empCorpList = empCorpHealthOffHisRepository.getBySidsAndBaseDate(sids, query.getStandardDate());
//        List<String> listFirstHisIds = new ArrayList<>();
//        query.getEmpInfos().stream().forEach(e -> {
//            result.add(new GridPeregDomainDto(e.getEmployeeId(), e.getPersonId(), null));
//            val itemPerId = empCorpList.stream().filter( item -> item.getEmployeeId().equalsIgnoreCase(e.getEmployeeId())).findFirst();
//            if (itemPerId.isPresent()) {
//                listFirstHisIds.add(itemPerId.get().getPeriod().get(0).identifier());
//            }
//        });
//
//        Map<String, String> affOfficeInfos = affOfficeInformationRepository.getByHistIds(listFirstHisIds).stream().collect(Collectors.toMap(e -> e.getHistoryId(), e -> e.getSocialInsurOfficeCode().v()));
//
//        result.stream().forEach( e-> {
//            val empCorpHealOffOpt = empCorpList.stream()
//                    .filter(item -> item.getEmployeeId().equals(e.getEmployeeId())).findFirst();
//            if (empCorpHealOffOpt.isPresent()) {
//                val affOfficeOpt = affOfficeInfos.get(empCorpHealOffOpt.get().getPeriod().get(0).identifier());
//                EmpCorpHealthOffHisDto item = new EmpCorpHealthOffHisDto(
//                        empCorpHealOffOpt.get().getEmployeeId(),
//                        empCorpHealOffOpt.get().getPeriod().get(0).start(),
//                        empCorpHealOffOpt.get().getPeriod().get(0).end(),
//                        affOfficeOpt
//                );
//                e.setPeregDomainDto(item);
//            }
//        });
//
//        return result;
        return null;
    }

    @Override
    public List<GridPeregDomainBySidDto> getListData(PeregQueryByListEmp peregQueryByListEmp) {
        return null;
    }
}
