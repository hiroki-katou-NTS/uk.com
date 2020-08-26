package nts.uk.screen.at.app.query.kdp.kdp004.a;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampResultDisplayDto;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplayRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunalRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDP_打刻.KDP004_打刻入力(指静脈).A:打刻入力(指静脈).メニュー別OCD.打刻入力(指静脈)を起動する
 * 
 * @author sonnlb
 *
 */
@Stateless
public class GetFingerStampSetting {

	@Inject
	private StampSetCommunalRepository stampSetCommunalRepo;

	@Inject
	private StampResultDisplayRepository stampResulRepo;

	public GetFingerStampSettingDto getFingerStampSetting() {
		String companyId = AppContexts.user().companyId();
		return getFingerStampSetting(companyId);
	}

	/**
	 * Get stamp setting with companyId
	 * @param companyId
	 * @return
	 */
	public GetFingerStampSettingDto getFingerStampSetting(String companyId) {
		GetFingerStampSettingDto result = new GetFingerStampSettingDto();
		
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

		return result;
	}	
}
