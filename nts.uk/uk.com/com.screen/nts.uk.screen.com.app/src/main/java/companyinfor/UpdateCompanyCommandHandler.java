package companyinfor;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.command.UpdateCompanyInforCommandHandler;

@Stateless
@Transactional
public class UpdateCompanyCommandHandler {
	@Inject
	private UpdateCompanyInforCommandHandler updateCom;
	
//	@Inject
//	private UpdateSysUsageSetCommandHandler updateSys;
}
