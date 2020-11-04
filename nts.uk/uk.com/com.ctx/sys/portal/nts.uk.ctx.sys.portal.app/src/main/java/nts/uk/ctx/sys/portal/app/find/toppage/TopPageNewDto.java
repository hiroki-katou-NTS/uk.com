package nts.uk.ctx.sys.portal.app.find.toppage;

import lombok.Data;
import nts.uk.ctx.sys.portal.dom.toppage.ToppageNew;

/**
 * The Class TopPageNewDto.
 */
@Data
public class TopPageNewDto {
	
	/** The top page code. */
	private String topPageCode;

	/** The top page name. */
	private String topPageName;

	/** The layout display id. */
	private int layoutDisp;

	/**
	 * From domain.
	 *
	 * @param topPage the top page
	 * @param lstPlacement the lst placement
	 * @param lstTopPagePart the lst top page part
	 * @return the top page dto
	 */
	public static TopPageNewDto fromDomain(ToppageNew topPage) {
		TopPageNewDto topPageDto = new TopPageNewDto();
		topPageDto.topPageCode = topPage.getTopPageCode().v();
		topPageDto.topPageName = topPage.getTopPageName().v();
		topPageDto.layoutDisp = topPage.getLayoutDisp().value;
		return topPageDto;
	}
}
