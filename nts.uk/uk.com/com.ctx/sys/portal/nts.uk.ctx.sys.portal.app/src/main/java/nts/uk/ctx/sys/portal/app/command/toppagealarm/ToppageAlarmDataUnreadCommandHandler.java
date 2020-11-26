package nts.uk.ctx.sys.portal.app.command.toppagealarm;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.toppagealarm.AlarmClassification;
import nts.uk.ctx.sys.portal.dom.toppagealarm.DisplayAtr;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmData;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmDataRepository;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmLog;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmLogRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KTG_ウィジェット.KTG031_トップページアラーム.トップページアラームver4.A:トップページアラーム.メニュー別OCD.アラームを未読にする
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ToppageAlarmDataUnreadCommandHandler extends CommandHandler<ToppageAlarmDataUnreadCommand> {

	@Inject
	private ToppageAlarmDataRepository toppageAlarmDataRepo;
	
	@Inject
	private ToppageAlarmLogRepository toppageAlarmLogRepo;
	
	@Override
	protected void handle(CommandHandlerContext<ToppageAlarmDataUnreadCommand> context) {
		ToppageAlarmDataUnreadCommand command = context.getCommand();
		String contractCd = AppContexts.user().contractCode();
		
		Optional<ToppageAlarmData> oExistedData = this.toppageAlarmDataRepo.get(
				command.getCompanyId(), 
				AlarmClassification.valueOf(command.getAlarmClassification()), 
				command.getIdentificationKey(), 
				command.getSid(), 
				DisplayAtr.valueOf(command.getDisplayAtr()));
		if (!oExistedData.isPresent()) {
			return;
		}
		Optional<ToppageAlarmLog> oExistedLog = this.toppageAlarmLogRepo.get(
				command.getCompanyId(), 
				AlarmClassification.valueOf(command.getAlarmClassification()), 
				command.getIdentificationKey(), 
				command.getSid(), 
				DisplayAtr.valueOf(command.getDisplayAtr()));
		if (!oExistedLog.isPresent()) {
			return;
		}
		ToppageAlarmLog updateLog = oExistedLog.get();
		updateLog.changeToUnread(oExistedData.get().getOccurrenceDateTime());
		this.toppageAlarmLogRepo.update(contractCd, updateLog);
	}

}
