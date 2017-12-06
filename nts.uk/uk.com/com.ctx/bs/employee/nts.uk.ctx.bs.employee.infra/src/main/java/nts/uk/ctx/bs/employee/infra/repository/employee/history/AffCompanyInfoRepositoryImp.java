package nts.uk.ctx.bs.employee.infra.repository.employee.history;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyInfoRepository;

@Stateless
public class AffCompanyInfoRepositoryImp extends JpaRepository implements AffCompanyInfoRepository {

}
