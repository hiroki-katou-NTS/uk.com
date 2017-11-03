/**
 * 
 */
package nts.uk.ctx.bs.employee.infra.repository.department;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.department.AffDepartmentRepository;
import nts.uk.ctx.bs.employee.dom.department.AffiliationDepartment;
import nts.uk.ctx.bs.employee.infra.entity.department.BsymtAffiDepartment;

/**
 * @author danpv
 *
 */
@Stateless
public class AffDepartmentRepoImpl extends JpaRepository implements AffDepartmentRepository {

	private static final String SELECT_BY_EID_STD = "select ad from BsymtAffiDepartment sd"
			+ "where ad.sid = :empId and ad.strD <= :std and ad.endD >= :std";

	@Override
	public Optional<AffiliationDepartment> getByEmpIdAndStandDate(String employeeId, GeneralDate standandDate) {
		Optional<BsymtAffiDepartment> dataOpt = this.queryProxy().query(SELECT_BY_EID_STD, BsymtAffiDepartment.class)
				.getSingle();
		if (dataOpt.isPresent()) {
			BsymtAffiDepartment ent = dataOpt.get();
			return Optional.of(AffiliationDepartment.createDmainFromJavaType(ent.getId(), ent.getStrD(), ent.getEndD(),
					ent.getSid(), ent.getDepId()));
		}
		return Optional.empty();
	}

}
