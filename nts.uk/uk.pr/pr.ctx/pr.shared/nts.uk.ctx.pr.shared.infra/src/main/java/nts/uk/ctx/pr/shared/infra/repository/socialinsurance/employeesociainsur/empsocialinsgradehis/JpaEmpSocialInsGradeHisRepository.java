package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empsocialinsgradehis;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeHisRepository;
import javax.ejb.Stateless;

@Stateless
public class JpaEmpSocialInsGradeHisRepository extends JpaRepository implements EmpSocialInsGradeHisRepository{
}
