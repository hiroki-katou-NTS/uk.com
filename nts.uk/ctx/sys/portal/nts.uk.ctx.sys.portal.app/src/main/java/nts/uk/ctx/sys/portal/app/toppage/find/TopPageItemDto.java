package nts.uk.ctx.sys.portal.app.toppage.find;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class TopPageItemDto.
 */
@Getter
@Setter
public class TopPageItemDto {
	
	/** The top page code. */
	public String topPageCode;

	/** The top page name. */
	public String topPageName;

	/**
	 * Instantiates a new top page item dto.
	 *
	 * @param topPageCode the top page code
	 * @param topPageName the top page name
	 */
	public TopPageItemDto(String topPageCode, String topPageName) {
		super();
		this.topPageCode = topPageCode;
		this.topPageName = topPageName;
	}
}
