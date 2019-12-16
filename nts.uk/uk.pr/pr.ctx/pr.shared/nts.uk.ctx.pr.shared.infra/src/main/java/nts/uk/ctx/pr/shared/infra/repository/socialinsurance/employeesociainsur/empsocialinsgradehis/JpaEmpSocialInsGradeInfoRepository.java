package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empsocialinsgradehis;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeInfo;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeInfoRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empsocialinsgradehis.QqsmpEmpSocialInsGradeInfo;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaEmpSocialInsGradeInfoRepository extends JpaRepository implements EmpSocialInsGradeInfoRepository {
    @Override
    public void add(EmpSocialInsGradeInfo domain) {

    }

    @Override
    public void update(EmpSocialInsGradeInfo domain) {

    }

    @Override
    public void remove(EmpSocialInsGradeInfo domain) {

    }

    @Override
    public void remove(String histId) {

    }

    @Override
    public Optional<EmpSocialInsGradeInfo> getEmpSocialInsGradeInfoByHistId(String histId) {
        Optional<QqsmpEmpSocialInsGradeInfo> gradeInfo = this.queryProxy().find(histId, QqsmpEmpSocialInsGradeInfo.class);
        if (gradeInfo.isPresent()) {
            return Optional.of(toDomain(gradeInfo.get()));
        }
        return Optional.empty();
    }

    private EmpSocialInsGradeInfo toDomain(QqsmpEmpSocialInsGradeInfo entity) {
        return entity.toDomain();
    }
}
