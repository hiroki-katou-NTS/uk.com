package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.List;
import java.util.Optional;

/**
 * 社員社会保険等級履歴
 */
public interface EmpSocialInsGradeHisRepository {

    void add(String cid, String sid, YearMonthHistoryItem item);

    void update(YearMonthHistoryItem domain);

    /**
     *
     * @param histId
     */
    void delete(String histId);

    void remove(EmpSocialInsGradeHis domain);

    void remove(String sId);

    void remove(String sId, String histId);

    List<EmpSocialInsGradeHis> getAllEmpSocialInsGradeHis();

    Optional<EmpSocialInsGradeHis> getEmpSocialInsGradeHisBySId(String cid, String employeeId);

    Optional<EmpSocialInsGradeHis> getEmpSocialInsGradeHisById(String employeeId, String hisId);

    Optional<EmpSocialInsGradeHis> getEmpSocialInsGradeHisByHistId(String histId);

    Optional<EmpSocialInsGradeHis> getEmpSocialInsGradeHisById(List<String> employeeIds, GeneralDate startDate);
}
