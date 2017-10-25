/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.jobtitle.sequence;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.jobtitle.sequence.SequenceMaster;
import nts.uk.ctx.bs.employee.dom.jobtitle.sequence.SequenceMasterRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveSequenceCommandHandler.
 */
@Stateless
@Transactional
public class SaveSequenceCommandHandler extends CommandHandler<SaveSequenceCommand> {

	/** The repository. */
	@Inject
	private SequenceMasterRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveSequenceCommand> context) {

		// Get data
		SaveSequenceCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();

		// Check required param
		if (StringUtils.isEmpty(command.getSequenceCode()) || StringUtils.isEmpty(command.getSequenceName())) {
			return;
		}

		Optional<SequenceMaster> opSequenceMaster = this.repository.findBySequenceCode(companyId,
				command.getSequenceCode());

		if (command.getIsCreateMode()) {
			// Add
			if (opSequenceMaster.isPresent()) {
				// Throw Exception - Duplicated
				throw new BusinessException("Msg_3");
			}
			final int newOrder = this.repository.findMaxOrder() + 1;
			command.setOrder(newOrder);
			this.repository.add(command.toDomain(companyId));
		} else {
			// Update
			// Sequence code + order is not changable
			SequenceMaster oldDomain = opSequenceMaster.get();
			command.setOrder(oldDomain.getOrder());
			command.setSequenceCode(oldDomain.getSequenceCode().v());

			this.repository.update(command.toDomain(companyId));
		}
	}
}
