/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.login;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.gateway.dom.login.EmployeeCodeSetting;
import nts.uk.ctx.sys.gateway.dom.login.EmployeeCodeSettingRepository;
@Stateless
public class JpaEmployeeCodeSettingRepository extends JpaRepository implements EmployeeCodeSettingRepository{

	@Override
	public EmployeeCodeSetting getbyCompanyId(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
