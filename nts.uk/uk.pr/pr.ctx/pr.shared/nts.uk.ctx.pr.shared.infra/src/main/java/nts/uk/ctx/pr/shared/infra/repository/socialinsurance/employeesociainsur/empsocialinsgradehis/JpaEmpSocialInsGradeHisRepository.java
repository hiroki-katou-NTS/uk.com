package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empsocialinsgradehis;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeHisRepository;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaEmpSocialInsGradeHisRepository extends JpaRepository implements EmpSocialInsGradeHisRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsdtEmpSocialInsGradeHis f";

    @Override
    public void add(EmpSocialInsGradeHis domain) {

    }

    @Override
    public void update(EmpSocialInsGradeHis domain) {

    }

    @Override
    public void remove(EmpSocialInsGradeHis domain) {

    }

    @Override
    public void remove(String sId) {

    }

    @Override
    public void remove(String sId, String histId) {

    }

    @Override
    public List<EmpSocialInsGradeHis> getAllEmpSocialInsGradeHis() {
        return null;
    }

    @Override
    public Optional<EmpSocialInsGradeHis> getEmpSocialInsGradeHisById(String employeeId, GeneralDate basDate) {
        return Optional.empty();
    }

    @Override
    public Optional<EmpSocialInsGradeHis> getEmpSocialInsGradeHisById(String employeeId, String hisId) {
        return Optional.empty();
    }

    @Override
    public Optional<EmpSocialInsGradeHis> getEmpSocialInsGradeHisById(String employeeId) {
        return Optional.empty();
    }

    @Override
    public Optional<EmpSocialInsGradeHis> getEmpSocialInsGradeHisById(List<String> employeeIds, GeneralDate startDate) {
        return Optional.empty();
    }
}
