package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
	 * check = true (hien thi top page truoc)
	 * check = false (hien thi my page truoc)
	 */
	Boolean check = false;
	
	/**
	 * hien thi my page
	 */
	@Override
	public LayoutForMyPageDto buildLayoutDto(Layout layout, List<Placement> placements) {
		 MyPageSettingDto myPage = myPageSetFinder.findByCompanyId(companyId);
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
			return new LayoutForMyPageDto(layout.getCompanyID(), layout.getLayoutID(), layout.getPgType().value, flowmenuNew,placementNew);
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
			Optional<TopPagePart> topPagePart = activeTopPageParts.stream()
					.filter(c -> c.getToppagePartID().equals(placement.getToppagePartID()))
					.findFirst();
			if (topPagePart.isPresent()) {
					placementDtos.add(new PlacementDto(placement.getPlacementID(), placement.getLayoutID(),
							placement.getColumn().v(), placement.getRow().v(), fromTopPagePart(topPagePart.get())));
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
	 * hien thi top page
	 */
	@Override
	public LayoutForTopPageDto buildLayoutTopPage(Layout layout, List<Placement> placements) {
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
			return new LayoutForTopPageDto(layout.getCompanyID(), layout.getLayoutID(), layout.getPgType().value, flowmenuNew,placementNew,null);
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
	public LayoutAllDto getTopPageForPosition(JobPositionDto jobPosition,TopPageJobSet topPageJob) {
		TopPageSelfSettingDto tpSelfSet = topPageSelfSet.getTopPageSelfSet();
		LayoutForTopPageDto layoutTopPage = null;
		if(topPageJob.getPersonPermissionSet().value ==1 && tpSelfSet !=null){//check co duoc setting khong
			//hien thi C
			check = true;
			layoutTopPage = getTopPageByCode(companyId,tpSelfSet.getCode(),0,8,check);
			LayoutForMyPageDto layoutMypage = null;
			MyPageSettingDto myPage = myPageSetFinder.findByCompanyId(companyId);
			if (myPage.getUseMyPage().intValue() == 0) {//khong su dung my page
				return new LayoutAllDto(null,layoutTopPage,check,false);
			}
			MyPage mPage = mypage.getMyPage(employeeId);
			if(mPage == null){//dang ky my page
				String layoutId = UUID.randomUUID().toString();
				MyPage mypageNew = new MyPage(employeeId,layoutId);
				mypage.addMyPage(mypageNew);
				layoutMypage = null;
			} else {
				Optional<Layout> layout = toppageRepository.find(mPage.getLayoutId(),0);
				if(layout.isPresent()){
					List<Placement> placements = placementRepository.findByLayout(mPage.getLayoutId());
					if(!placements.isEmpty()){
						layoutMypage =buildLayoutDto(layout.get(),placements) ;
					}
				}
			}
			return new LayoutAllDto(layoutMypage,layoutTopPage,check,true);
		}else{//khong duoc setting
			Optional<TopPagePersonSet> tpPerson = topPagePerson.getbyCode(companyId, employeeId);
			String code = topPageJob.getLoginMenuCode().toString();
			if(tpPerson.isPresent() && !StringUtil.isNullOrEmpty(code,true)){//ktra login menu code co hay khong
				//hien thi B
				check = true;
				layoutTopPage = getTopPageByCode(companyId,tpPerson.get().getTopMenuCode().toString(),tpPerson.get().getLoginSystem().value,tpPerson.get().getMenuClassification().value,check);
				LayoutForMyPageDto layoutMypage = null;
				MyPageSettingDto myPage = myPageSetFinder.findByCompanyId(companyId);
				if (myPage.getUseMyPage().intValue() == 0) {//khong su dung my page
					return new LayoutAllDto(null,layoutTopPage,check,false);
				}
				MyPage mPage = mypage.getMyPage(employeeId);
				if(mPage == null){//dang ky my page
					String layoutId = UUID.randomUUID().toString();
					MyPage mypageNew = new MyPage(employeeId,layoutId);
					mypage.addMyPage(mypageNew);
					layoutMypage = null;
				} else {
					Optional<Layout> layout = toppageRepository.find(mPage.getLayoutId(),0);
					if(layout.isPresent()){
						List<Placement> placements = placementRepository.findByLayout(mPage.getLayoutId());
						if(!placements.isEmpty()){
							layoutMypage =buildLayoutDto(layout.get(),placements) ;
						}
					}
				}
				return new LayoutAllDto(layoutMypage,layoutTopPage,check,true);
			}else{
				if(StringUtil.isNullOrEmpty(code,true)){//khong co login menu code
					//chi hien thi my page
					check = false;
					layoutTopPage = null;
					LayoutForMyPageDto layoutMypage = null;
					MyPageSettingDto myPage = myPageSetFinder.findByCompanyId(companyId);
					if (myPage.getUseMyPage().intValue() == 0) {//khong su dung my page
						return new LayoutAllDto(null,layoutTopPage,check,false);
					}
					//duoc du dung my page
					MyPage mPage = mypage.getMyPage(employeeId);
					if(mPage == null){//dang ky my page
						String layoutId = UUID.randomUUID().toString();
						MyPage mypageNew = new MyPage(employeeId,layoutId);
						mypage.addMyPage(mypageNew);
						layoutMypage = null;
					} else {
						Optional<Layout> layout = toppageRepository.find(mPage.getLayoutId(),0);
						if(layout.isPresent()){
							List<Placement> placements = placementRepository.findByLayout(mPage.getLayoutId());
							if(!placements.isEmpty()){
								layoutMypage =buildLayoutDto(layout.get(),placements) ;
							}
						}
					}
					return new LayoutAllDto(layoutMypage,layoutTopPage,check,true);
				}else{
					//hien thi A
					check = true;
					layoutTopPage = getTopPageByCode(companyId,topPageJob.getTopMenuCode().toString(),topPageJob.getLoginSystem().value,topPageJob.getMenuClassification().value,check);
					LayoutForMyPageDto layoutMypage = null;
					MyPageSettingDto myPage = myPageSetFinder.findByCompanyId(companyId);
					if (myPage.getUseMyPage().intValue() == 0) {//khong su dung my page
						return new LayoutAllDto(null,layoutTopPage,check,false);
					}
					MyPage mPage = mypage.getMyPage(employeeId);
					if(mPage == null){//dang ky my page
						String layoutId = UUID.randomUUID().toString();
						MyPage mypageNew = new MyPage(employeeId,layoutId);
						mypage.addMyPage(mypageNew);
						layoutMypage = null;
					} else {
						Optional<Layout> layout = toppageRepository.find(mPage.getLayoutId(),0);
						if(layout.isPresent()){
							List<Placement> placements = placementRepository.findByLayout(mPage.getLayoutId());
							if(!placements.isEmpty()){
								layoutMypage =buildLayoutDto(layout.get(),placements) ;
							}
						}
					}
					return new LayoutAllDto(layoutMypage,layoutTopPage,check,true);
				}
			}

		}
	}

	/**
	 * Lay khong theo chuc vu
	 */
	@Override
	public LayoutAllDto getTopPageNotPosition() {
		//lay du lieu bang person set
		Optional<TopPagePersonSet> tpPerson = topPagePerson.getbyCode(companyId, employeeId);
		LayoutForTopPageDto layoutToppage = null;
		LayoutForMyPageDto layoutMypage = null;
		if(tpPerson.isPresent()){//co tpPerson
			String code = tpPerson.get().getLoginMenuCode().toString();
			if(!StringUtil.isNullOrEmpty(code,true)){//co login menu code
				check = true;//hien thi B
				layoutToppage = getTopPageByCode(companyId,tpPerson.get().getTopMenuCode().toString(),tpPerson.get().getLoginSystem().value,tpPerson.get().getMenuClassification().value,check);
				MyPageSettingDto myPage = myPageSetFinder.findByCompanyId(companyId);
				if (myPage.getUseMyPage().intValue() == 0) {//khong su dung my page
					return new LayoutAllDto(null,null,true,false);
				}
				MyPage mPage = mypage.getMyPage(employeeId);
				if(mPage == null){//dang ky my page
					String layoutId = UUID.randomUUID().toString();
					MyPage mypageNew = new MyPage(employeeId,layoutId);
					mypage.addMyPage(mypageNew);
					layoutMypage = null;
				} else {
					Optional<Layout> layout2 = toppageRepository.find(mPage.getLayoutId(),0);
					List<Placement> placements = placementRepository.findByLayout(mPage.getLayoutId());
					layoutMypage =buildLayoutDto(layout2.get(),placements) ;
				}
				return new LayoutAllDto(layoutMypage,layoutToppage,check,true);
			}
		}
		check = false;
		//chi hien thi my page
		MyPageSettingDto myPage = myPageSetFinder.findByCompanyId(companyId);
		if (myPage.getUseMyPage().intValue() == 0) {//khong su dung my page
			return new LayoutAllDto(null,null,true,false);
		}
		MyPage mPage = mypage.getMyPage(employeeId);
		if(mPage == null){//dang ky my page
			String layoutId = UUID.randomUUID().toString();
			MyPage mypageNew = new MyPage(employeeId,layoutId);
			mypage.addMyPage(mypageNew);
			layoutMypage = null;
		} else {//co my page
			Optional<Layout> layout = toppageRepository.find(mPage.getLayoutId(),2);
			if(layout.isPresent()){
				List<Placement> placements = placementRepository.findByLayout(mPage.getLayoutId());
				if(!placements.isEmpty()){
					layoutMypage =buildLayoutDto(layout.get(),placements);
				}
			}
		}
		return new LayoutAllDto(layoutMypage,null,false,true);
	}

	/**
	 * ktra xem la top page hay standar menu
	 */
	@Override
	public LayoutForTopPageDto getTopPageByCode(String companyId, String code, int system, int classification,boolean check) {
		if(classification==8){//topPage
			TopPageDto topPage = toppageFinder.findByCode(companyId, code, "0");
			Optional<Layout> layout = toppageRepository.find(topPage.getLayoutId(),2);
			if (layout.isPresent()) {
				List<Placement> placements = placementRepository.findByLayout(topPage.getLayoutId());
			return buildLayoutTopPage(layout.get(),placements);
			}
			return null;
		}else{//standard menu
			Optional<StandardMenu> sMenu = standardMenu.getStandardMenubyCode(companyId, code, system, classification);
			if(sMenu.isPresent()){
				List<FlowMenuPlusDto> flowMenu = null;
				List<PlacementDto> placements = null;
				return new LayoutForTopPageDto(companyId, "", 0, flowMenu,placements,sMenu.get().getUrl());
			}
			return null;
		}
		
		
		
	}

}
