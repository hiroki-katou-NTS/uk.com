package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empsocialinsgradehis;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.GeneralHistoryItem;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregEmpInfoQuery;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregQueryByListEmp;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainBySidDto;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainDto;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Stateless
public class EmpSocialInsGradeInforFinder implements PeregFinder<EmpSocialInsGradeInforDto> {

    @Inject
    private EmpSocialInsGradeHisRepository esighFinder;

    @Inject
    private EmpSocialInsGradeInfoRepository esigiFinder;

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
            Optional<EmpSocialInsGradeHis> domain = esighFinder.getEmpSocialInsGradeHisBySId(companyId, peregQuery.getEmployeeId());
            if (!domain.isPresent()) {
                return null;
            }
            YearMonthHistoryItem period = domain.get().getYearMonthHistoryItems().get(0);
            if (period == null) {
                return null;
            }
            EmpSocialInsGradeInfo info = esigiFinder.getEmpSocialInsGradeInfoByHistId(period.identifier()).orElse(null);
            String currentGrade = service.getCurrentGrade(domain.get(), peregQuery.getStandardDate());
            return EmpSocialInsGradeInforDto.fromDomain(domain.get(), info, currentGrade);
        } else {
            EmpSocialInsGradeInfo info = esigiFinder.getEmpSocialInsGradeInfoByHistId(peregQuery.getInfoId()).orElse(null);
            EmpSocialInsGradeHis domain = esighFinder.getEmpSocialInsGradeHisByHistId(peregQuery.getInfoId()).orElse(null);
            String currentGrade = service.getCurrentGrade(domain, peregQuery.getStandardDate());
            return EmpSocialInsGradeInforDto.fromDomain(domain, info, currentGrade);
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
    public List<GridPeregDomainDto> getAllData(PeregQueryByListEmp query) {
        List<GridPeregDomainDto> result = new ArrayList<>();
        GeneralDate baseDate = query.getStandardDate();
        List<String> sids = query.getEmpInfos().stream().map(PeregEmpInfoQuery::getEmployeeId).collect(Collectors.toList());

        Map<String, EmpSocialInsGradeHis> histories = esighFinder.getBySidsAndBaseYM(sids, baseDate.yearMonth())
                .stream().collect(Collectors.toMap(EmpSocialInsGradeHis::getEmployeeId, Function.identity()));

        List<YearMonthHistoryItem> histItems = histories.values().stream().map(e -> e.items().get(0)).collect(Collectors.toList());
        List<String> histIds = histItems.stream().map(GeneralHistoryItem::identifier).collect(Collectors.toList());
        Map<String, EmpSocialInsGradeInfo> infos = esigiFinder.getByHistIds(histIds)
                .stream().collect(Collectors.toMap(EmpSocialInsGradeInfo::getHistId, Function.identity()));

        // 選択履歴が次回給与適用等級か判定する
        Map<String, String> currentGrades = service.getMapCurrentGrade(histories, baseDate);

        query.getEmpInfos().forEach(c -> {
            EmpSocialInsGradeHis history = histories.get(c.getEmployeeId());
            EmpSocialInsGradeInfo info = null;
            String currentGrade = "";
            if (history != null && !history.items().isEmpty()) {
                String histId = history.items().get(0).identifier();
                info = infos.get(histId);
                currentGrade = currentGrades.get(c.getEmployeeId());
            }
            EmpSocialInsGradeInforDto dto = EmpSocialInsGradeInforDto.fromDomain(history, info, currentGrade);
            result.add(new GridPeregDomainDto(c.getEmployeeId(), c.getPersonId(), dto));
        });
        return result;
    }

    @Override
    public List<GridPeregDomainBySidDto> getListData(PeregQueryByListEmp peregQueryByListEmp) {
        return null;
    }

}
