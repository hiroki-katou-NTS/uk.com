package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empsocialinsgradehis;

import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.*;
import nts.uk.shr.com.history.YearMonthHistoryItem;
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
import java.util.List;
import java.util.Optional;

@Stateless
public class EmpSocialInsGradeInforFinder implements PeregFinder<EmpSocialInsGradeInforDto> {

    @Inject
    EmpSocialInsGradeHisRepository esighFinder;

    @Inject
    EmpSocialInsGradeInfoRepository esigiFinfer;

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
        if (peregQuery.getInfoId() == null) {
            Optional<EmpSocialInsGradeHis> domain = esighFinder.getEmpSocialInsGradeHisBySId(peregQuery.getEmployeeId());
            if (!domain.isPresent()) {
                return null;
            }
            YearMonthHistoryItem period = domain.get().getPeriod().get(0);
            if (period == null) {
                return null;
            }
            EmpSocialInsGradeInfo info = esigiFinfer.getEmpSocialInsGradeInfoByHistId(period.identifier()).get();

            return EmpSocialInsGradeInforDto.fromDomain(domain.get(), info);
        } else {
            EmpSocialInsGradeInfo info = esigiFinfer.getEmpSocialInsGradeInfoByHistId(peregQuery.getInfoId()).get();
            EmpSocialInsGradeHis domain = esighFinder.getEmpSocialInsGradeHisByHistId(peregQuery.getInfoId()).get();

            return  EmpSocialInsGradeInforDto.fromDomain(domain, info);
        }
    }

    @Override
    public List<PeregDomainDto> getListData(PeregQuery peregQuery) {
        return null;
    }

    @Override
    public List<ComboBoxObject> getListFirstItems(PeregQuery peregQuery) {
        return null;
    }

    @Override
    public List<GridPeregDomainDto> getAllData(PeregQueryByListEmp peregQueryByListEmp) {
        return null;
    }

    @Override
    public List<GridPeregDomainBySidDto> getListData(PeregQueryByListEmp peregQueryByListEmp) {
        return null;
    }
}
