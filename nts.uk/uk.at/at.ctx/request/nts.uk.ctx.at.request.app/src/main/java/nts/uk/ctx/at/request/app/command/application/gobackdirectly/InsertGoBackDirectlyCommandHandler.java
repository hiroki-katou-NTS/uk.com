package nts.uk.ctx.at.request.app.command.application.gobackdirectly;

import java.util.Optional;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.primitive.UseAtr;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.primitive.WorkTimeGoBack;
import nts.uk.ctx.at.shared.dom.worktime.SiftCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class InsertGoBackDirectlyCommandHandler extends CommandHandler<InsertGoBackDirectlyCommand> {
	@Inject
	private GoBackDirectlyRepository goBackDirectRepo;

	@Override
	protected void handle(CommandHandlerContext<InsertGoBackDirectlyCommand> context) {
		String companyId = AppContexts.user().companyId();
		InsertGoBackDirectlyCommand command = context.getCommand();
		Optional<GoBackDirectly> currentGoBack = this.goBackDirectRepo.findByApplicationID(companyId,
				command.getAppID());
		GoBackDirectly newGoBack = new GoBackDirectly(companyId, 
				UUID.randomUUID().toString(),
				new WorkTypeCode(command.workTypeCD), 
				new SiftCode(command.siftCd),
				EnumAdaptor.valueOf(command.workChangeAtr, UseAtr.class),
				EnumAdaptor.valueOf(command.goWorkAtr1, UseAtr.class),
				EnumAdaptor.valueOf(command.backHomeAtr1, UseAtr.class), 
				new WorkTimeGoBack(command.workTimeStart1),
				new WorkTimeGoBack(command.workTimeEnd1), 
				command.workLocationCD1,
				EnumAdaptor.valueOf(command.goWorkAtr2, UseAtr.class),
				EnumAdaptor.valueOf(command.backHomeAtr2, UseAtr.class), 
				new WorkTimeGoBack(command.workTimeStart2),
				new WorkTimeGoBack(command.workTimeEnd2), 
				command.workLocationCD2);
		if (currentGoBack.isPresent()) {
			throw new BusinessException("Msg_3");
		} else {
			goBackDirectRepo.insert(newGoBack);
		}

	}

}
