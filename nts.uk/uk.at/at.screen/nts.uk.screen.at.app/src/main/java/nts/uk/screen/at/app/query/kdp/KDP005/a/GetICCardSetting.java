package nts.uk.screen.at.app.query.kdp.KDP005.a;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampResultDisplayDto;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplayRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunalRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampinputfunctionsettings.notificationmessagesettings.NoticeSetRepository;
import nts.uk.screen.at.app.query.kdp.kdp004.a.NoticeSetDto;
import nts.uk.screen.at.app.query.kdp.kdp004.a.StampSetCommunalDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDP_打刻.KDP005_打刻入力(ICカード).A:打刻入力(ICカード).メニュー別OCD.打刻入力(ICカード)を起動する
 * 
 * @author tutt
 *
 */
@Stateless
public class GetICCardSetting {
	
	@Inject
	private StampSetCommunalRepository stampSetCommunalRepo;

	@Inject
	private StampResultDisplayRepository stampResulRepo;
	
	@Inject
	private NoticeSetRepository noticeSetRepo;
	
	public GetICCardSettingDto  getICCardSetting() {
		String companyId = AppContexts.user().companyId();
		return getICCardSetting(companyId);
	}
	
	/**
	 * Get stamp setting with companyId
	 * @param companyId
	 * @return
	 */
	public GetICCardSettingDto getICCardSetting(String companyId) {
		GetICCardSettingDto result = new GetICCardSettingDto();
		
		// 1:get 会社ID
		this.stampSetCommunalRepo.gets(companyId)
			.ifPresent(setComu -> {
				result.setStampSetting(new StampSetCommunalDto(setComu));
				
			});
		
		// 2:get 会社ID
		this.stampResulRepo.getStampSet(companyId)
			.ifPresent(stampRes -> {
				result.setStampResultDisplay(new StampResultDisplayDto(Optional.ofNullable(stampRes)));
			});
		
		// 3: get 会社ID
		this.noticeSetRepo.get(companyId)
			.ifPresent(noticeSet -> {
			result.setNoticeSetDto(new NoticeSetDto(noticeSet));
		});

		return result;
	}	

}
