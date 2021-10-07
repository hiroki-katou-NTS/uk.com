package nts.uk.screen.at.app.kdw006.query;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.jobmanagement.usagesetting.ManHrInputUsageSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
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
