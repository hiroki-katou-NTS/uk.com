package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empsocialinsgradehis;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGrade;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeService;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class EmpSocialInsGradeInforFinder implements PeregFinder<EmpSocialInsGradeInforDto> {

    @Inject
    private EmpSocialInsGradeRepository repository;

    @Inject
    private EmpSocialInsGradeService service;

    @Override
    public String targetCategoryCode() {
        return "CS00092";
    }

    @Override
    public Class<EmpSocialInsGradeInforDto> dtoClass() {
        return EmpSocialInsGradeInforDto.class;
    }

    @Override
    public DataClassification dataType() {
        return DataClassification.EMPLOYEE;
    }

    @Override
    public EmpSocialInsGradeInforDto getSingleData(PeregQuery peregQuery) {
        String companyId = AppContexts.user().companyId();
        if (peregQuery.getInfoId() == null) {
            Optional<EmpSocialInsGrade> domain = repository.getByEmpIdAndBaseDate(companyId, peregQuery.getEmployeeId(), peregQuery.getStandardDate());
            if (!domain.isPresent()) {
                return null;
            }
            String currentGrade = service.getCurrentGrade(domain.get().getHistory(), peregQuery.getStandardDate() != null ? peregQuery.getStandardDate() : GeneralDate.today());
            return EmpSocialInsGradeInforDto.fromDomain(domain.get().getHistory(), domain.get().getInfos().get(0), currentGrade);
        } else {
            Optional<EmpSocialInsGrade> domain = repository.getByKey(companyId, peregQuery.getEmployeeId(), peregQuery.getInfoId());
            String currentGrade = service.getCurrentGrade(domain.get().getHistory(), peregQuery.getStandardDate() != null ? peregQuery.getStandardDate() : GeneralDate.today());
            return EmpSocialInsGradeInforDto.fromDomain(domain.get().getHistory(), domain.get().getInfos().get(0), currentGrade);
        }

    }

    @Override
    public List<PeregDomainDto> getListData(PeregQuery peregQuery) {
        return null;
    }

    @Override
    public List<ComboBoxObject> getListFirstItems(PeregQuery query) {
        String companyId = AppContexts.user().companyId();
        Optional<EmpSocialInsGrade> domain = repository.getByEmpId(companyId, query.getEmployeeId());
        if (domain.isPresent()) {
            return domain.get().getHistory().items()
                    .stream()
                    .map(x -> ComboBoxObject.toComboBoxObject(x.identifier(), x.start().toString().length() == 6 ? x.start().toString().substring(0, 4) + "/" + x.start().toString().substring(4) : x.start().toString(),
                            x.end().equals(GeneralDate.max().yearMonth())
                                    ? "" : x.end().toString().length() == 6 ? x.end().toString().substring(0, 4) + "/" + x.end().toString().substring(4) : x.end().toString()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<GridPeregDomainDto> getAllData(PeregQueryByListEmp query) {
        return null;
    }

    @Override
    public List<GridPeregDomainBySidDto> getListData(PeregQueryByListEmp peregQueryByListEmp) {
        return null;
    }

}
