/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.businesstype.command;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessType;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessTypeRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class InsuranceBusinessTypeUpdateCommandHandler.
 */
@Stateless
@Transactional
public class InsuranceBusinessTypeUpdateCommandHandler extends CommandHandler<InsuranceBusinessTypeUpdateCommand> {

	/**  CompanyRepository. */
	@Inject
	private InsuranceBusinessTypeRepository insuranceBusinessTypeRepository;

	/**
	 * Handle command.
	 * 
	 * @param context
	 *            context
	 */
	@Override
	protected void handle(CommandHandlerContext<InsuranceBusinessTypeUpdateCommand> context) { 
		List<InsuranceBusinessType> lsInsuranceBusinessType = context.getCommand().toDomain();
		for (InsuranceBusinessType insuranceBusinessType : lsInsuranceBusinessType) {
			this.insuranceBusinessTypeRepository.update(insuranceBusinessType);
		}
	}

}
