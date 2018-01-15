/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime.employee;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employee.EmployeeWtSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employee.EmployeeWtSettingRepository;

/**
 * The Class JpaEmploymentWtSettingRepository.
 */
@Stateless
public class JpaEmployeeWtSettingRepository extends JpaRepository
		implements EmployeeWtSettingRepository {

	@Override
	public void create(EmployeeWtSetting setting) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(EmployeeWtSetting setting) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String companyId) {
		// TODO Auto-generated method stub

	}

	@Override
	public EmployeeWtSetting find(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmployeeWtSetting findByEmployeeId(String companyId, String employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

}
