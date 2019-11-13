package nts.uk.file.pr.infra.core.empinsqualifiinfo.empinsqualifinfo;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.file.app.core.empinsqualifinfo.empinsqualifinfo.EmpInsGetQualifRepository;

import javax.ejb.Stateless;

@Stateless
public class JpaEmpInsGetQualifRepository extends JpaRepository implements EmpInsGetQualifRepository {

}
