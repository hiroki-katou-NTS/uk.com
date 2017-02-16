package nts.uk.ctx.basic.app.command.organization.jobtitle;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.basic.dom.organization.jobtitle.JobTitle;
import nts.uk.ctx.basic.dom.organization.jobtitle.JobTitleRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class CreateJobTitleCommandHandler extends CommandHandler<CreateJobTitleCommand>{
	
	@Inject
	private JobTitleRepository positionRepo;
	
	
	@Override
	protected void handle(CommandHandlerContext<CreateJobTitleCommand> context){
		
		CreateJobTitleCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		if (positionRepo.isExist(companyCode, command.getStartDate())) {
			throw new BusinessException(new RawErrorMessage(""));
		}
		JobTitle jobTitle = context.getCommand().toDomain(IdentifierUtil.randomUniqueId());
		jobTitle.validate();
		this.positionRepo.add(jobTitle);
	}
}
