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
				.setParameter("empId", employeeId).setParameter("std", standandDate).getSingle();
		if (dataOpt.isPresent()) {
			BsymtAffiDepartment ent = dataOpt.get();
			return Optional.of(AffiliationDepartment.createDmainFromJavaType(ent.getId(), ent.getStrD(), ent.getEndD(),
					ent.getSid(), ent.getDepId()));
		}
		return Optional.empty();
	}

	/**
	 * Convert from domain to entity
	 * 
	 * @param domain
	 * @return
	 */
	private BsymtAffiDepartment toEntity(AffiliationDepartment domain) {
		return new BsymtAffiDepartment(domain.getId(), domain.getEmployeeId(), domain.getDepartmentId(),
				domain.getPeriod().start(), domain.getPeriod().end());
	}

	private void updateEntity(AffiliationDepartment domain, BsymtAffiDepartment entity) {
		entity.setSid(domain.getEmployeeId());
		entity.setDepId(domain.getDepartmentId());
		entity.setStrD(domain.getPeriod().start());
		entity.setEndD(domain.getPeriod().end());
	}

	/**
	 * ドメインモデル「所属部門」を新規登録する
	 * 
	 * @param domain
	 */
	@Override
	public void addAffDepartment(AffiliationDepartment domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	/**
	 * 取得した「所属部門」を更新する
	 * 
	 * @param domain
	 */
	@Override
	public void updateAffDepartment(AffiliationDepartment domain) {
		// Get exist item
		Optional<BsymtAffiDepartment> existItem = this.queryProxy().find(domain.getId(), BsymtAffiDepartment.class);
		if (!existItem.isPresent()) {
			return;
		}
		// Update entity
		updateEntity(domain, existItem.get());
		// Update table
		this.commandProxy().update(existItem.get());
	}

}
