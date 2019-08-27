package nts.uk.ctx.hr.develop.app.guidance.find;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.hr.develop.app.guidance.dto.CategoryGuideDto;
import nts.uk.ctx.hr.develop.app.guidance.dto.GuidanceDto;
import nts.uk.ctx.hr.develop.app.guidance.dto.GuideMsgDto;
import nts.uk.ctx.hr.develop.app.guidance.dto.ParamFindScreen;
import nts.uk.ctx.hr.develop.app.guidance.dto.ScreenGuideParam;
import nts.uk.ctx.hr.develop.app.guidance.dto.ScreenGuideParamList;
import nts.uk.ctx.hr.develop.app.guidance.dto.ScreenGuideSettingDto;
import nts.uk.ctx.hr.develop.dom.guidance.GuidanceService;
import nts.uk.ctx.hr.develop.dom.guidance.IGuidance;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GuidanceFinder {

	@Inject
	private GuidanceService guidanceService;
	@Inject
	private IGuidance iGuidance;
	
	public GuidanceDto getGuideDispSetting(String companyId) {
		return new GuidanceDto(guidanceService.getGuideDispSetting(companyId));
	}
	
	public List<CategoryGuideDto> getGuideCategory(){
		List<CategoryGuideDto> result = new ArrayList<>();
//		2019/7時点でカテゴリを取得するAPIがないため、以下のリストをOutputとする。
//		Vi khong co  API lay category tai thoi diem thang 7/ 2019, nen list sau day se la Output 
		result.add(new CategoryGuideDto(0,""));
		result.add(new CategoryGuideDto(1,"共通"));
		result.add(new CategoryGuideDto(2, "勤次郎"));
		result.add(new CategoryGuideDto(3, "Ｑ太郎"));
		result.add(new CategoryGuideDto(4, "人事郎"));
		result.add(new CategoryGuideDto(5, "オフィスヘルパー"));
		result.add(new CategoryGuideDto(6, "共通ダイアログ"));
		
		return result;
	}
	
	public List<GuideMsgDto> getGuideMsg(ParamFindScreen param){
		String cId = AppContexts.user().companyId();
		List<GuideMsgDto>result = guidanceService.getGuideMsg(cId, param.categoryCode, param.eventName, param.programName, param.screenName, param.useSet)
				.stream().map(c -> new GuideMsgDto(c)).collect(Collectors.toList());
		if(result.isEmpty()) {
			throw new BusinessException("MsgJ_JMM017_2");
		}
		return result;
	}
	
	public List<ScreenGuideSettingDto> guideOperate(ScreenGuideParamList params) {
		String cId = AppContexts.user().companyId();
		List<ScreenGuideSettingDto> result = new ArrayList<>();
		for (ScreenGuideParam param : params.getScreenGuideParam()) {
			result.add(ScreenGuideSettingDto.fromDomain(iGuidance.getGuidance(cId, param.getProgramId(), param.getScreenId()), param.getProgramId(), param.getScreenId()));
		}
		return result;
	}
}
