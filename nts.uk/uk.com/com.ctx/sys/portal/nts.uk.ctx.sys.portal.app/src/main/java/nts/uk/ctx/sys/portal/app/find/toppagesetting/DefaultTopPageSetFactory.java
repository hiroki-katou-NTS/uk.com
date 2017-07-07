package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.storage.FileStorage;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.portal.app.find.mypage.setting.MyPageSettingDto;
import nts.uk.ctx.sys.portal.app.find.mypage.setting.MyPageSettingFinder;
import nts.uk.ctx.sys.portal.app.find.placement.PlacementDto;
import nts.uk.ctx.sys.portal.app.find.placement.PlacementPartDto;
import nts.uk.ctx.sys.portal.app.find.toppage.TopPageDto;
import nts.uk.ctx.sys.portal.app.find.toppage.TopPageFinder;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.enums.System;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.enums.UseDivision;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenu;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenuRepository;
import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.layout.PGType;
import nts.uk.ctx.sys.portal.dom.mypage.MyPage;
import nts.uk.ctx.sys.portal.dom.mypage.MyPageRepository;
import nts.uk.ctx.sys.portal.dom.placement.Placement;
import nts.uk.ctx.sys.portal.dom.placement.PlacementRepository;
import nts.uk.ctx.sys.portal.dom.placement.externalurl.ExternalUrl;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenu;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;
import nts.uk.ctx.sys.portal.dom.toppagepart.service.TopPagePartService;
import nts.uk.ctx.sys.portal.dom.toppagesetting.PersonPermissionSetting;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageJobSet;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageJobSetRepository;
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
	@Inject
	private TopPageJobSetRepository topPageJobSet;
	
	@Inject
	private FlowMenuRepository repository;
	
	@Inject
	private FileStorage fileStorage;
	
	/**
	 * display my page
	 */
	@Override
	public LayoutForMyPageDto buildLayoutDto(Layout layout, List<Placement> placements) {
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		
		// get my page setting
		MyPageSettingDto myPage = myPageSetFinder.findByCompanyId(companyId);
		// get placement
		List<PlacementDto> placementDtos = buildPlacementDto(layout, placements, myPage);
		
		// get top page part id by flow menu
		List<String> listTopPagePartIdTypeFlow = placementDtos.stream()
				.filter(placementDto-> placementDto.getPlacementPartDto().getExternalUrl() == null 
						&& placementDto.getPlacementPartDto().getType().intValue() == TopPagePartType.FlowMenu.value)
				.map(x->x.getPlacementPartDto().getTopPagePartID())
				.distinct()
				.collect(Collectors.toList());
		
		// get placement by flow menu
		List<PlacementDto> placementFlows = placementDtos.stream()
				.filter(placementDto-> placementDto.getPlacementPartDto().getExternalUrl() == null
						&& placementDto.getPlacementPartDto().getType().intValue() == TopPagePartType.FlowMenu.value)
				.collect(Collectors.toList());
				
		// process data with flow menu
		List<FlowMenuPlusDto> flowmenuNew = new ArrayList<FlowMenuPlusDto>();
		if (!listTopPagePartIdTypeFlow.isEmpty()) {
			// get all flow menu by top page part id
			List<FlowMenu> flowmenus = repository.findByCodes(companyId, listTopPagePartIdTypeFlow);
			Map<String, FlowMenu> flowMenuMap = flowmenus.stream().collect(Collectors.toMap(FlowMenu::getToppagePartID, x->x));
			
			for (PlacementDto placementDto : placementFlows) {
                FlowMenu flowMenu = flowMenuMap.get(placementDto.getPlacementPartDto().getTopPagePartID());
                if (flowMenu == null) {
                	continue;
                }
                
                // get file infe
            	Optional<StoredFileInfo> fileInfo = fileStorage.getInfo(flowMenu.getFileID());
            	
                FlowMenuPlusDto fMenu = new FlowMenuPlusDto(flowMenu.getToppagePartID(),
                        flowMenu.getCode().v(),
                        flowMenu.getName().v(),
                        flowMenu.getType().value,
                        flowMenu.getWidth().v(),
                        flowMenu.getHeight().v(),
                        flowMenu.getFileID(),
                        fileInfo != null ? fileInfo.get().getOriginalName() : "",
                        flowMenu.getDefClassAtr().value,
                        placementDto.getRow(),
                        placementDto.getColumn());
                
                flowmenuNew.add(fMenu);
	        }
		}
		
		// process data with not flow menu
		List<PlacementDto> placementNew = placementDtos.stream()
				.filter(placementDto-> !(placementDto.getPlacementPartDto().getExternalUrl() == null 
						&& placementDto.getPlacementPartDto().getType().intValue() == TopPagePartType.FlowMenu.value))
				.collect(Collectors.toList());
		
		// return
		return new LayoutForMyPageDto(employeeId, layout.getLayoutID(), PGType.MYPAGE.value, flowmenuNew, placementNew);
	}
	
	//build placement my page
	private List<PlacementDto> buildPlacementDto(Layout layout, List<Placement> placements, MyPageSettingDto myPage) {
		List<TopPagePart> activeTopPageParts = topPagePartService.getAllActiveTopPagePart(layout.getCompanyID(), layout.getPgType());
		List<PlacementDto> placementDtos = new ArrayList<PlacementDto>();
		for (Placement placement : placements) {
			if (placement.isExternalUrl()) {
				if (myPage.getExternalUrlPermission().intValue() == UseDivision.Use.value) {
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
		return new PlacementPartDto(externalUrl.getWidth().v(), externalUrl.getHeight().v(), null, null, null, null,externalUrl.getUrl().v());
	}
	
	/**
	 * display top page
	 */
	@Override
	public LayoutForTopPageDto buildLayoutTopPage(Layout layout, List<Placement> placements) {
		String companyId = AppContexts.user().companyId();
		// get placement
		List<PlacementDto> placementDtos = buildPlacementTopPage(layout, placements);
		
		// get top page part id by flow menu
				List<String> listTopPagePartIdTypeFlow = placementDtos.stream()
						.filter(placementDto-> placementDto.getPlacementPartDto().getExternalUrl() == null 
								&& placementDto.getPlacementPartDto().getType().intValue() == TopPagePartType.FlowMenu.value)
						.map(x->x.getPlacementPartDto().getTopPagePartID())
						.distinct()
						.collect(Collectors.toList());
				
				// get placement by flow menu
				List<PlacementDto> placementFlows = placementDtos.stream()
						.filter(placementDto-> placementDto.getPlacementPartDto().getExternalUrl() == null
								&& placementDto.getPlacementPartDto().getType().intValue() == TopPagePartType.FlowMenu.value)
						.collect(Collectors.toList());
						
				// process data with flow menu
				List<FlowMenuPlusDto> flowmenuNew = new ArrayList<FlowMenuPlusDto>();
				if (!listTopPagePartIdTypeFlow.isEmpty()) {
					// get all flow menu by top page part id
					List<FlowMenu> flowmenus = repository.findByCodes(companyId, listTopPagePartIdTypeFlow);
					Map<String, FlowMenu> flowMenuMap = flowmenus.stream().collect(Collectors.toMap(FlowMenu::getToppagePartID, x->x));
					
					for (PlacementDto placementDto : placementFlows) {
		                FlowMenu flowMenu = flowMenuMap.get(placementDto.getPlacementPartDto().getTopPagePartID());
		                if (flowMenu == null) {
		                	continue;
		                }
		                
		                // get file infe
		            	Optional<StoredFileInfo> fileInfo = fileStorage.getInfo(flowMenu.getFileID());
		            	
		                FlowMenuPlusDto fMenu = new FlowMenuPlusDto(flowMenu.getToppagePartID(),
		                        flowMenu.getCode().v(),
		                        flowMenu.getName().v(),
		                        flowMenu.getType().value,
		                        flowMenu.getWidth().v(),
		                        flowMenu.getHeight().v(),
		                        flowMenu.getFileID(),
		                        fileInfo != null ? fileInfo.get().getOriginalName() : "",
		                        flowMenu.getDefClassAtr().value,
		                        placementDto.getRow(),
		                        placementDto.getColumn());
		                
		                flowmenuNew.add(fMenu);
			        }
				}
				
				// process data with not flow menu
				List<PlacementDto> placementNew = placementDtos.stream()
						.filter(placementDto-> !(placementDto.getPlacementPartDto().getExternalUrl() == null 
								&& placementDto.getPlacementPartDto().getType().intValue() == TopPagePartType.FlowMenu.value))
						.collect(Collectors.toList());
				
		return new LayoutForTopPageDto(layout.getCompanyID(), layout.getLayoutID(), layout.getPgType().value, flowmenuNew,placementNew,null);
	}
	/**
	 * build placement my page
	 * @param layout
	 * @param placements
	 * @return
	 */
	private List<PlacementDto> buildPlacementTopPage(Layout layout, List<Placement> placements) {
		List<TopPagePart> activeTopPageParts = topPagePartService.getAllActiveTopPagePart(layout.getCompanyID(), layout.getPgType());
		List<PlacementDto> placementDtos = new ArrayList<PlacementDto>();
		for (Placement placement : placements) {
			if (placement.isExternalUrl()) {
				ExternalUrl externalUrl = placement.getExternalUrl().get();
				placementDtos.add(new PlacementDto(placement.getPlacementID(), placement.getLayoutID(),
						placement.getColumn().v(), placement.getRow().v(), fromExternalUrl(externalUrl)));
				continue;
			}
			TopPagePart topPagePart = activeTopPageParts.stream()
										.filter(c -> c.getToppagePartID()
										.equals(placement.getToppagePartID())).findFirst().orElse(null);
			if (topPagePart != null) {
				placementDtos.add(new PlacementDto(placement.getPlacementID(), placement.getLayoutID(),
							placement.getColumn().v(), placement.getRow().v(), fromTopPagePart(topPagePart)));
			}
		}
		return placementDtos;
	}
	/**
	 * get by position
	 */
	@Override
	public LayoutAllDto getTopPageForPosition(JobPositionDto jobPosition, TopPageJobSet topPageJob) {
		/**
		 * check = true (When start: display top page)
		 * check = false (When start: display my page)
		 */
		Boolean check = false;
		//companyId
		String companyId = AppContexts.user().companyId();
		//employeeId
		String employeeId = AppContexts.user().employeeId();
		TopPageSelfSettingDto tpSelfSet = topPageSelfSet.getTopPageSelfSet();
		LayoutForTopPageDto layoutTopPage = null;
		//check my page: use or not use
		boolean checkMyPage = checkMyPageSet();
		//check top page: setting or not setting
		boolean checkTopPage = checkTopPageSet();
		if(topPageJob.getPersonPermissionSet() == PersonPermissionSetting.SET && tpSelfSet != null){//check topPageJob: setting or not setting
			//display top page self set (本人トップページ設定)-C
			check = true;
			layoutTopPage = getTopPageByCode(companyId,tpSelfSet.getCode(), System.Common.value, MenuClassification.TopPage.value, check);
			if (!checkMyPage) {//not use my page
				return new LayoutAllDto(null, layoutTopPage, check, checkMyPage, checkTopPage);
			}
			LayoutForMyPageDto layoutMypage = findLayoutMyPage();
			return new LayoutAllDto(layoutMypage, layoutTopPage, check, checkMyPage, checkTopPage);
		}
		//topPageJob: setting
		Optional<TopPagePersonSet> topPPerson = topPagePerson.getbyCode(companyId, employeeId);
		String code = topPageJob.getLoginMenuCode().toString();
		if(topPPerson.isPresent() && !StringUtil.isNullOrEmpty(code, true)){//login menu code & top page person
			//display top page person set (個人別トップページ設定)-B
			TopPagePersonSet tpPerson = topPPerson.get();
			check = true;
			layoutTopPage = getTopPageByCode(companyId, tpPerson.getTopMenuCode().toString(), tpPerson.getLoginSystem().value, tpPerson.getMenuClassification().value, check);
			MyPageSettingDto myPage = myPageSetFinder.findByCompanyId(companyId);
			if (myPage.getUseMyPage().intValue() == UseDivision.NotUse.value) {//khong su dung my page
				return new LayoutAllDto(null, layoutTopPage, check, false, checkTopPage);
			}
			LayoutForMyPageDto layoutMypage = findLayoutMyPage();
			return new LayoutAllDto(layoutMypage, layoutTopPage, check, true, checkTopPage);
		}
		//not top page person or not login menu code
		if(StringUtil.isNullOrEmpty(code, true)){//login menu code: empty
			//display my page
			check = false;
			layoutTopPage = null;
			if (!checkMyPage) {//not use my page
				return new LayoutAllDto(null, layoutTopPage, check, checkMyPage, checkTopPage);
			}
			//use my page
			LayoutForMyPageDto layoutMypage = findLayoutMyPage();
			return new LayoutAllDto(layoutMypage, layoutTopPage, check, checkMyPage, checkTopPage);
		}
		////display top page job title set (職位別トップページ設定)-A
		check = true;
		layoutTopPage = getTopPageByCode(companyId, topPageJob.getTopMenuCode().toString(), topPageJob.getLoginSystem().value, topPageJob.getMenuClassification().value, check);
		if (!checkMyPage) {//not use my page
			return new LayoutAllDto(null, layoutTopPage, check, checkMyPage, checkTopPage);
		}
		LayoutForMyPageDto layoutMypage = findLayoutMyPage();
		return new LayoutAllDto(layoutMypage, layoutTopPage, check, checkMyPage, checkTopPage);
	}

	/**
	 * get by not position
	 */
	@Override
	public LayoutAllDto getTopPageNotPosition() {
		/**
		 * check = true (When start: display top page)
		 * check = false (When start: display my page)
		 */
		Boolean check = false;
		//companyId
		String companyId = AppContexts.user().companyId();
		//employeeId
		String employeeId = AppContexts.user().employeeId();
		//get data from domain person set (個人別トップページ設定)
		Optional<TopPagePersonSet> topPPerson = topPagePerson.getbyCode(companyId, employeeId);
		LayoutForTopPageDto layoutToppage = null;
		//check my page: use or not use
		boolean checkMyPage = checkMyPageSet();
		//check top page: setting or not setting
		boolean checkTopPage = checkTopPageSet();
		if(topPPerson.isPresent()){//co tpPerson
			TopPagePersonSet tpPerson = topPPerson.get();
			String code = tpPerson.getLoginMenuCode().toString();
			if(!StringUtil.isNullOrEmpty(code,true)){//login menu code not empty
				//display top page person set (個人別トップページ設定)-B
				check = true;
				layoutToppage = getTopPageByCode(companyId,tpPerson.getTopMenuCode().toString(),tpPerson.getLoginSystem().value,tpPerson.getMenuClassification().value,check);
				if (!checkMyPage) {//not use my page
					return new LayoutAllDto(null, layoutToppage, true, checkMyPage, checkTopPage);
				}
				LayoutForMyPageDto layoutMypage = findLayoutMyPage();
				return new LayoutAllDto(layoutMypage, layoutToppage, check, checkMyPage, checkTopPage);
			}
		}
		check = false;
		//display only my page
		if (!checkMyPage) {//not use my page
			return new LayoutAllDto(null, null, true, checkMyPage, checkTopPage);
		}
		LayoutForMyPageDto layoutMypage = findLayoutMyPage();
		return new LayoutAllDto(layoutMypage, null, false, checkMyPage, checkTopPage);
	}

	/**
	 * check is top page or standard menu
	 */
	@Override
	public LayoutForTopPageDto getTopPageByCode(String companyId, String code, int system, int classification, boolean check) {
		if(classification == MenuClassification.TopPage.value){//topPage
			TopPageDto topPage = toppageFinder.findByCode(companyId, code, "0");
			Optional<Layout> layout = toppageRepository.find(topPage.getLayoutId(),  PGType.TOPPAGE.value);
			if (layout.isPresent()) {
				List<Placement> placements = placementRepository.findByLayout(topPage.getLayoutId());
				return buildLayoutTopPage(layout.get(), placements);
			}
			return null;
		}
		//standard menu
		Optional<StandardMenu> sMenu = standardMenu.getStandardMenubyCode(companyId, code, system, classification);
		if(sMenu.isPresent()){
			List<FlowMenuPlusDto> flowMenu = null;
			List<PlacementDto> placements = null;
			return new LayoutForTopPageDto(companyId, "", PGType.TOPPAGE.value, flowMenu, placements, sMenu.get().getUrl());
		}
		return null;
	}
	/**
	 * check my page: setting or not setting
	 */
	@Override
	public boolean checkMyPageSet(){
		//companyId
		String companyId = AppContexts.user().companyId();
		MyPageSettingDto myPage = myPageSetFinder.findByCompanyId(companyId);
		if (myPage!=null && myPage.getUseMyPage().intValue() == UseDivision.Use.value) {//co su dung my page
			return true;
		}
		return false;
	}
	/**
	 * check top page: use or not use
	 * @return
	 */
	@Override
	public boolean checkTopPageSet() {
		//companyId
		String companyId = AppContexts.user().companyId();
		List<String> lst = new ArrayList<>();
		//lay job position
		JobPositionDto jobPosition = topPageSelfSet.getJobPosition(AppContexts.user().employeeId());
		if(jobPosition != null){
			lst.add(jobPosition.getJobId());
			//lay top page job title set
			List<TopPageJobSet> lstTpJobSet = topPageJobSet.findByListJobId(companyId, lst);
			if(!lstTpJobSet.isEmpty()){
				TopPageJobSet tpJobSet = lstTpJobSet.get(0);
				if(tpJobSet != null && tpJobSet.getPersonPermissionSet().value == PersonPermissionSetting.SET.value){//co job title va duoc setting
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * find lay out my page
	 * @return
	 */
	@Override
	public LayoutForMyPageDto findLayoutMyPage() {
		//employeeId
		String employeeId = AppContexts.user().employeeId();
		LayoutForMyPageDto layoutMypage = null;
		Optional<MyPage> mPage = mypage.getMyPage(employeeId);
		if(!mPage.isPresent()){//register my page
			MyPage mypageNew = MyPage.createNew(employeeId);
			mypage.addMyPage(mypageNew);
			layoutMypage = new LayoutForMyPageDto(employeeId, mypageNew.getLayoutId(), PGType.MYPAGE.value, null, null);
			return layoutMypage;
		}
		Optional<Layout> layout = toppageRepository.find(mPage.get().getLayoutId(), PGType.MYPAGE.value);
		if(layout.isPresent()){
			List<Placement> placements = placementRepository.findByLayout(mPage.get().getLayoutId());
			if(!placements.isEmpty()){
				layoutMypage =buildLayoutDto(layout.get(), placements) ;
			}
		}else{
			layoutMypage = new LayoutForMyPageDto(employeeId, mPage.get().getLayoutId(), PGType.MYPAGE.value, null, null);
		}
		return layoutMypage;
	}

}
