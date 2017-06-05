package nts.uk.ctx.pr.report.app.payment.refundsetting.refundlayout.command;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout.RefundLayout;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout.RefundLayoutRepository;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout.ShowRefundLayout;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class InsertUpdateRefundLayoutCommandHandler extends CommandHandler<InsertUpdateRefundLayoutCommand> {

	@Inject
	private RefundLayoutRepository refundLayoutRepository;

	@Override
	protected void handle(CommandHandlerContext<InsertUpdateRefundLayoutCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		InsertUpdateRefundLayoutCommand insertUpdateCommand = context.getCommand();
		ShowRefundLayout showRefundLayout = ShowRefundLayout.createFromJavaType(insertUpdateCommand.getShowCompName(),
				insertUpdateCommand.getShowCompAddInSurface(), insertUpdateCommand.getShowCompNameInSurface(),
				insertUpdateCommand.getShowDependencePerNum(), insertUpdateCommand.getShowInsuranceLevel(),
				insertUpdateCommand.getShowMnyItemName(), insertUpdateCommand.getShowPerAddInSurface(),
				insertUpdateCommand.getShowPerNameInSurface(), insertUpdateCommand.getShowRemainAnnualLeave(),
				insertUpdateCommand.getShowTotalTaxMny(), insertUpdateCommand.getShowZeroInAttend(),
				insertUpdateCommand.getShowPerTaxCatalog(), insertUpdateCommand.getShowDepartment(),
				insertUpdateCommand.getShowZeroInMny(), insertUpdateCommand.getShowProductsPayMny(),
				insertUpdateCommand.getShowAttendItemName());
		RefundLayout refundLayout = RefundLayout.createFromJavaType(companyCode, insertUpdateCommand.getPrintType(),
				insertUpdateCommand.getUsingZeroSettingCtg(), insertUpdateCommand.getPrintYearMonth(), showRefundLayout,
				insertUpdateCommand.getPaymentCellNameCtg(), insertUpdateCommand.getIsShaded(),
				insertUpdateCommand.getBordWidth());

		if (this.refundLayoutRepository.getRefundLayout(companyCode, insertUpdateCommand.getPrintType()).isPresent()) {
			this.refundLayoutRepository.updateRefundLayout(refundLayout);
			return;
		}
		this.refundLayoutRepository.insertRefundLayout(refundLayout);
	}

}
