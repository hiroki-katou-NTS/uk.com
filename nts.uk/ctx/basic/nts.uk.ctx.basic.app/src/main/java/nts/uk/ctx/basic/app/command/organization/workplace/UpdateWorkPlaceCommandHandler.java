package nts.uk.ctx.basic.app.command.organization.workplace;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.shr.HierarchyCode;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlace;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceCode;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceName;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceRepository;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceShortName;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateWorkPlaceCommandHandler extends CommandHandler<UpdateWorkPlaceCommand> {

	@Inject
	private WorkPlaceRepository workPlaceRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateWorkPlaceCommand> context) {
//		String companyCode = AppContexts.user().companyCode();
//		WorkPlace workPlace = new WorkPlace(companyCode, new WorkPlaceCode(context.getCommand().getWorkPlaceCode()),
//				GeneralDate.legacyDate(context.getCommand().getEndDate()),
//				new WorkPlaceCode(context.getCommand().getExternalCode()),
//				new WorkPlaceFullName(context.getCommand().getFullName()),
//				new HierarchyCode(context.getCommand().getHierarchyCode()),
//				new WorkPlaceName(context.getCommand().getName()),
//				new WorkPlaceShortName(context.getCommand().getShortName()),
//				GeneralDate.legacyDate(context.getCommand().getStartDate()));
//		workPlaceRepository.update(workPlace);
	}

}

