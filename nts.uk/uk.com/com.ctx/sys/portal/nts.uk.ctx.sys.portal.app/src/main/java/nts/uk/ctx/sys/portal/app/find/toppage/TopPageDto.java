/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.app.find.toppage;

import lombok.Data;
import nts.uk.ctx.sys.portal.dom.toppage.TopPage;

/**
 * The Class TopPageDto.
 */
@Data
public class TopPageDto {

	/** The top page code. */
	private String topPageCode;

	/** The top page name. */
	private String topPageName;

	/** The Language number. */
	private Integer languageNumber;

	/** The layout id. */
	private String layoutId;

	/**
	 * From domain.
	 *
	 * @param topPage the top page
	 * @param lstPlacement the lst placement
	 * @param lstTopPagePart the lst top page part
	 * @return the top page dto
	 */
	public static TopPageDto fromDomain(TopPage topPage) {
		TopPageDto topPageDto = new TopPageDto();
		topPageDto.topPageCode = topPage.getTopPageCode().v();
		topPageDto.topPageName = topPage.getTopPageName().v();
		topPageDto.languageNumber = topPage.getLanguageNumber();
		topPageDto.layoutId = topPage.getLayoutId();
		return topPageDto;
	}
}
