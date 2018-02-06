package nts.uk.ctx.at.request.app.command.setting.company.vacationapplicationsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSetRepository;

@Stateless
public class UpdateHdAppSetCommandHandler extends CommandHandler<HdAppSetCommand>{
	@Inject
	private HdAppSetRepository hdRep;

	@Override
	protected void handle(CommandHandlerContext<HdAppSetCommand> context) {
		HdAppSetCommand data = context.getCommand();
		Optional<HdAppSet> hdApp = hdRep.getHdAppSet(data.getHdAppType());
		HdAppSet hdAppSet = HdAppSet.createFromJavaType(data.companyId, data.hdAppType, data.dispUnselec, 
				data.use60h, data.obstacleName, data.regisShortLostHd, data.hdName, data.regisLackPubHd, 
				data.changeWrkHour, data.ckuperLimit, data.actualDisp, data.wrkHours, data.pridigCheck, 
				data.yearHdName, data.regisNumYear, data.furikyuName, data.regisInsuff, data.useGener, 
				data.useYear, data.timeDigest, data.absenteeism, data.concheckOutLegal, data.specialVaca,
				data.concheckDateRelease, data.appDateContra, data.yearResig, data.regisShortReser);
		if(hdApp.isPresent()){
			hdRep.update(hdAppSet);
			return;
		}
		hdRep.insert(hdAppSet);
	}
	
}
