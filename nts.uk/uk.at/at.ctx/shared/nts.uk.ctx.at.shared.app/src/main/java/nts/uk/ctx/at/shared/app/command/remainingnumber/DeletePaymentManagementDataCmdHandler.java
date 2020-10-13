package nts.uk.ctx.at.shared.app.command.remainingnumber;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;

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
		//	ドメインモデル「振出管理データ」を取得
		Optional<PayoutManagementData> dataPayout = payoutManagementDataRepo.findByID(command.getPayoutId());
		if (command.getPayoutId() != null) {
			//	ドメインモデル「振出管理データ」を削除 (Delete domain model「振出管理データ」)
			payoutManagementDataRepo.delete(command.getPayoutId());
		}
		//	ドメインモデル「振休管理データ」を取得
		Optional<SubstitutionOfHDManagementData> dataSub = substitutionOfHDManaDataRepo
				.findByID(command.getSubOfHDID());
		if (command.getSubOfHDID() != null) {
			// ドメインモデル「振休管理データ」を削除 (Delete domain model 「振休管理データ」)
			substitutionOfHDManaDataRepo.delete(command.getSubOfHDID());
		}
		//	ドメインモデル「振出振休紐付け管理」を削除 (Delete domain model 「振出振休紐付け管理」)
		if (command.getPayoutId() != null && command.getSubOfHDID() != null) {
			this.payoutSubofHDManaRepository.delete(dataPayout.map(PayoutManagementData::getSID).orElse(null),
					dataSub.map(SubstitutionOfHDManagementData::getSID).orElse(null),
					dataPayout.map(PayoutManagementData::getPayoutDate).orElse(new CompensatoryDayoffDate()).getDayoffDate().orElse(null),
					dataSub.map(SubstitutionOfHDManagementData::getHolidayDate).orElse(new CompensatoryDayoffDate()).getDayoffDate().orElse(null));
		}
	}

}
