package nts.uk.ctx.sys.portal.dom.toppagepart.service;

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
import nts.uk.ctx.sys.portal.dom.mypage.setting.TopPagePartUseSetting;
import nts.uk.ctx.sys.portal.dom.placement.service.PlacementService;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartRepository;

/**
 * @author HieuLT
 */
@Stateless
public class TopPagePartServiceImpl implements TopPagePartService{

	@Inject
	private TopPagePartRepository topPagePartRepository;
	
	@Inject
	private PlacementService placementService;
	
	@Inject
	private MyPageSettingRepository	myPageSettingRepository;
	
	@Override
	public boolean isExist(String companyID, String code, int type) {
		Optional<TopPagePart> topPage = topPagePartRepository.findByCodeAndType(companyID, code, type);
		return topPage.isPresent();
	}

	@Override
	public void deleteTopPagePart(String companyID, String topPagePartID) {
		Optional<TopPagePart> checkTopPagePart = topPagePartRepository.find(topPagePartID);
		if (checkTopPagePart.isPresent()) {
			topPagePartRepository.remove(companyID, topPagePartID);
			myPageSettingRepository.removeTopPagePartUseSettingById(companyID, topPagePartID);
			placementService.deletePlacementByTopPagePart(companyID, topPagePartID);
		}
	}

	@Override
	public List<EnumConstant> getTopPagePartTypeByPGType(String companyID, PGType pgType) {
		List<EnumConstant> listTopPagePartType = EnumAdaptor.convertToValueNameList(TopPagePartType.class);
		List<Integer> checkingTopPagePartTypeValues = new ArrayList<Integer>();
		if (pgType == PGType.TopPage) {
			checkingTopPagePartTypeValues.add(TopPagePartType.Widget.value);
			checkingTopPagePartTypeValues.add(TopPagePartType.DashBoard.value);
			checkingTopPagePartTypeValues.add(TopPagePartType.FlowMenu.value);
			checkingTopPagePartTypeValues.add(TopPagePartType.ExternalUrl.value);
		}
		else if (pgType == PGType.TitleMenu) {
			checkingTopPagePartTypeValues.add(TopPagePartType.FlowMenu.value);
			checkingTopPagePartTypeValues.add(TopPagePartType.ExternalUrl.value);
		}
		else if (pgType == PGType.MyPage) {
			Optional<MyPageSetting> checkMyPageSetting = myPageSettingRepository.findByCompanyId(companyID);
			if (checkMyPageSetting.isPresent()) {
				MyPageSetting myPageSetting = checkMyPageSetting.get();
				if (myPageSetting.getUseMyPage() == UseDivision.Use) {
					if (myPageSetting.getUseWidget() == UseDivision.Use)
						checkingTopPagePartTypeValues.add(TopPagePartType.Widget.value);
					if (myPageSetting.getUseDashboard() == UseDivision.Use)
						checkingTopPagePartTypeValues.add(TopPagePartType.DashBoard.value);
					if (myPageSetting.getUseFlowMenu() == UseDivision.Use)
						checkingTopPagePartTypeValues.add(TopPagePartType.FlowMenu.value);
					if (myPageSetting.getExternalUrlPermission() == PermissionDivision.Allow)
						checkingTopPagePartTypeValues.add(TopPagePartType.ExternalUrl.value);
				}
			}
		}
		List<EnumConstant> usingTopPagePartType = new ArrayList<EnumConstant>();
		usingTopPagePartType.addAll(listTopPagePartType.stream().filter(o -> checkingTopPagePartTypeValues.contains(o.getValue())).collect(Collectors.toList()));
		return usingTopPagePartType;
	}

	@Override
	public List<TopPagePart> getAllActiveTopPagePart(String companyID, List<EnumConstant> usingTopPagePartTypes) {
		List<Integer> usingTopPagePartTypeIDs = usingTopPagePartTypes.stream().map(c -> c.getValue()).collect(Collectors.toList());
		List<String> usingTopPagePartIDs = new ArrayList<String>(); 
		List<TopPagePartUseSetting> topPagePartSettings = myPageSettingRepository.findTopPagePartUseSettingByCompanyId(companyID);
		for (TopPagePartUseSetting topPagePartUseSetting : topPagePartSettings) {
			if (topPagePartUseSetting.getUseDivision() == UseDivision.Use)
				usingTopPagePartIDs.add(topPagePartUseSetting.getTopPagePartId());
		}
		return topPagePartRepository.findByTypeAndIDs(companyID, usingTopPagePartTypeIDs, usingTopPagePartIDs);
	}
	
	@Override
	public List<TopPagePart> getActiveTopPagePartByID(String companyID, List<EnumConstant> usingTopPagePartTypes, List<String> topPagePartIDs) {
		List<Integer> usingTopPagePartTypeIDs = usingTopPagePartTypes.stream().map(c -> c.getValue()).collect(Collectors.toList());
		List<String> usingTopPagePartIDs = new ArrayList<String>();
		List<TopPagePartUseSetting> topPagePartSettings = myPageSettingRepository.findTopPagePartUseSettingByCompanyId(companyID);
		for (TopPagePartUseSetting topPagePartUseSetting : topPagePartSettings) {
			if (topPagePartUseSetting.getUseDivision() == UseDivision.Use)
				usingTopPagePartIDs.add(topPagePartUseSetting.getTopPagePartId());
		}
		topPagePartIDs.retainAll(usingTopPagePartIDs);
		return topPagePartRepository.findByTypeAndIDs(companyID, usingTopPagePartTypeIDs, topPagePartIDs);
	}

}