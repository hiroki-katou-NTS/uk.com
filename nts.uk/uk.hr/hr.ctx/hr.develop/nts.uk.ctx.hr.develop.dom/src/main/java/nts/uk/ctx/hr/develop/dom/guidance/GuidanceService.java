package nts.uk.ctx.hr.develop.dom.guidance;

import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;

@Stateless
public class GuidanceService {

	@Inject
	private GuidanceRepository guidanceRepo;

	// 操作ガイド表示条件の取得
	public Guidance getGuideDispSetting(String companyId) {
		Optional<Guidance> guidance = guidanceRepo.getGuidance(companyId);
		if(guidance.isPresent()) {
			return guidance.get();  
		}
		Guidance defaultGuidance = Guidance.createFromJavaType(companyId, false, 2, 100, new ArrayList<>());
		guidanceRepo.addGuidance(defaultGuidance);
		return defaultGuidance;
	}

	// 操作ガイド表示条件の追加
	public void addGuideDispSetting(String companyId, boolean usageFlgCommon, int guideMsgAreaRow, int guideMsgMaxNum) {
		Guidance defaultGuidance = Guidance.createFromJavaType(companyId, usageFlgCommon, guideMsgAreaRow, guideMsgMaxNum, new ArrayList<>());
		guidanceRepo.addGuidance(defaultGuidance);
	}
	
	// 操作ガイド表示条件の更新
	public void updateGuideDispSetting(String companyId, boolean usageFlgCommon, int guideMsgAreaRow, int guideMsgMaxNum) {
		Guidance defaultGuidance = Guidance.createFromJavaType(companyId, usageFlgCommon, guideMsgAreaRow, guideMsgMaxNum, new ArrayList<>());
		guidanceRepo.updateGuidance(defaultGuidance);
	}
	
	// 操作ガイドメッセージの取得
	public String getGuideMsg(String companyId, String categoryCode, String eventName, String programName, String screenName, boolean usageFlgByScreen) {
		Optional<Guidance> guidance = guidanceRepo.getGuidance(companyId);
		if(!guidance.isPresent()) {
			throw new BusinessException("MsgJ_JMM017_1");
		}
		Optional<GuideMsg> guideMsg = guidance.get().getGuideMsg().stream().filter(c->{
			return c.getCategoryCode().contains(categoryCode) 
					&& c.getEventName().contains(eventName) 
					&& c.getProgramName().contains(programName)
					&& c.getScreenName().contains(screenName)
					&& c.isUsageFlgByScreen() == usageFlgByScreen;
			}).findFirst();
		
		return guideMsg.isPresent()?guideMsg.get().getGuideMsg().v():"";
	}

}
