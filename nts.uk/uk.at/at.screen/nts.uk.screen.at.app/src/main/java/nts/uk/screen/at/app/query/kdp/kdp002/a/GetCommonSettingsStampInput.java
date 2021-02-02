package nts.uk.screen.at.app.query.kdp.kdp002.a;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.application.CommonSettingsStampInput;
import nts.uk.ctx.at.record.dom.stamp.application.CommonSettingsStampInputRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 打刻入力の共通設定を取得する
 * UKDesign.UniversalK.就業.KDP_打刻.KDP002_打刻入力(個人打刻).A:打刻入力(個人).メニュー別OCD.打刻入力で共通設定を取得する
 * @author chungnt
 *
 */

@Stateless
public class GetCommonSettingsStampInput {

	@Inject
	private CommonSettingsStampInputRepository commonSettingsStampInputRepo;
	
	public GetCommonSettingsStampInputDto get() {
		
		boolean resutl = false;
		Optional<CommonSettingsStampInput> optional = this.commonSettingsStampInputRepo.get(AppContexts.user().companyId());
		
		if (optional.isPresent()) {
			resutl = optional.get().getNotUseAtr().value == 1 ? true : false;
		}
		
		return new GetCommonSettingsStampInputDto(resutl);
	}
}
