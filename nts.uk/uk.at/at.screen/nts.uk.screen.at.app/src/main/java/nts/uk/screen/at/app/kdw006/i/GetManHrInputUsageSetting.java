package nts.uk.screen.at.app.kdw006.i;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.jobmanagement.usagesetting.ManHrInputUsageSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.作業管理.工数入力の利用設定.App.工数入力の利用設定を取得する
 */
@Stateless
public class GetManHrInputUsageSetting {

	@Inject
	private ManHrInputUsageSettingRepository repo;

	/***
	 * 工数入力の利用設定を取得する
	 * 
	 * @return 工数入力の利用設定Dto
	 */
	public ManHrInputUsageSettingDto getManHrInputUsageSetting() {
		return this.repo.get(AppContexts.user().companyId()).map(x -> ManHrInputUsageSettingDto.fromDomain(x))
				.orElse(null);
	}
}
