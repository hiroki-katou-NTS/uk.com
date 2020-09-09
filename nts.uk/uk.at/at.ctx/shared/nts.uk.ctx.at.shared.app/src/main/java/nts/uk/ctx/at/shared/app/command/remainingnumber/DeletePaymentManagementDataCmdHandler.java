package nts.uk.ctx.at.shared.app.command.remainingnumber;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManaDataRepository;

/*
 * 振休振出管理データを削除 
 * Delete management data of taking holidays
 */
@Stateless
public class DeletePaymentManagementDataCmdHandler extends CommandHandler<DeletePaymentManagementDataCommand>{
	
	@Inject 
	private PayoutManagementDataRepository payoutManagementDataRepo;

	@Inject
	private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepo;
	
	@Inject
	private PayoutSubofHDManaRepository payoutSubofHDManaRepository;
	
	@Override
	protected void handle(CommandHandlerContext<DeletePaymentManagementDataCommand> context) {
		DeletePaymentManagementDataCommand command = context.getCommand();
		// step ドメインモデル「振出管理データ」を削除 (Delete domain model「振出管理データ」) 
		this.payoutManagementDataRepo.delete(command.getPayoutId());
		// step ドメインモデル「振休管理データ」を削除 (Delete domain model 「振休管理データ」)
		this.substitutionOfHDManaDataRepo.delete(command.getSubOfHDID()); 
		// step ドメインモデル「振出振休紐付け管理」を削除 (Delete domain model 「振出振休紐付け管理」)
		this.payoutSubofHDManaRepository.delete(command.getPayoutId(), command.getSubOfHDID());
	}

}
