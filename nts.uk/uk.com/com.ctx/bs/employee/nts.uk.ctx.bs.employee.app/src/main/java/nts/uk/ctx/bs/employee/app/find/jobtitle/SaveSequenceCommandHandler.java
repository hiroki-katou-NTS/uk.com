package nts.uk.ctx.bs.employee.app.find.jobtitle;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.app.find.jobtitle.dto.SaveSequenceCommand;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMaster;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMasterRepository;
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
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveSequenceCommand> context) {
		
		// Get data
		SaveSequenceCommand command = context.getCommand();
        String companyId = AppContexts.user().companyId();

        Optional<SequenceMaster> opSequenceMaster = this.repository.findBySequenceCode(companyId, command.getSequenceMasterDto().getSequenceCode());
        
        if (command.getIsCreateMode()) {
        	// Add
        	if (opSequenceMaster.isPresent()) {
        		//TODO: Exception - Duplicated
        	}
            this.repository.add(command.toDomain(companyId));
        } else {
        	// Update
        	if (!opSequenceMaster.isPresent()) {
        		//TODO: Exception - Sequence not found
        	}       	
            this.repository.update(command.toDomain(companyId));
        }                
	}
}
