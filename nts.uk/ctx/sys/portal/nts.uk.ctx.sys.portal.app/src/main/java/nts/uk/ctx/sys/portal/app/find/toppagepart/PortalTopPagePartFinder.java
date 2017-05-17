package nts.uk.ctx.sys.portal.app.find.toppagepart;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.sys.portal.dom.enums.PermissionDivision;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.enums.UseDivision;
import nts.uk.ctx.sys.portal.dom.layout.PGType;
import nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSetting;
import nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSettingRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author LamDT
 */
@Stateless
public class PortalTopPagePartFinder {
	
	@Inject
	private MyPageSettingRepository myPageSettingRepository;
	
	@Inject
	private TopPagePartRepository topPagePartRepository;
	
	/**
	 * Find all TopPagePart and TopPagePartType
	 * @return ActiveTopPagePartDto
	 */
	public ActiveTopPagePartDto findAll(int pgType) {
		// Company ID
		String companyID = AppContexts.user().companyId();
		
		// List TopPage Part Type
		List<EnumConstant> listTopPagePartType = EnumAdaptor.convertToValueNameList(TopPagePartType.class);
		List<Integer> checkingValues = new ArrayList<Integer>();
		if (pgType == PGType.TopPage.value) {
			checkingValues.add(TopPagePartType.Widget.value);
			checkingValues.add(TopPagePartType.DashBoard.value);
			checkingValues.add(TopPagePartType.FlowMenu.value);
			checkingValues.add(TopPagePartType.ExternalUrl.value);
		}
		if (pgType == PGType.TitleMenu.value) {
			checkingValues.add(TopPagePartType.FlowMenu.value);
			checkingValues.add(TopPagePartType.ExternalUrl.value);
		}
		if (pgType == PGType.MyPage.value) {
			Optional<MyPageSetting> checkPageSetting = myPageSettingRepository.findByCompanyId(companyID);
			if (checkPageSetting.isPresent()) {
				MyPageSetting myPageSetting = checkPageSetting.get();
				if (myPageSetting.getUseMyPage() == UseDivision.Use) {
					if (myPageSetting.getUseWidget() == UseDivision.Use)
						checkingValues.add(TopPagePartType.Widget.value);
					if (myPageSetting.getUseDashboard() == UseDivision.Use)
						checkingValues.add(TopPagePartType.DashBoard.value);
					if (myPageSetting.getUseFlowMenu() == UseDivision.Use)
						checkingValues.add(TopPagePartType.FlowMenu.value);
					if (myPageSetting.getExternalUrlPermission() == PermissionDivision.Allow)
						checkingValues.add(TopPagePartType.ExternalUrl.value);
				}
			}
		}
		List<EnumConstant> usingTopPagePartType = new ArrayList<EnumConstant>();
		usingTopPagePartType.addAll(listTopPagePartType.stream().filter(o -> checkingValues.contains(o.getValue())).collect(Collectors.toList()));
		
		//List TopPage Part
		List<TopPagePartDto> listTopPagePart = topPagePartRepository.findAll(companyID).stream()
				.map(topPagePart -> TopPagePartDto.fromDomain(topPagePart))
				.collect(Collectors.toList());

		// Build Dto
		ActiveTopPagePartDto activeTopPagePartDto = new ActiveTopPagePartDto(usingTopPagePartType, listTopPagePart);
		return activeTopPagePartDto;
	}

}
