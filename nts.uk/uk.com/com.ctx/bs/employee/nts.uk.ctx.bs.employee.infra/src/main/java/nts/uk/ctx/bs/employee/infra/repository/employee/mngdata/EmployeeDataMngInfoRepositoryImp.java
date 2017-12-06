package nts.uk.ctx.bs.employee.infra.repository.employee.mngdata;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;

@Stateless
public class EmployeeDataMngInfoRepositoryImp extends JpaRepository implements EmployeeDataMngInfoRepository {

}
