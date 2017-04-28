package nts.uk.ctx.sys.portal.app.toppage.find;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.placement.Placement;
import nts.uk.ctx.sys.portal.dom.placement.PlacementRepository;
import nts.uk.ctx.sys.portal.dom.toppage.TopPage;
import nts.uk.ctx.sys.portal.dom.toppage.TopPageRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;

/**
 * The Class TopPageFinder.
 */
@Stateless
public class TopPageFinder {
	@Inject
	TopPageRepository topPageRepository;

	@Inject
	PlacementRepository PlacementRepository;

	// @Inject
	// TopPagePartRepository topPagePartRepository;

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
		List<Placement> lstPlacement = PlacementRepository.findByLayout(topPage.get().getLayoutId());
		// TODO use topPagePartRepository find
		List<TopPagePart> lstTopPagePart = new ArrayList<>();
		lstTopPagePart.add(TopPagePart.createFromJavaType("1", "topPagePartCode1", "topPagePartName", 1, 1, 1));
		lstTopPagePart.add(TopPagePart.createFromJavaType("1", "topPagePartCode2", "topPagePartName", 1, 1, 1));
		lstTopPagePart.add(TopPagePart.createFromJavaType("1", "topPagePartCode3", "topPagePartName", 1, 1, 1));
		lstTopPagePart.add(TopPagePart.createFromJavaType("1", "topPagePartCode4", "topPagePartName", 1, 1, 1));
		lstTopPagePart.add(TopPagePart.createFromJavaType("1", "topPagePartCode5", "topPagePartName", 1, 1, 1));
		lstTopPagePart.add(TopPagePart.createFromJavaType("1", "topPagePartCode6", "topPagePartName", 1, 1, 1));
		// convert toppage domain to dto
		if (topPage.isPresent()) {
			TopPage tp = topPage.get();
			return TopPageDto.fromDomain(tp, lstPlacement, lstTopPagePart);
		}
		return null;
	}
}
