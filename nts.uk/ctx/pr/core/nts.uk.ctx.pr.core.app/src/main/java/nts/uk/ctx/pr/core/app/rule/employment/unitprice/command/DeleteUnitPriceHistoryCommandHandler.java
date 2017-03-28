///******************************************************************
// * Copyright (c) 2016 Nittsu System to present.                   *
// * All right reserved.                                            *
// *****************************************************************/
//package nts.uk.ctx.pr.core.app.rule.employment.unitprice.command;
//
//import java.util.Optional;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//import javax.transaction.Transactional;
//
//import nts.arc.layer.app.command.CommandHandler;
//import nts.arc.layer.app.command.CommandHandlerContext;
//import nts.uk.ctx.core.dom.company.CompanyCode;
//import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
//import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPrice;
//import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceCode;
//import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistory;
//import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistoryRepository;
//import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceRepository;
//import nts.uk.shr.com.context.AppContexts;
//
///**
// * The Class DeleteUnitPriceHistoryCommandHandler.
// */
//@Stateless
//public class DeleteUnitPriceHistoryCommandHandler extends CommandHandler<DeleteUnitPriceHistoryCommand> {
//
//	/** The unit price history repository. */
//	@Inject
//	private UnitPriceHistoryRepository unitPriceHistoryRepository;
//
//	/** The unit price repository. */
//	@Inject
//	private UnitPriceRepository unitPriceRepository;
//
//	/*
//	 * (non-Javadoc)
//	 *
//	 * @see
//	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
//	 * .CommandHandlerContext)
//	 */
//	@Override
//	@Transactional
//	protected void handle(CommandHandlerContext<DeleteUnitPriceHistoryCommand> context) {
//		// Get the current company code.
//		CompanyCode companyCode = new CompanyCode(AppContexts.user().companyCode());
//
//		// Get the command
//		DeleteUnitPriceHistoryCommand command = context.getCommand();
//
//		UnitPriceCode unitPriceCode = new UnitPriceCode(command.getUnitPriceCode());
//
//		UnitPrice unitPrice = unitPriceRepository.findByCode(companyCode, unitPriceCode).get();
//
//		unitPriceHistoryRepository.remove(companyCode, unitPriceCode, command.getId());
//
//		Optional<UnitPriceHistory> lastUnitPriceHistory = unitPriceHistoryRepository.findLastHistory(companyCode,
//				unitPriceCode);
//
//		// Update last history if present
//		if (lastUnitPriceHistory.isPresent()) {
//			UnitPriceHistory updatedLastUnitPriceHistory = lastUnitPriceHistory.get();
//
//			// Update to max Date
//			MonthRange updatedMonthRange = MonthRange
//					.toMaxDate(updatedLastUnitPriceHistory.getApplyRange().getStartMonth());
//			updatedLastUnitPriceHistory.setApplyRange(updatedMonthRange);
//
//			unitPriceHistoryRepository.update(unitPrice, updatedLastUnitPriceHistory);
//		}
//		// Remove UnitPriceHeader if no history left
//		else {
//			unitPriceRepository.remove(companyCode, unitPriceCode);
//		}
//	}
//
//}
