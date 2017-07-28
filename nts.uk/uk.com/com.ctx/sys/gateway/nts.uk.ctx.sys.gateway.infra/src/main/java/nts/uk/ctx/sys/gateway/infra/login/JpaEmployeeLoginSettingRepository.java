/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.login;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.gateway.dom.login.EmployeeLoginSetting;
import nts.uk.ctx.sys.gateway.dom.login.EmployeeLoginSettingRepository;
@Stateless
public class JpaEmployeeLoginSettingRepository extends JpaRepository implements EmployeeLoginSettingRepository{

	@Override
	public Optional<EmployeeLoginSetting> getByContractCode(String contractCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
