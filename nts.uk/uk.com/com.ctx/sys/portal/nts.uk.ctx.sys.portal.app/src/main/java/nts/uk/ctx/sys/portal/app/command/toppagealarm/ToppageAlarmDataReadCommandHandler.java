package nts.uk.ctx.sys.portal.app.command.toppagealarm;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.toppagealarm.AlarmClassification;
import nts.uk.ctx.sys.portal.dom.toppagealarm.DisplayAtr;
import nts.uk.ctx.sys.portal.dom.toppagealarm.IdentificationKey;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmLog;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmLogRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KTG_ウィジェット.KTG031_トップページアラーム.トップページアラームver4.A:トップページアラーム.メニュー別OCD.アラームを既読にする
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ToppageAlarmDataReadCommandHandler extends CommandHandler<ToppageAlarmDataReadCommand> {

	@Inject
	private ToppageAlarmLogRepository toppageAlarmLogRepo;
	
	@Override
	protected void handle(CommandHandlerContext<ToppageAlarmDataReadCommand> context) {
		ToppageAlarmDataReadCommand command = context.getCommand();
		String contractCd = AppContexts.user().contractCode();
		
		Optional<ToppageAlarmLog> oExistedLog = this.toppageAlarmLogRepo.get(
				command.getCompanyId(), 
				EnumAdaptor.valueOf(command.getAlarmClassification(), AlarmClassification.class), 
				command.getIdentificationKey(), 
				command.getSid(), 
				EnumAdaptor.valueOf(command.getDisplayAtr(), DisplayAtr.class));
		
		if (oExistedLog.isPresent()) {
			ToppageAlarmLog updateLog = oExistedLog.get();
			updateLog.updateAlreadyDatetime(); 
			this.toppageAlarmLogRepo.update(contractCd, updateLog);
		} else {
			ToppageAlarmLog newLog = ToppageAlarmLog.builder()
					.cid(command.getCompanyId())
					.alarmClassification(EnumAdaptor.valueOf(command.getAlarmClassification(), AlarmClassification.class))
					.identificationKey(new IdentificationKey(command.getIdentificationKey()))
					.displaySId(command.getSid())
					.displayAtr(EnumAdaptor.valueOf(command.getDisplayAtr(), DisplayAtr.class))
					.build();
			newLog.updateAlreadyDatetime();
			this.toppageAlarmLogRepo.insert(contractCd, newLog);
		}
	}

}
