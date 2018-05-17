package nts.uk.ctx.at.record.app.command.remainingnumber.paymana;

import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementDataService;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutSubofHDManaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManaDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddPayManaCommandHandler extends CommandHandler<PayManaRemainCommand>{
	
	@Inject
	private PayoutManagementDataRepository payoutManaRepo;
	
	@Inject
	private PayoutManagementDataService payoutManaDataService;
	
	@Inject
	private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepository;
	
	@Inject
	private PayoutSubofHDManaRepository payoutSubofHDManaRepository;
	
	@Override
	protected void handle(CommandHandlerContext<PayManaRemainCommand> context) {
		PayManaRemainCommand command = context.getCommand();
		String cid = AppContexts.user().companyId();
		String newIDPayout = IdentifierUtil.randomUniqueId();
		String newIDSub = IdentifierUtil.randomUniqueId();
		PayoutManagementData data = new PayoutManagementData(newIDPayout,cid, command.getSID(), command.getUnknownDate(), command.getDayOff(), command.getExpiredDate(), command.getLawAtr(),
				command.getOccurredDays(), command.getUnUsedDays(), command.getStateAtr());

		if (command.getPickUp()) {
			if (payoutManaDataService.getClosingDate().compareTo(command.getDayOff()) > 0){
				throw new BusinessException("Msg_740");
			}
		}
		else if (!command.getPause()) {
			throw new BusinessException("Msg_725");
		}
		if (payoutManaDataService.checkInfoPayMana(data)) {
			throw new BusinessException("Msg_737");
		}
		if (command.getPause()) {
			if (command.getSubDayoffDate().compareTo(payoutManaDataService.getClosingDate()) > 0) {
				throw new BusinessException("Msg_744");
			}
			if (command.getDayOff().compareTo(command.getSubDayoffDate()) >= 0) {
				throw new BusinessException("Msg_729");
			}
			if (payoutManaDataService.checkInfoPayMana(data)) {
				throw new BusinessException("Msg_737");
			}
		}
		if (command.getPickUp()) {
			payoutManaRepo.create(data);
		}
		if (command.getPause()) {
			SubstitutionOfHDManagementData subDomain = new SubstitutionOfHDManagementData(newIDSub, cid, command.getSID(),command.isSubUunknownDate(), command.getSubDayoffDate(), command.getRequiredDays(), command.getRemainDays());
			substitutionOfHDManaDataRepository.create(subDomain);
		}
		if (command.getPickUp() && command.getPause()) {
			PayoutSubofHDManagement paySub = new PayoutSubofHDManagement(newIDPayout, newIDSub, new BigDecimal(0), TargetSelectionAtr.MANUAL.value);
			payoutSubofHDManaRepository.add(paySub);
		}
	}



}
