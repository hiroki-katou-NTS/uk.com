package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empsocialinsgradehis;

import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.*;
import nts.uk.shr.com.context.AppContexts;
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
    EmpSocialInsGradeInfoRepository esigiFinder;

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
            Optional<EmpSocialInsGradeHis> domain = esighFinder.getEmpSocialInsGradeHisBySId(companyId, peregQuery.getEmployeeId());
            if (!domain.isPresent()) {
                return null;
            }
            YearMonthHistoryItem period = domain.get().getYearMonthHistoryItems().get(0);
            if (period == null) {
                return null;
            }
            EmpSocialInsGradeInfo info = esigiFinder.getEmpSocialInsGradeInfoByHistId(period.identifier()).orElse(null);

            return EmpSocialInsGradeInforDto.fromDomain(domain.get(), info);
        } else {
            EmpSocialInsGradeInfo info = esigiFinder.getEmpSocialInsGradeInfoByHistId(peregQuery.getInfoId()).orElse(null);
            EmpSocialInsGradeHis domain = esighFinder.getEmpSocialInsGradeHisByHistId(peregQuery.getInfoId()).orElse(null);

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
