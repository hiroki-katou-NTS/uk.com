/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.overtime.breakdown;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.OutsideOTBRDItemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class OvertimeBRDItemSaveCommandHandler.
 */
@Stateless
public class OvertimeBRDItemSaveCommandHandler extends CommandHandler<OvertimeBRDItemSaveCommand>{

	/** The repository. */
	@Inject
	private OutsideOTBRDItemRepository repository;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<OvertimeBRDItemSaveCommand> context) {
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		// get command
		OvertimeBRDItemSaveCommand command = context.getCommand();
		
		// save all list overtime breakdown item
		this.repository.saveAll(command.getOvertimeBRDItems().stream()
				.map(dto -> new OutsideOTBRDItem(dto)).collect(Collectors.toList()), companyId);
	}

}
