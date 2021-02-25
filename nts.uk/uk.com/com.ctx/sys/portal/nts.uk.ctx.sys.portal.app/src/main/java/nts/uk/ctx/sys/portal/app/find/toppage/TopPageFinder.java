/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.app.find.toppage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenuRepository;
import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.layout.LayoutRepository;
import nts.uk.ctx.sys.portal.dom.layout.LayoutType;
import nts.uk.ctx.sys.portal.dom.toppage.Toppage;
import nts.uk.ctx.sys.portal.dom.toppage.ToppageRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.CreateFlowMenuRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class TopPageFinder.
 */
@Stateless
public class TopPageFinder {
	@Inject
	private ToppageRepository toppageNewRepository;
	@Inject
	private FlowMenuRepository flowMenuRepository;
	@Inject
	private CreateFlowMenuRepository CFlowMenuRepo;
	@Inject
	private LayoutRepository layoutNewRepository;

	public List<TopPageItemDto> findAll(String companyId) {
		// 会社の「トップページ」を全て取得する
		List<Toppage> listTopPage = toppageNewRepository.getByCid(companyId);
		// convert from domain to dto
		List<TopPageItemDto> lstTopPageItemDto = listTopPage.stream()
				.map(item -> new TopPageItemDto(item.getTopPageCode().v(), item.getTopPageName().v()))
				.collect(Collectors.toList());
		return lstTopPageItemDto;
	}

	public TopPageNewDto findByCode(String companyId, String topPageCode) {
		Optional<Toppage> topPage = toppageNewRepository.getByCidAndCode(companyId, topPageCode);
		if (topPage.isPresent()) {
			Toppage tp = topPage.get();
			return TopPageNewDto.fromDomain(tp);
		}
		return null;
	}

	public LayoutNewDto getLayout(String topPageCd, int layoutNo) {
		String companyId = AppContexts.user().companyId();
		// ドメインモデル「レイアウト」を取得する
		Optional<Layout> layout1 = layoutNewRepository.getByCidAndCode(companyId, topPageCd,
				BigDecimal.valueOf(layoutNo));
		if (layout1.isPresent()) {
			LayoutNewDto layoutDto = toDto(layout1.get());
			return layoutDto;
		}
		return null;
	}

	public List<FlowMenuOutput> getFlowMenuOrFlowMenuUploadList(String cId, String topPageCd, int layoutType) {
		List<FlowMenuOutput> listFlow = new ArrayList<FlowMenuOutput>();
		// アルゴリズム「フローメニューの作成リストを取得する」を実行する
		if (layoutType == LayoutType.FLOW_MENU.value) {
			// アルゴリズム「フローメニューの作成リストを取得する」を実行する
			// Inputフローコードが指定されている場合
			listFlow = CFlowMenuRepo.findByCid(cId).stream()
					.map(item -> FlowMenuOutput.builder()
							.flowCode(item.getFlowMenuCode().v())
							.flowName(item.getFlowMenuName().v())
							.fileId(item.getFlowMenuLayout().map(x -> x.getFileId()).orElse(""))
							.build())
					.collect(Collectors.toList());
			
		} else if (layoutType == LayoutType.FLOW_MENU_UPLOAD.value) {
			// アルゴリズム「フローメニュー（アップロード）リストを取得する」を実行する
			// Inputフローコードが指定されている場合
				listFlow = this.flowMenuRepository.findByType(cId, TopPagePartType.FlowMenu.value).stream()
						.map(item -> FlowMenuOutput.builder()
								.flowCode(item.getCode().v())
								.flowName(item.getName().v())
								.fileId(item.getFileID())
								.build())
						.collect(Collectors.toList());
	
		}
		return listFlow;
	}

	private LayoutNewDto toDto(Layout domain) {
		LayoutNewDto dto = new LayoutNewDto();
		domain.setMemento(dto);
		return dto;
	}
}
