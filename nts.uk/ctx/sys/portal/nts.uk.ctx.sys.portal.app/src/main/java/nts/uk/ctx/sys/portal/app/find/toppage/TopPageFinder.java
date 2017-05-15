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

import nts.uk.ctx.sys.portal.dom.layout.PGType;
import nts.uk.ctx.sys.portal.dom.placement.Placement;
import nts.uk.ctx.sys.portal.dom.placement.PlacementRepository;
import nts.uk.ctx.sys.portal.dom.toppage.TopPage;
import nts.uk.ctx.sys.portal.dom.toppage.TopPageRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartRepository;

/**
 * The Class TopPageFinder.
 */
@Stateless
public class TopPageFinder {
	@Inject
	private TopPageRepository topPageRepository;

	@Inject
	private PlacementRepository placementRepository;

	@Inject
	private TopPagePartRepository topPagePartRepository;

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
		//TODO cho anh Lam sua
//		List<Placement> lstPlacement = placementRepository.findByLayout(topPage.get().getLayoutId());
		List<Placement> lstPlacement = new ArrayList<>();
		
		List<TopPagePart> lstTopPagePart = topPagePartRepository.findByType(companyId,PGType.TopPage.value);
		// convert toppage domain to dto
		if (topPage.isPresent()) {
			TopPage tp = topPage.get();
			return TopPageDto.fromDomain(tp, lstPlacement, lstTopPagePart);
		}
		return null;
	}
}
