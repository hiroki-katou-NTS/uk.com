package nts.uk.ctx.sys.portal.app.find.toppage;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.sys.portal.dom.placement.Placement;
import nts.uk.ctx.sys.portal.dom.toppage.TopPage;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;

/**
 * The Class TopPageDto.
 */
public class TopPageDto {

	/** The top page code. */
	public String topPageCode;

	/** The top page name. */
	public String topPageName;

	/** The Language number. */
	public Integer languageNumber;
	
	/** The layout id. */
	public String layoutId;
	
	/** The placement dto. */
	public List<PlacementDto> placementDto;

	/**
	 * From domain.
	 *
	 * @param topPage the top page
	 * @param lstPlacement the lst placement
	 * @param lstTopPagePart the lst top page part
	 * @return the top page dto
	 */
	public static TopPageDto fromDomain(TopPage topPage, List<Placement> lstPlacement,
			List<TopPagePart> lstTopPagePart) {
		TopPageDto topPageDto = new TopPageDto();
		topPageDto.topPageCode = topPage.getTopPageCode().v();
		topPageDto.topPageName = topPage.getTopPageName().v();
		topPageDto.languageNumber = topPage.getLanguageNumber();
		topPageDto.layoutId = topPage.getLayoutId();
		topPageDto.placementDto = lstPlacement.stream().map(item -> {
			return PlacementDto.fromDomain(item, lstTopPagePart.stream().filter(t -> {
				return true;
//				return t.getToppagePartID().equals(item.getToppagePartID());
			}).findFirst().get());
		}).collect(Collectors.toList());
		return topPageDto;
	}
}
