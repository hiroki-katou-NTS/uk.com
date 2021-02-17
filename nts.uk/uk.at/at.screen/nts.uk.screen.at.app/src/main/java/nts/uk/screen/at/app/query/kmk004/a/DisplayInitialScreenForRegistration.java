package nts.uk.screen.at.app.query.kmk004.a;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.workrule.deformed.AggDeformedLaborSetting;
import nts.uk.ctx.at.shared.dom.workrule.deformed.AggDeformedLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.workrule.workform.FlexWorkMntSetRepository;
import nts.uk.ctx.at.shared.dom.workrule.workform.FlexWorkSet;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * 法定労働時間の登録（目次）の初期画面を表示する
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.A：法定労働時間の登録（目次）.メニュー別OCD.法定労働時間の登録（目次）の初期画面を表示する
 * @author chungnt
 *
 */

@Stateless
public class DisplayInitialScreenForRegistration {
	
	@Inject
	private FlexWorkMntSetRepository flexWorkRepository;
	
	@Inject
	private AggDeformedLaborSettingRepository aggDeformedLaborSettingRepository;
	

	public DisplayInitialScreenForRegistrationDto get () {
		DisplayInitialScreenForRegistrationDto result = new DisplayInitialScreenForRegistrationDto();
		String cid = AppContexts.user().companyId();
		
		Optional<AggDeformedLaborSetting> agg = this.aggDeformedLaborSettingRepository.findByCid(cid);
		Optional<FlexWorkSet> flex = this.flexWorkRepository.find(cid);
		
		if (flex.isPresent()) {
			result.setFlexWorkManaging(flex.get().getUseFlexWorkSetting().isUse());
		}
		result.setUseDeformedLabor(agg.map(m -> m.getUseDeformedLabor().isUse()).orElse(false));
		
		return result;
	}
	
}
