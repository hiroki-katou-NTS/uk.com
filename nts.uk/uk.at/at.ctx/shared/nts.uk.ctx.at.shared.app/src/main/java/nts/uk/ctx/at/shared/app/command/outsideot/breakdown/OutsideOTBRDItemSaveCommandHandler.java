/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.outsideot.breakdown;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;


/**
 * The Class OvertimeBRDItemSaveCommandHandler.
 */
@Stateless
public class OutsideOTBRDItemSaveCommandHandler extends CommandHandler<OutsideOTBRDItemSaveCommand>{

	/** The repository. */
	@Inject
	private OutsideOTSettingRepository repository;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<OutsideOTBRDItemSaveCommand> context) {
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		// get command
		OutsideOTBRDItemSaveCommand command = context.getCommand();

		// to domains
		List<OutsideOTBRDItem> domains = command.getOvertimeBRDItems().stream()
				.map(dto -> new OutsideOTBRDItem(dto)).collect(Collectors.toList());
		
		if(CollectionUtil.isEmpty(domains)){
			throw new BusinessException("Msg_485");
		}
		if (!checkUseBreakdownItem(domains)) {
			throw new BusinessException("Msg_485");
		}
		
		if(this.checkOverlapProductNumber(domains)){
			throw new BusinessException("Msg_490");
		}
		
		// save all list overtime breakdown item
		this.repository.saveAllBRDItem(domains, companyId);
	}
	
	/**
	 * Check use breakdown item.
	 *
	 * @return true, if successful
	 */
	private boolean checkUseBreakdownItem(List<OutsideOTBRDItem> domains) {
		for (OutsideOTBRDItem breakdownItem : domains) {
			if (breakdownItem
					.getUseClassification().value == UseClassification.UseClass_Use.value) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check overlap product number.
	 *
	 * @return true, if successful
	 */
	private boolean checkOverlapProductNumber(List<OutsideOTBRDItem> domains) {
		for (OutsideOTBRDItem breakdownItem1 : domains) {
			for (OutsideOTBRDItem breakdownItem2 : domains) {
				if (breakdownItem1.getBreakdownItemNo().value != breakdownItem2.getBreakdownItemNo().value
						&& breakdownItem1.getProductNumber().value == breakdownItem2.getProductNumber().value) {
					return true;
				}
			}
		}
		return false;
	}

}
