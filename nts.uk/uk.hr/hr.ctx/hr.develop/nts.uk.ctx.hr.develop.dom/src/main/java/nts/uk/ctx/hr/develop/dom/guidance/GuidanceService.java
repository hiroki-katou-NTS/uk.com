package nts.uk.ctx.hr.develop.dom.guidance;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
		guidanceRepo.updateGuidance(companyId, usageFlgCommon, guideMsgAreaRow, guideMsgMaxNum);
	}
	
	// 操作ガイドメッセージの取得
	public List<GuideMsg> getGuideMsg(String companyId, String categoryCode, String eventName, String programName, String screenName, Boolean usageFlgByScreen) {
		Optional<Guidance> guidance = guidanceRepo.getGuidance(companyId);
		if(!guidance.isPresent()) {
			throw new BusinessException("MsgJ_JMM017_1");
		}
		List<GuideMsg> guideMsg = guidance.get().getGuideMsg().stream().filter(c->{
			return (categoryCode == null || c.getCategoryCode().contains(categoryCode)) 
					&& (eventName.equals("") || c.getEventName().contains(eventName)) 
					&& (programName.equals("") || c.getProgramName().contains(programName)) 
					&& (screenName.equals("") || c.getScreenName().contains(screenName)) 
					&& (usageFlgByScreen == null || c.isUsageFlgByScreen() == usageFlgByScreen); 
			}).collect(Collectors.toList());
		//sorted ASC（Category CD, Event CD, Program ID, Screen ID）
		Comparator<GuideMsg> compareByName = Comparator
                .comparing(GuideMsg::getCategoryCode)
                .thenComparing(GuideMsg::getEventCode)
                .thenComparing(GuideMsg::getProgramId)
                .thenComparing(GuideMsg::getScreenId);
		return guideMsg.stream().sorted(compareByName).collect(Collectors.toList());
	}

}
