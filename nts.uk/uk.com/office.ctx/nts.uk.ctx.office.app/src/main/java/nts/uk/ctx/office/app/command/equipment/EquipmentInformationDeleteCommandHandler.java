package nts.uk.ctx.office.app.command.equipment;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.office.dom.equipment.EquipmentInformationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.オフィス.OEM_設備マスタ.OEM002_設備の登録.A：設備の登録.メニュー別OCD.設備を削除する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class EquipmentInformationDeleteCommandHandler extends CommandHandler<EquipmentInformationCommand> {

	@Inject
	private EquipmentInformationRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<EquipmentInformationCommand> context) {
		EquipmentInformationCommand command = context.getCommand();
		// delete(ログイン会社ID、設備コード)
		this.repository.delete(AppContexts.user().companyId(), command.getCode());
	}

}
