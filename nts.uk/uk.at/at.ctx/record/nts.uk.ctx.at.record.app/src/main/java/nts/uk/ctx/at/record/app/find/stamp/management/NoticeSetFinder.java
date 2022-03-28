package nts.uk.ctx.at.record.app.find.stamp.management;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.application.CommonSettingsStampInputRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampinputfunctionsettings.notificationmessagesettings.NoticeSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author thanhpv
 *
 */
@Stateless
public class NoticeSetFinder {

	@Inject
	private NoticeSetRepository repo;
	
	@Inject
	private CommonSettingsStampInputRepository commonRepo;
	
	public NoticeSetAndAupUseArtDto getNoticeSetAndAupUseArt() {
		String companyId = AppContexts.user().companyId();
		return new NoticeSetAndAupUseArtDto(repo.get(companyId).map(c->new NoticeSetDto(c)).orElse(null)); 
	}
}
