package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.portal.app.find.flowmenu.FlowMenuDto;
import nts.uk.ctx.sys.portal.app.find.flowmenu.FlowMenuFinder;
import nts.uk.ctx.sys.portal.app.find.mypage.setting.MyPageSettingDto;
import nts.uk.ctx.sys.portal.app.find.mypage.setting.MyPageSettingFinder;
import nts.uk.ctx.sys.portal.app.find.placement.PlacementDto;
import nts.uk.ctx.sys.portal.app.find.placement.PlacementPartDto;
import nts.uk.ctx.sys.portal.app.find.toppage.TopPageDto;
import nts.uk.ctx.sys.portal.app.find.toppage.TopPageFinder;
import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.mypage.MyPage;
import nts.uk.ctx.sys.portal.dom.mypage.MyPageRepository;
import nts.uk.ctx.sys.portal.dom.placement.Placement;
import nts.uk.ctx.sys.portal.dom.placement.PlacementRepository;
import nts.uk.ctx.sys.portal.dom.placement.externalurl.ExternalUrl;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenu;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;
import nts.uk.ctx.sys.portal.dom.toppagepart.service.TopPagePartService;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageJobSet;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPagePersonSet;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPagePersonSetRepository;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageSelfSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class DefaultTopPageSetFactory implements TopPageSetFactory {

	@Inject
	private TopPagePartService topPagePartService;
	@Inject
	private FlowMenuFinder flowmenu;
	@Inject
	private TopPageSelfSettingFinder topPageSelfSet;
	@Inject
	private TopPagePersonSetRepository topPagePerson;
	@Inject
	private StandardMenuRepository standardMenu;
	@Inject
	private TopPageFinder toppageFinder;
	@Inject
	private MyPageSettingFinder myPageSetFinder;
	@Inject
	private TopPageSelfSetRepository toppageRepository;
	@Inject
	private PlacementRepository placementRepository;
	@Inject
	private MyPageRepository mypage;
	//companyId
	String companyId = AppContexts.user().companyId();
	//employeeId
	String employeeId = AppContexts.user().employeeId();
	/**
	 * my page
	 */
	@Override
	public LayoutForMyPageDto buildLayoutDto(Layout layout, List<Placement> placements) {
		 MyPageSettingDto myPage = myPageSetFinder.findByCompanyId(companyId);
		if (myPage.getUseMyPage().intValue() == 0) {
			return buildLayoutTopPage(layout,placements);
		} else {
			//lay my page
//			MyPage myPage = mypage.getMyPage(employeeId, layoutId)
			List<PlacementDto> placementDtos = buildPlacementDto(layout, placements, myPage);
			List<FlowMenuPlusDto> flowmenuNew = new ArrayList<FlowMenuPlusDto>();
			List<PlacementDto> placementNew = new ArrayList<PlacementDto>();
			for (PlacementDto placementDto : placementDtos) {
				if(placementDto.getPlacementPartDto().getExternalUrl()==null && placementDto.getPlacementPartDto().getType().intValue()==2){
					FlowMenuDto flowMenu = flowmenu.getFlowMenu(placementDto.getPlacementPartDto().getTopPagePartID());
					FlowMenuPlusDto fMenu = new FlowMenuPlusDto(flowMenu.getToppagePartID(),
							flowMenu.getTopPageCode(),
							flowMenu.getTopPageName(),
							flowMenu.getType(),
							flowMenu.getWidthSize(),
							flowMenu.getHeightSize(),
							flowMenu.getFileID(),
							flowMenu.getFileName(),
							flowMenu.getDefClassAtr(),
							placementDto.getRow(),
							placementDto.getColumn());
					if(flowMenu != null) flowmenuNew.add(fMenu);
				}else{
					placementNew.add(placementDto);
				}
			}
			return new LayoutForMyPageDto(layout.getCompanyID(), layout.getLayoutID(), layout.getPgType().value, flowmenuNew,placementNew,null);
		}
	}

	private List<PlacementDto> buildPlacementDto(Layout layout, List<Placement> placements, MyPageSettingDto myPage) {
		List<TopPagePart> activeTopPageParts = topPagePartService.getAllActiveTopPagePart(layout.getCompanyID(),layout.getPgType());

		List<PlacementDto> placementDtos = new ArrayList<PlacementDto>();
		for (Placement placement : placements) {
			if (placement.isExternalUrl()) {
				if (myPage.getExternalUrlPermission().intValue() == 1) {
					ExternalUrl externalUrl = placement.getExternalUrl().get();
					placementDtos.add(new PlacementDto(placement.getPlacementID(), placement.getLayoutID(),
							placement.getColumn().v(), placement.getRow().v(), fromExternalUrl(externalUrl)));
				}
				continue;
			}
			TopPagePart topPagePart = activeTopPageParts.stream()
					.filter(c -> c.getToppagePartID().equals(placement.getToppagePartID())).findFirst().orElse(null);
			if (topPagePart != null) {
					placementDtos.add(new PlacementDto(placement.getPlacementID(), placement.getLayoutID(),
							placement.getColumn().v(), placement.getRow().v(), fromTopPagePart(topPagePart)));
			}
		}
		return placementDtos;
	}

	private PlacementPartDto fromTopPagePart(TopPagePart topPagePart) {
		return new PlacementPartDto(topPagePart.getWidth().v(), topPagePart.getHeight().v(),
				topPagePart.getToppagePartID(), topPagePart.getCode().v(), topPagePart.getName().v(),
				topPagePart.getType().value, null);
	}

	private PlacementPartDto fromExternalUrl(ExternalUrl externalUrl) {
		return new PlacementPartDto(externalUrl.getWidth().v(), externalUrl.getHeight().v(), null, null, null, null,
				externalUrl.getUrl().v());
	}
	/**
	 * top page
	 */
	@Override
	public LayoutForMyPageDto buildLayoutTopPage(Layout layout, List<Placement> placements) {
			List<PlacementDto> placementDtos = buildPlacementTopPage(layout, placements);
			List<FlowMenuPlusDto> flowmenuNew = new ArrayList<FlowMenuPlusDto>();
			List<PlacementDto> placementNew = new ArrayList<PlacementDto>();
			for (PlacementDto placementDto : placementDtos) {
				if(placementDto.getPlacementPartDto().getExternalUrl()==null && placementDto.getPlacementPartDto().getType().intValue()==2){
						FlowMenuDto flowMenu = flowmenu.getFlowMenu(placementDto.getPlacementPartDto().getTopPagePartID());
						FlowMenuPlusDto fMenu = new FlowMenuPlusDto(flowMenu.getToppagePartID(),
								flowMenu.getTopPageCode(),
								flowMenu.getTopPageName(),
								flowMenu.getType(),
								flowMenu.getWidthSize(),
								flowMenu.getHeightSize(),
								flowMenu.getFileID(),
								flowMenu.getFileName(),
								flowMenu.getDefClassAtr(),
								placementDto.getRow(),
								placementDto.getColumn());
						if(flowMenu != null) flowmenuNew.add(fMenu);
				}else{
					placementNew.add(placementDto);
				}
			}
			return new LayoutForMyPageDto(layout.getCompanyID(), layout.getLayoutID(), layout.getPgType().value, flowmenuNew,placementNew,null);
	}
	private List<PlacementDto> buildPlacementTopPage(Layout layout, List<Placement> placements) {
		List<TopPagePart> activeTopPageParts = topPagePartService.getAllActiveTopPagePart(layout.getCompanyID(),layout.getPgType());

		List<PlacementDto> placementDtos = new ArrayList<PlacementDto>();
		for (Placement placement : placements) {
			if (placement.isExternalUrl()) {
					ExternalUrl externalUrl = placement.getExternalUrl().get();
					placementDtos.add(new PlacementDto(placement.getPlacementID(), placement.getLayoutID(),
							placement.getColumn().v(), placement.getRow().v(), fromExternalUrl(externalUrl)));
					continue;
			}
			TopPagePart topPagePart = activeTopPageParts.stream()
					.filter(c -> c.getToppagePartID().equals(placement.getToppagePartID())).findFirst().orElse(null);
			if (topPagePart != null) {
					placementDtos.add(new PlacementDto(placement.getPlacementID(), placement.getLayoutID(),
							placement.getColumn().v(), placement.getRow().v(), fromTopPagePart(topPagePart)));
			}
		}
		return placementDtos;
	}
	/**
	 * Lay theo chuc vu
	 */
	@Override
	public LayoutForMyPageDto getTopPageForPosition(JobPositionDto jobPosition,TopPageJobSet topPageJob) {
		TopPageSelfSettingDto tpSelfSet = topPageSelfSet.getTopPageSelfSet();
		LayoutForMyPageDto layout = null;
		if(topPageJob.getPersonPermissionSet().value ==1 && tpSelfSet !=null){//check co duoc setting khong
			//hien thi C
			layout = getTopPageByCode(companyId,tpSelfSet.getCode(),0,8);
		}else{//khong duoc setting
			TopPagePersonSet tpPerson = topPagePerson.findBySid(companyId, employeeId).get();
			String code = topPageJob.getLoginMenuCode().toString();
			if(tpPerson!=null && !StringUtil.isNullOrEmpty(code,true)){//ktra login menu code co hay khong
				//hien thi B
				layout = getTopPageByCode(companyId,tpPerson.getTopMenuCode().toString(),tpPerson.getLoginSystem().value,tpPerson.getMenuClassification().value);
			}else{
				if(StringUtil.isNullOrEmpty(code,true)){
					return null;
				}else{
					//hien thi A
					layout = getTopPageByCode(companyId,topPageJob.getTopMenuCode().toString(),topPageJob.getLoginSystem().value,topPageJob.getMenuClassification().value);
				}
			}

		}
		return layout;
	}

	/**
	 * Lay khong theo chuc vu
	 */
	@Override
	public LayoutForMyPageDto getTopPageNotPosition(TopPagePersonSet tpPerson) {
		String code = tpPerson.getLoginMenuCode().toString();
		if(!StringUtil.isNullOrEmpty(code,true)){//ktra login menu code co hay khong
			return null;//hien thi B
		}
		return null;
	}

	@Override
	public LayoutForMyPageDto getTopPageByCode(String companyId, String code, int system, int classification) {
		if(classification==8){//topPage
			TopPageDto topPage = toppageFinder.findByCode(companyId, code, "0");
			Optional<Layout> layout = toppageRepository.find(topPage.getLayoutId(),2);
			if (layout.isPresent()) {
				List<Placement> placements = placementRepository.findByLayout(topPage.getLayoutId());
			return buildLayoutDto(layout.get(),placements);
			}
			return null;
		}else{//standard menu
			Optional<StandardMenu> sMenu = standardMenu.getStandardMenubyCode(companyId, code, system, classification);
			if(sMenu.isPresent()){
				List<FlowMenuPlusDto> flowMenu = null;
				List<PlacementDto> placements = null;
				return new LayoutForMyPageDto(companyId, "", 0, flowMenu,placements,sMenu.get().getUrl());
			}
			return null;
		}
		
		
		
	}

}
