/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/ 
package nts.uk.ctx.sys.portal.app.find.toppage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.flowmenu.CreateFlowMenu;
import nts.uk.ctx.sys.portal.dom.flowmenu.CreateFlowMenuRepository;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenu;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenuRepository;
import nts.uk.ctx.sys.portal.dom.layout.LayoutNew;
import nts.uk.ctx.sys.portal.dom.layout.LayoutNewRepository;
import nts.uk.ctx.sys.portal.dom.layout.LayoutType;
import nts.uk.ctx.sys.portal.dom.toppage.TopPage;
import nts.uk.ctx.sys.portal.dom.toppage.TopPageRepository;
import nts.uk.ctx.sys.portal.dom.toppage.ToppageNewRepository;

/**
 * The Class TopPageFinder.
 */
@Stateless
public class TopPageFinder {
	@Inject
	private TopPageRepository topPageRepository;
	@Inject 
	private FlowMenuRepository flowMenuRepository;
    @Inject
    private CreateFlowMenuRepository CFlowMenuRepo;

	public List<TopPageItemDto> findAll(String companyId) {
		List<TopPage> listTopPage = topPageRepository.findAll(companyId);
		// convert from domain to dto
		List<TopPageItemDto> lstTopPageItemDto = listTopPage.stream()
				.map(item -> new TopPageItemDto(item.getTopPageCode().v(), item.getTopPageName().v()))
				.collect(Collectors.toList());
		return lstTopPageItemDto;
	}

	public TopPageDto findByCode(String companyId, String topPageCode, String languageType) {
		Optional<TopPage> topPage = topPageRepository.findByCode(companyId, topPageCode);
		if (topPage.isPresent()) {
			TopPage tp = topPage.get();
			return TopPageDto.fromDomain(tp);
		}
		return null;
	}
	
	public List<FlowMenuOutput> getFlowMenuOrFlowMenuUploadList(String cId, LayoutNew layout1) {
		List<FlowMenuOutput> listFlow = new ArrayList<FlowMenuOutput>();
		//	アルゴリズム「フローメニューの作成リストを取得する」を実行する
		if(layout1.getLayoutType() == LayoutType.FLOW_MENU) {
            //    アルゴリズム「フローメニューの作成リストを取得する」を実行する
            //    Inputフローコードが指定されている場合
            if(layout1.getFlowMenuCd().isPresent()) {
                Optional<CreateFlowMenu> data =  CFlowMenuRepo.findByPk(cId, layout1.getFlowMenuCd().get().v());
                if(data.isPresent()) {
                	FlowMenuOutput item = FlowMenuOutput.builder()
                            .flowCode(data.get().getFlowMenuCode().v())
                            .flowName(data.get().getFlowMenuName().v())
                            .fileId(data.get().getFlowMenuLayout().map(x-> x.getFileId()).orElse(""))
                            .build();
                    listFlow.add(item);
                }
            } else {
	            List<CreateFlowMenu> listData = CFlowMenuRepo.findByCid(cId);
	            listFlow = listData.stream().map(item -> FlowMenuOutput.builder()
	                    .flowCode(item.getFlowMenuCode().v())
	                    .flowName(item.getFlowMenuName().v())
	                    .fileId(item.getFlowMenuLayout().map(x-> x.getFileId()).orElse(""))
	                    .build())
	                    .collect(Collectors.toList());
	        }
		}
		//	アルゴリズム「フローメニュー（アップロード）リストを取得する」を実行する
		else if (layout1.getLayoutType() == LayoutType.FLOW_MENU_UPLOAD) {
			// Inputフローコードが指定されている場合
			if (layout1.getFlowMenuCd().isPresent()) {
				Optional<FlowMenu> data = flowMenuRepository.findByCodeAndType(cId, layout1.getFlowMenuCd().get().v(), TopPagePartType.FlowMenu.value);
				if(data.isPresent()) {
                	FlowMenuOutput item = FlowMenuOutput.builder()
                            .flowCode(data.get().getCode().v())
                            .flowName(data.get().getName().v())
                            .fileId(data.get().getFileID())
                            .build();
                    listFlow.add(item);
                }
			} else {
				List<FlowMenu> lstData = flowMenuRepository.findByType(cId, TopPagePartType.FlowMenu.value);
				listFlow = lstData.stream().map(item -> FlowMenuOutput.builder()
	                    .flowCode(item.getCode().v())
	                    .flowName(item.getName().v())
	                    .fileId(item.getFileID())
	                    .build())
	                    .collect(Collectors.toList());
			}
		}
		return listFlow;
	}
	
}
