package nts.uk.ctx.bs.employee.infra.repository.employee.history;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;

@Stateless
public class AffCompanyHistRepositoryImp extends JpaRepository  implements AffCompanyHistRepository {

}
