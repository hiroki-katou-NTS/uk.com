package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.portal.app.find.standardmenu.StandardMenuDto;
import nts.uk.ctx.sys.portal.app.find.toppage.TopPageNewDto;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenu;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenuRepository;
import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.layout.LayoutNO;
import nts.uk.ctx.sys.portal.dom.layout.LayoutRepository;
import nts.uk.ctx.sys.portal.dom.layout.LayoutType;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenu;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository;
import nts.uk.ctx.sys.portal.dom.toppage.Toppage;
import nts.uk.ctx.sys.portal.dom.toppage.ToppageRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.CreateFlowMenu;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.CreateFlowMenuRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.FlowMenuLayout;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class DisplayMyPageFinder {
	@Inject 
	private ToppageRepository topPageRepo;
	@Inject
	private LayoutRepository layoutRepo;
	@Inject
	private CreateFlowMenuRepository cFlowMenuRepo;
	@Inject
	private FlowMenuRepository flowMenuRepository;
	@Inject
	private StandardMenuRepository standardMenuRepo;
	
	private static final String IS_LOGIN = "login";

	
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
			if(!displayCode.isPresent()) {
				return null;
			}
			if(param.getFromScreen().equals(IS_LOGIN)) {
				if(!param.getTopPageSetting().get().getLoginMenuCode().equals("0000")) {
					//	標準メニューの場合
					if (param.getTopPageSetting().get().getMenuClassification() != MenuClassification.TopPage.value) {
						result.setMenuClassification(MenuClassification.Standard.value);
						Optional<StandardMenu> standardMenu = this.standardMenuRepo.getStandardMenubyCode(cId, displayCode.get(),
								param.getTopPageSetting().get().getSystem(), param.getTopPageSetting().get().getMenuClassification());
						if(standardMenu.isPresent()) {
							result.setStandardMenu(StandardMenuDto.fromDomain(standardMenu.get()));
						}
						
					} else {
						DisplayInTopPage dataDisplay = this.displayTopPage(displayCode.orElse(""));
						result.setDisplayTopPage(dataDisplay);
					}
					//	トップページの場合
				} else {
					DisplayInTopPage dataDisplay = this.displayTopPage(param.getTopPageSetting().get().getTopMenuCode());
					result.setDisplayTopPage(dataDisplay);
				}
				
			} else {
				DisplayInTopPage dataDisplay = this.displayTopPage(displayCode.orElse(""));
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
		Optional<Toppage> topPage = this.topPageRepo.getByCidAndCode(cId, topPageCode);
		DisplayInTopPage result = new DisplayInTopPage();
		
		if (topPage.isPresent()) {
			result.setTopPage(TopPageNewDto.fromDomain(topPage.get()));
			result.setLayoutDisplayType(topPage.get().getLayoutDisp().value);
			List<Integer> frameLayoutList = Arrays.asList(
					topPage.get().getFrameLayout1().map(LayoutNO::v).map(BigDecimal::intValue).orElse(null),
					topPage.get().getFrameLayout2().map(LayoutNO::v).map(BigDecimal::intValue).orElse(null),
					topPage.get().getFrameLayout3().map(LayoutNO::v).map(BigDecimal::intValue).orElse(null));
			// #loopNo１～３でループ（レイアウト枠１～３をチェック）
			List<List<?>> listLayout = frameLayoutList.stream()
					.map(layoutNo -> {
						// トップページ.枠レイアウト(#loopNo)の値を取得する
						// Nullの場合
						if (layoutNo == null) {
							return Collections.emptyList();
						}
						Optional<Layout> optLayout = this.layoutRepo
								.getByCidAndCode(cId, topPageCode, BigDecimal.valueOf(layoutNo));
						// 取得した「レイアウト枠#loopNo」にドメインが存在をチェックする
						if (!optLayout.isPresent()) {
							return Collections.emptyList();
						}
						// 存在している　&　トップページ.枠レイアウト(loopNo)＝0の場合
						if (layoutNo == 0) {
							// 外部URLの場合
							if (optLayout.get().getLayoutType().equals(LayoutType.EXTERNAL_URL)) {
								result.setUrlLayout1(optLayout.get().getUrl().orElse(null));
								return Collections.emptyList();
							}
							return this.getTopPageFlowMenu(layoutNo, optLayout.get());
						}
						// 存在している　&　トップページ.枠レイアウト(loopNo)≠0の場合
						return this.getTopPageWidget(layoutNo, optLayout.get());
					}).collect(Collectors.toList());
			result.setListLayout(listLayout);
		}
		return result;
	}
	
	/**
	 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG015_トップページの作成.F：プレビュー.アルゴリズム.トップページHTMLを表示する.トップページHTMLを表示する
	 */
	private List<FlowMenuOutputCCG008> getTopPageFlowMenu(int frameLayout, Layout layout) {
		String cid = AppContexts.user().companyId();
		// レイアウト.レイアウト種類
		LayoutType layoutType = layout.getLayoutType();
		switch (layoutType) {
		// フローメニューの場合
		case FLOW_MENU:
			//	アルゴリズム「フローメニューの作成リストを取得する」を実行する
			//	Inputフローコードが指定されている場合
			if (layout.getFlowMenuCd().isPresent() && !StringUtils.isEmpty(layout.getFlowMenuCd().get().v())) {
				// ドメインモデル「フローメニュー作成」を取得する
				Optional<CreateFlowMenu> data = this.cFlowMenuRepo.findByPk(cid,
						layout.getFlowMenuCd().get().v());
				if (data.isPresent()) {
					return Arrays.asList(FlowMenuOutputCCG008.builder()
							.flowCode(data.get().getFlowMenuCode().v())
							.flowName(data.get().getFlowMenuName().v())
							.fileId(data.get().getFlowMenuLayout().map(FlowMenuLayout::getFileId).orElse(""))
							.isFlowmenu(true)
							.build());
				}
			} else {
				//	ドメインモデル「フローメニュー作成」を取得する
				return this.cFlowMenuRepo.findByCid(cid).stream()
						.map(item -> FlowMenuOutputCCG008.builder()
										.flowCode(item.getFlowMenuCode().v())
										.flowName(item.getFlowMenuName().v())
										.fileId(item.getFlowMenuLayout()
													.map(FlowMenuLayout::getFileId)
													.orElse(""))
										.isFlowmenu(true)
										.build())
						.collect(Collectors.toList());
			}
			break;
		case FLOW_MENU_UPLOAD:
			//	フローメニュー（アップロード）の場合
			//	アルゴリズム「フローメニュー（アップロード）リストを取得する」を実行する
			//	Inputフローコードが指定されている場合
			if (layout.getFlowMenuUpCd().isPresent()) {
				//	ドメインモデル「フローメニュー」を取得する
				Optional<FlowMenu> data = this.flowMenuRepository.findByToppagePartCodeAndType(cid
						, layout.getFlowMenuUpCd().get().v()
						, TopPagePartType.FlowMenu.value);
				if (data.isPresent()) {
					return Arrays.asList(FlowMenuOutputCCG008.builder()
							.flowCode(data.get().getCode().v())
							.flowName(data.get().getName().v())
							.fileId(data.get().getFileID())
							.isFlowmenu(false)
							.build());
				}
			} else {
				//	ドメインモデル「フローメニュー」を取得する
				return this.flowMenuRepository.findByType(cid, TopPagePartType.FlowMenu.value).stream()
						.map(item -> FlowMenuOutputCCG008.builder()
							.flowCode(item.getCode().v())
							.flowName(item.getName().v())
							.fileId(item.getFileID())
							.isFlowmenu(false)
							.build())
						.collect(Collectors.toList());
			}
			break;
		default: 
			return Collections.emptyList();	
		}
		return Collections.emptyList();
	}
	
	/**
	 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG015_トップページの作成.F：プレビュー.アルゴリズム.トップページウィジェットを表示する.トップページウィジェットを表示する
	 */
	private List<WidgetSettingDto> getTopPageWidget(int frameLayout, Layout layout) {
		// レイアウトにウィジェットを表示する
		return layout.getWidgetSettings().stream()
				.map(data -> WidgetSettingDto.builder().widgetType(data.getWidgetType().value)
						.order(data.getOrder()).build())
				.collect(Collectors.toList());
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
