package nts.uk.ctx.pr.report.app.payment.comparing.confirm.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ComfirmDifferentRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.PaycompConfirm;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class InsertUpdatePaycompConfirmCommandHandler extends CommandHandler<InsertUpdatePaycompConfirmCommand> {

	@Inject
	private ComfirmDifferentRepository comfirmDiffRepository;

	@Override
	protected void handle(CommandHandlerContext<InsertUpdatePaycompConfirmCommand> context) {
		List<PaycompConfirm> updatePaycompConfirmList = new ArrayList<>();
		List<PaycompConfirm> insertPaycompConfirmList = new ArrayList<>();
		String companyCode = AppContexts.user().companyCode();
		List<InsertUpdatePaycompConfirm> lstInsertUpdatedata = context.getCommand().getLstInsertUpdatedata();
		if (lstInsertUpdatedata == null || lstInsertUpdatedata.isEmpty()) {
			return;
		}
		List<PaycompConfirm> paycompConfirmList = this.comfirmDiffRepository.getPayCompComfirm(companyCode,
				context.getCommand().getPersionIDs(), lstInsertUpdatedata.get(0).getProcessingYMEarlier(),
				lstInsertUpdatedata.get(0).getProcessingYMLater());

		lstInsertUpdatedata.stream().forEach(c -> {
			PaycompConfirm newPaycompConfirm = PaycompConfirm.createFromJavaType(companyCode, c.getEmployeeCode(),
					c.getItemCode(), c.getProcessingYMEarlier(), c.getProcessingYMLater(), c.getCategoryAtr(),
					c.getValueDifference(), c.getReasonDifference(), c.getConfirmedStatus());
			Optional<PaycompConfirm> paycompConfirmFilter = paycompConfirmList.stream().filter(
					p -> p.getEmployeeCode().v().equals(c.getEmployeeCode()) && p.getCategoryAtr().value == c.getCategoryAtr() && p.getItemCode().v().equals(c.getItemCode()))
					.findFirst();

			if (paycompConfirmFilter.isPresent()) {
				updatePaycompConfirmList.add(newPaycompConfirm);
			} else {
				insertPaycompConfirmList.add(newPaycompConfirm);
			}
		});
		if (!updatePaycompConfirmList.isEmpty()) {
			this.comfirmDiffRepository.updateComparingPrintSet(updatePaycompConfirmList);
		}
		if (!insertPaycompConfirmList.isEmpty()) {
			this.comfirmDiffRepository.insertComparingPrintSet(insertPaycompConfirmList);
		}
	}
}
