package nts.uk.screen.at.app.query.kmk004.s;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 利用単位の設定を取得する
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.メニュー別OCD（共通）.利用単位の設定を取得する.利用単位の設定を取得する
 * @author chungnt
 *
 */

@Stateless
public class GetUsageUnitSetting {

	@Inject
	private UsageUnitSettingRepository repo;
	
	public UsageUnitSettingDto get(){
		String companyId = AppContexts.user().companyId();
		UsageUnitSettingDto result = new UsageUnitSettingDto();
		
		Optional<UsageUnitSetting> unitSetting = this.repo.findByCompany(companyId);
		
		if (unitSetting.isPresent()) {
			result.employee = unitSetting.get().isEmployee();
			result.employment = unitSetting.get().isEmployment();
			result.workPlace = unitSetting.get().isWorkPlace();
		}
		
		return result;
	}
}
