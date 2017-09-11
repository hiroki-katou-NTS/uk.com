/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.workplace;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceConfig;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceConfigRepository;
@Stateless
public class JpaWorkplaceConfigRepository implements WorkplaceConfigRepository {

	@Override
	public List<WorkplaceConfig> findAllByCompanyId(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<WorkplaceConfig> findLastestByCompanyId(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
