package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.portal.app.find.standardmenu.StandardMenuDto;
import nts.uk.ctx.sys.portal.app.find.toppage.TopPageDto;
import nts.uk.ctx.sys.portal.app.find.toppage.TopPageFinder;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenu;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenuRepository;
import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.layout.LayoutNew;
import nts.uk.ctx.sys.portal.dom.layout.LayoutNewRepository;
import nts.uk.ctx.sys.portal.dom.layout.LayoutType;
import nts.uk.ctx.sys.portal.dom.layout.PGType;
import nts.uk.ctx.sys.portal.dom.placement.Placement;
import nts.uk.ctx.sys.portal.dom.placement.PlacementRepository;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenu;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository;
import nts.uk.ctx.sys.portal.dom.toppage.LayoutDisplayType;
import nts.uk.ctx.sys.portal.dom.toppage.ToppageNew;
import nts.uk.ctx.sys.portal.dom.toppage.ToppageNewRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.CreateFlowMenu;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.CreateFlowMenuRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.FlowMenuLayout;
import nts.uk.ctx.sys.portal.dom.toppagesetting.PortalJobTitleAdapter;
import nts.uk.ctx.sys.portal.dom.toppagesetting.PortalJobTitleImport;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageJobSet;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageJobSetRepository;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageSelfSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class DisplayMyPageFinder {
	@Inject
	private TopPageSelfSetRepository toppageRepository;
	@Inject
	private PlacementRepository placementRepository;
	@Inject
	private TopPageSetFactory topPageSet;
	@Inject
	private TopPageFinder toppageFinder;
	@Inject
	private TopPageJobSetRepository topPageJobSet;
	@Inject
	private PortalJobTitleAdapter jobTitleAdapter;
	@Inject 
	private ToppageNewRepository topPageRepo;
	@Inject
	private LayoutNewRepository layoutRepo;
	@Inject
	private CreateFlowMenuRepository cFlowMenuRepo;
	@Inject
	private FlowMenuRepository flowMenuRepository;
	@Inject
	private StandardMenuRepository standardMenuRepo;
	
	private static final String IS_LOGIN = "login";

	/**
	 * find layout (top page)
	 * 
	 * @param topPageCode
	 * @return
	 */
	public LayoutAllDto findLayoutTopPage(String fromScreen, String topPageCode) {
		// companyId
		String companyId = AppContexts.user().companyId();
		if (topPageCode != null && topPageCode != "") {// co top page code
			LayoutForMyPageDto layoutMypage = topPageSet.findLayoutMyPage();
			// check my page: use or not use
			boolean checkMyPage = topPageSet.checkMyPageSet();
			// check top page: setting or not setting
			boolean checkTopPage = topPageSet.checkTopPageSet();
			TopPageDto topPage = toppageFinder.findByCode(companyId, topPageCode, "0");
			if (topPage == null) {// data is empty
				return new LayoutAllDto(layoutMypage, null, true, checkMyPage, checkTopPage);
			}
			Optional<Layout> layout = toppageRepository.find(topPage.getLayoutId(), PGType.TOPPAGE.value);
			if (layout.isPresent()) {// co du lieu
				List<Placement> placements = placementRepository.findByLayout(topPage.getLayoutId());
				LayoutForTopPageDto layoutTopPage = topPageSet.buildLayoutTopPage(layout.get(), placements);
				return new LayoutAllDto(layoutMypage, layoutTopPage, true, checkMyPage, checkTopPage);
			}
			return new LayoutAllDto(layoutMypage, null, true, checkMyPage, checkTopPage);
		}
		// top page code is empty
		// get position(所属職位履歴)
		Optional<PortalJobTitleImport> jobPosition = jobTitleAdapter.getJobPosition(AppContexts.user().employeeId());
		List<String> lstJobId = new ArrayList<>();
		if (!jobPosition.isPresent()) {
			return topPageSet.getTopPageNotPosition(fromScreen);
		}

		lstJobId.add(jobPosition.get().getJobTitleID());

		// lay top page job title set
		List<TopPageJobSet> lstTpJobSet = topPageJobSet.findByListJobId(companyId, lstJobId);
		if (lstTpJobSet.isEmpty()) {// position and job setting
			return topPageSet.getTopPageNotPosition(fromScreen);
		}

		TopPageJobSet tpJobSet = lstTpJobSet.get(0);

		return topPageSet.getTopPageForPosition(fromScreen, jobPosition.get(), tpJobSet);

	}
	
	/**
	 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG008_トップページ.A：トップページ.アルゴリズム.新規起動.新規起動
	 * 
	 * @param param
	 * @return
	 */
	public DataTopPage startTopPage(StartTopPageParam param) {
		DataTopPage result = DataTopPage.builder().build();
		String cId = AppContexts.user().companyId();
		// inputトップページコード
		// 指定がない場合
		if (!StringUtil.isNullOrEmpty(param.getTopPageCode(), true)) {
			DisplayInTopPage dataDisplay = this.displayTopPage(param.getTopPageCode());
			result.setDisplayTopPage(dataDisplay);
		// 指定がある場合
		} else {
			Optional<String> displayCode = this.getTopPageDisplay(param.getFromScreen(), param.getTopPageSetting());
			//	標準メニューの場合
			if (param.getTopPageSetting().get().getMenuClassification() != MenuClassification.TopPage.value) {
				result.setMenuClassification(MenuClassification.Standard.value);
				Optional<StandardMenu> standardMenu = this.standardMenuRepo.getStandardMenubyCode(cId, param.getTopPageSetting().get().getLoginMenuCode(),
						param.getTopPageSetting().get().getSystem(), param.getTopPageSetting().get().getMenuClassification());
				if(standardMenu.isPresent()) {
					result.setStandardMenu(StandardMenuDto.fromDomain(standardMenu.get()));
				}
				//	トップページの場合
			} else if (param.getTopPageSetting().get().getMenuClassification() == MenuClassification.TopPage.value) {
				DisplayInTopPage dataDisplay = this.displayTopPage(displayCode.get());
				result.setDisplayTopPage(dataDisplay);
			}
		}

		return result;
	}

	/**
	 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG015_トップページの作成.F：プレビュー.アルゴリズム.トップページを表示する.トップページを表示する
	 * 
	 * @param topPageCode トップページコード
	 */
	public DisplayInTopPage displayTopPage(String topPageCode) {
		String cId = AppContexts.user().companyId(); 
		//	アルゴリズム「トップページを取得する」を実行する
		Optional<ToppageNew> topPage = this.topPageRepo.getByCidAndCode(cId, topPageCode);
		DisplayInTopPage result = new DisplayInTopPage();
		
		// 取得したレイアウト枠１～３を画面の枠に合わせて設置する
		Optional<LayoutNew> layout1 = Optional.empty();
		Optional<LayoutNew> layout2 = Optional.empty();
		Optional<LayoutNew> layout3 = Optional.empty();

		// ドメインモデル「レイアウト」（レイアウト枠１）が存在している
		if (topPage.isPresent()) {
			result.setLayoutDisplayType(topPage.get().getLayoutDisp().value);
			//	レイアウトの表示種類＝「中１」
			if (topPage.get().getLayoutDisp() == LayoutDisplayType.MIDDLE_ONE) {
				layout1 = this.layoutRepo.getByCidAndCode(cId, topPageCode, BigDecimal.valueOf(0));
			} else if (topPage.get().getLayoutDisp() == LayoutDisplayType.LEFT_1_RIGHT_2
					|| topPage.get().getLayoutDisp() == LayoutDisplayType.LEFT_2_RIGHT_1) {
				//	レイアウトの表示種類＝「左１右２」、「左２右１」
				layout1 = this.layoutRepo.getByCidAndCode(cId, topPageCode, BigDecimal.valueOf(0));
				layout2 = this.layoutRepo.getByCidAndCode(cId, topPageCode, BigDecimal.valueOf(1));
			} else {
				//	レイアウトの表示種類＝「左２中１右３」
				layout1 = this.layoutRepo.getByCidAndCode(cId, topPageCode, BigDecimal.valueOf(0));
				layout2 = this.layoutRepo.getByCidAndCode(cId, topPageCode, BigDecimal.valueOf(1));
				layout3 = this.layoutRepo.getByCidAndCode(cId, topPageCode, BigDecimal.valueOf(2));
			}
		}

		//	存在する場合
		if (layout1.isPresent()) {
			List<FlowMenuOutputCCG008>  listFlow = new ArrayList<FlowMenuOutputCCG008>();
			//	フローメニューの場合
			if (layout1.get().getLayoutType() == LayoutType.FLOW_MENU) {
				//	アルゴリズム「フローメニューの作成リストを取得する」を実行する
				//	Inputフローコードが指定されている場合
				if (layout1.get().getFlowMenuCd().isPresent()) {
					//	ドメインモデル「フローメニュー作成」を取得する
					Optional<CreateFlowMenu> data = this.cFlowMenuRepo.findByPk(cId
							, layout1.get().getFlowMenuCd().get().v());
					if (data.isPresent()) {
						listFlow.add(FlowMenuOutputCCG008.builder()
										.flowCode(data.get().getFlowMenuCode().v())
										.flowName(data.get().getFlowMenuName().v())
										.fileId(data.get()
													.getFlowMenuLayout()
													.map(FlowMenuLayout::getFileId)
													.orElse(""))
										.build());
					}
				} else {
					//	ドメインモデル「フローメニュー作成」を取得する
					listFlow = this.cFlowMenuRepo.findByCid(cId).stream()
							.map(item -> FlowMenuOutputCCG008.builder()
											.flowCode(item.getFlowMenuCode().v())
											.flowName(item.getFlowMenuName().v())
											.fileId(item.getFlowMenuLayout()
														.map(FlowMenuLayout::getFileId)
														.orElse(""))
											.build())
							.collect(Collectors.toList());
				}
			} else if (layout1.get().getLayoutType() == LayoutType.FLOW_MENU_UPLOAD) {
				//	フローメニュー（アップロード）の場合
				//	アルゴリズム「フローメニュー（アップロード）リストを取得する」を実行する
				//	Inputフローコードが指定されている場合
				if (layout1.get().getFlowMenuCd().isPresent()) {
					//	ドメインモデル「フローメニュー」を取得する
					Optional<FlowMenu> data = this.flowMenuRepository.findByCodeAndType(cId
							, layout1.get().getFlowMenuCd().get().v()
							, TopPagePartType.FlowMenu.value);
					if (data.isPresent()) {
						listFlow.add(FlowMenuOutputCCG008.builder()
								.flowCode(data.get().getCode().v())
								.flowName(data.get().getName().v())
								.fileId(data.get().getFileID())
								.build());
					}
				} else {
					//	ドメインモデル「フローメニュー」を取得する
					listFlow = this.flowMenuRepository.findByType(cId, TopPagePartType.FlowMenu.value).stream()
							.map(item -> FlowMenuOutputCCG008.builder()
								.flowCode(item.getCode().v())
								.flowName(item.getName().v())
								.fileId(item.getFileID())
								.build())
							.collect(Collectors.toList());
				}
			} else {
				result.setUrlLayout1(layout1.get().getUrl().orElse(""));
			}
			result.setLayout1(listFlow);
			// アルゴリズム「レイアウトにウィジェットを表示する」を実行する
			if(layout2.isPresent() && !layout2.get().getWidgetSettings().isEmpty()) {
				List<WidgetSettingDto> lstWidgetLayout2 = layout2.get().getWidgetSettings().stream().map(x ->{
					return WidgetSettingDto.builder()
							.widgetType(x.getWidgetType().value)
							.order(x.getOrder())
							.build();
				}).collect(Collectors.toList());
				result.setLayout2(lstWidgetLayout2);
			} else {
				result.setLayout2(new ArrayList<WidgetSettingDto>());
			}

			// アルゴリズム「レイアウトにウィジェットを表示する」を実行する
			if(layout3.isPresent() && !layout3.get().getWidgetSettings().isEmpty()) {
				List<WidgetSettingDto> lstWidgetLayout3 = layout3.get().getWidgetSettings().stream().map(x ->{
					return WidgetSettingDto.builder()
							.widgetType(x.getWidgetType().value)
							.order(x.getOrder())
							.build();
				}).collect(Collectors.toList());
				result.setLayout3(lstWidgetLayout3);
			} else {
				result.setLayout3(new ArrayList<WidgetSettingDto>());
			}
		}
		return result;
	}
	
	/**
	 * @param fromScreen 遷移元画面
	 * @param topPageSetting: トップページコード
	 * @return
	 */
	private Optional<String> getTopPageDisplay(String transitionSourceScreen
											 , Optional<TopPageSettingNewDto> topPageSetting) {
		// 設定がある場合
		if (topPageSetting.isPresent()) {
			return transitionSourceScreen.equals(IS_LOGIN)
					? Optional.ofNullable(topPageSetting.get().getLoginMenuCode())
					: Optional.ofNullable(topPageSetting.get().getTopMenuCode());
		}
		
		return Optional.empty();
	}
}
