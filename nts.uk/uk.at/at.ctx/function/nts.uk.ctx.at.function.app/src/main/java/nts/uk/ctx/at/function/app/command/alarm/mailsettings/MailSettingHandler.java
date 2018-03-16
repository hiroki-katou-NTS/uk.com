package nts.uk.ctx.at.function.app.command.alarm.mailsettings;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettingAutomaticRepository;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettingNormalRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class MailSettingHandler {

	@Inject
	private MailSettingAutomaticRepository mailAutomaticRepo;

	@Inject
	private MailSettingNormalRepository mailNormalRepo;
	
	public void addMailSetting(MailAutoAndNormalCommand mailAutoAndNormal){
		String companyId = AppContexts.user().companyId();
		if (companyId == "" || companyId == null)
			return;
		if(mailAutomaticRepo.findByCompanyId(companyId).isPresent()){
			mailAutomaticRepo.update(mailAutoAndNormal.getMailSettingAutomatic().toDomain());
		}else{
			mailAutomaticRepo.create(mailAutoAndNormal.getMailSettingAutomatic().toDomain());
		}
		if(mailNormalRepo.findByCompanyId(companyId).isPresent()){
			mailNormalRepo.update(mailAutoAndNormal.getMailSettingNormal().toDomain());
		}else{
			mailNormalRepo.create(mailAutoAndNormal.getMailSettingNormal().toDomain());
		}
	}
}
