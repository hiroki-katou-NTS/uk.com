package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.app.find.toppage.TopPageDto;
import nts.uk.ctx.sys.portal.app.find.toppage.TopPageFinder;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.flowmenu.CreateFlowMenu;
import nts.uk.ctx.sys.portal.dom.flowmenu.CreateFlowMenuRepository;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenu;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenuRepository;
import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.layout.LayoutNew;
import nts.uk.ctx.sys.portal.dom.layout.LayoutNewRepository;
import nts.uk.ctx.sys.portal.dom.layout.LayoutType;
import nts.uk.ctx.sys.portal.dom.layout.PGType;
import nts.uk.ctx.sys.portal.dom.layout.WidgetSetting;
import nts.uk.ctx.sys.portal.dom.placement.Placement;
import nts.uk.ctx.sys.portal.dom.placement.PlacementRepository;
import nts.uk.ctx.sys.portal.dom.toppage.LayoutDisplayType;
import nts.uk.ctx.sys.portal.dom.toppage.ToppageNew;
import nts.uk.ctx.sys.portal.dom.toppage.ToppageNewRepository;
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
	private CreateFlowMenuRepository CFlowMenuRepo;
	@Inject
	private FlowMenuRepository flowMenuRepo;

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
	 * @param param
	 * @return
	 */
	public DataTopPage startTopPage(StartTopPageParam param) {
		DataTopPage result = DataTopPage.builder().build();
		Optional<Integer> loginMenuCode = Optional.empty();
		Optional<String> topMenuCode = Optional.empty();
		//	指定がある場合
		if (param.getTopPageCode() != "") {
			DisplayInTopPage dataDisplay = this.displayTopPage(param.getTopPageCode());
			result.setDisplayTopPage(dataDisplay);
		} else {
			if (param.getTopPageSetting().isPresent()) {
				if (param.getFromScreen() == "login") {
					loginMenuCode = Optional.of(param.getTopPageSetting().get().getLoginMenuCode());
				} else {
					topMenuCode = Optional.of(param.getTopPageSetting().get().getTopMenuCode());
				}
			}
		}
		return null;
	}
	
	/**
	 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG015_トップページの作成.F：プレビュー.アルゴリズム.トップページを表示する.トップページを表示する
	 * @param topPageCode
	 */
	public DisplayInTopPage displayTopPage(String topPageCode) {
		String cId = AppContexts.user().companyId(); 
		//	アルゴリズム「トップページを取得する」を実行する
		//	ドメインモデル「トップページ」を取得する
		Optional<ToppageNew> topPage = topPageRepo.getByCidAndCode(cId, topPageCode); // return topPage.get().getLayoutDisp()
		DisplayInTopPage result = DisplayInTopPage.builder()
				.build();
		Optional<LayoutNew> layout1 = Optional.empty();
		Optional<LayoutNew> layout2 = Optional.empty();
		Optional<LayoutNew> layout3 = Optional.empty();
		String url = "";
		//	アルゴリズム「トップページを取得する」を実行する
		if(topPage.isPresent()) {
			result.setLayoutDisplayType(topPage.get().getLayoutDisp().value);
			//	レイアウトの表示種類＝「中１」
			if(topPage.get().getLayoutDisp() == LayoutDisplayType.MIDDLE_ONE) {
				layout1 = layoutRepo.getByCidAndCode(cId, topPageCode, BigDecimal.valueOf(0));
			}else if(topPage.get().getLayoutDisp() == LayoutDisplayType.LEFT_1_RIGHT_2 || topPage.get().getLayoutDisp() == LayoutDisplayType.LEFT_2_RIGHT_1) {
				//	レイアウトの表示種類＝「左１右２」、「左２右１」
				layout1 = layoutRepo.getByCidAndCode(cId, topPageCode, BigDecimal.valueOf(0));
				layout2 = layoutRepo.getByCidAndCode(cId, topPageCode, BigDecimal.valueOf(1));
			}else {
				//	レイアウトの表示種類＝「左２中１右３」
				layout1 = layoutRepo.getByCidAndCode(cId, topPageCode, BigDecimal.valueOf(0));
				layout2 = layoutRepo.getByCidAndCode(cId, topPageCode, BigDecimal.valueOf(1));
				layout3 = layoutRepo.getByCidAndCode(cId, topPageCode, BigDecimal.valueOf(2));
			}
		}
		//	存在する場合
		if (layout1.isPresent()) {
			List<FlowMenuOutputCCG008>  listFlow = new ArrayList<FlowMenuOutputCCG008>(); 	//return listFlow (order by flowCode)
			//	フローメニューの場合
			if (layout1.get().getLayoutType() == LayoutType.FLOW_MENU) {
				//	アルゴリズム「フローメニューの作成リストを取得する」を実行する
				//	Inputフローコードが指定されている場合
				if (layout1.get().getFlowMenuCd().isPresent()) {
					//	ドメインモデル「フローメニュー作成」を取得する
					Optional<CreateFlowMenu> data =  CFlowMenuRepo.findByPk(cId, layout1.get().getFlowMenuCd().get().v());
					if(data.isPresent()) {
						FlowMenuOutputCCG008 item = FlowMenuOutputCCG008.builder()
								.flowCode(data.get().getFlowMenuCode().v())
								.flowName(data.get().getFlowMenuName().v())
								.fileId(data.get().getFlowMenuLayout().map(x-> x.getFileId()).orElse(""))
								.build();
						listFlow.add(item);
					}
				} else {
					//	ドメインモデル「フローメニュー作成」を取得する
					List<CreateFlowMenu> listData = CFlowMenuRepo.findByCid(cId);
					listFlow = listData.stream().map(item -> FlowMenuOutputCCG008.builder()
							.flowCode(item.getFlowMenuCode().v())
							.flowName(item.getFlowMenuName().v())
							.fileId(item.getFlowMenuLayout().map(x-> x.getFileId()).orElse(""))
							.build())
							.collect(Collectors.toList());
				}
			} else if (layout1.get().getLayoutType() == LayoutType.FLOW_MENU_UPLOAD) {
				//	フローメニュー（アップロード）の場合
				//	アルゴリズム「フローメニュー（アップロード）リストを取得する」を実行する
				//	Inputフローコードが指定されている場合
				if (layout1.get().getFlowMenuCd().isPresent()) {
					//	ドメインモデル「フローメニュー」を取得する
					Optional<FlowMenu> data =  flowMenuRepo.findByCodeAndType(cId, layout1.get().getFlowMenuCd().get().v(), TopPagePartType.FlowMenu.value);
					if (data.isPresent()) {
						FlowMenuOutputCCG008 item = FlowMenuOutputCCG008.builder()
								.flowCode(data.get().getCode().v())
								.flowName(data.get().getName().v())
								.fileId(data.get().getFileID())
								.build();
						listFlow.add(item);
					}
				} else {
					//	ドメインモデル「フローメニュー」を取得する
					List<FlowMenu> listData = flowMenuRepo.findByType(cId, TopPagePartType.FlowMenu.value);
					listFlow = listData.stream().map(item -> FlowMenuOutputCCG008.builder()
							.flowCode(item.getCode().v())
							.flowName(item.getName().v())
							.fileId(item.getFileID())
							.build())
							.collect(Collectors.toList());
				}
			} else {
				url = layout1.get().getUrl().get();	
				result.setUrlLayout1(url);
			}
			result.setLayout1(listFlow);
			if (layout2.isPresent()) {
				//	アルゴリズム「レイアウトにウィジェットを表示する」を実行する
				List<WidgetSetting> listWidget2 = layout2.get().getWidgetSettings(); 
				result.setLayout2(listWidget2);
			}
			if (layout3.isPresent()) {
				//	アルゴリズム「レイアウトにウィジェットを表示する」を実行する
				List<WidgetSetting> listWidget3 = layout3.get().getWidgetSettings();
				result.setLayout3(listWidget3);
				
			}
		}
		return result;
	}
}
