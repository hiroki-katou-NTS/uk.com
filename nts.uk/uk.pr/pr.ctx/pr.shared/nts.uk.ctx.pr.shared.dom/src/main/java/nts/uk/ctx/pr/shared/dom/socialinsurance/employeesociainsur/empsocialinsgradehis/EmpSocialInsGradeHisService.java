package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis;

import nts.uk.shr.com.history.YearMonthHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class EmpSocialInsGradeHisService {
    @Inject
    private EmpSocialInsGradeHisRepository empSocialInsGradeHisRepository;

    /**
     *
     * @param domain
     */
    public void add(EmpSocialInsGradeHis domain){
        if (domain.getYearMonthHistoryItems().isEmpty()) {
            return;
        }
        // Insert last element
        YearMonthHistoryItem lastItem = domain.getYearMonthHistoryItems().get(domain.getYearMonthHistoryItems().size() - 1);
        empSocialInsGradeHisRepository.add(domain.getCompanyId(), domain.getEmployeeId(), lastItem);
    }

    /**
     *
     * @param item
     */
    public void update(YearMonthHistoryItem item){
        empSocialInsGradeHisRepository.update(item);
    }

    /**
     *
     * @param domain
     */
    public void delete(EmpSocialInsGradeHis domain, YearMonthHistoryItem item){
        empSocialInsGradeHisRepository.delete(item.identifier());
    }
}
