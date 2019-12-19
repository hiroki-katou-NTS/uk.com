package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis;

import java.util.List;
import java.util.Optional;

/**
 * 社員社会保険等級情報
 */
public interface EmpSocialInsGradeInfoRepository {

    void add(EmpSocialInsGradeInfo domain);
    void update(EmpSocialInsGradeInfo domain);
    void delete(String histId);

    Optional<EmpSocialInsGradeInfo> getEmpSocialInsGradeInfoByHistId(String histId);
    List<EmpSocialInsGradeInfo> getByHistIds(List<String> histIds);
}
