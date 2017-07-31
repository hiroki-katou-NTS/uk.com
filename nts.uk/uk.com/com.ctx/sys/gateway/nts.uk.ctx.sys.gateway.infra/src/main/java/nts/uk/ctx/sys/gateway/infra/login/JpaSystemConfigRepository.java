/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.login;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.gateway.dom.login.SystemConfig;
import nts.uk.ctx.sys.gateway.dom.login.SystemConfigRepository;
@Stateless
public class JpaSystemConfigRepository extends JpaRepository implements SystemConfigRepository{

	@Override
	public Optional<SystemConfig> getSystemConfig() {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
