package nts.uk.ctx.sys.portal.app.find.toppage;

import lombok.Data;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;

/**
 * The Class TopPagePartDto.
 */
@Data
public class TopPagePartDto {
	
	/** The top page part type. */
	Integer topPagePartType;

	/** The top page part code. */
	String topPagePartCode;

	/** The top page part name. */
	String topPagePartName;
	
	/** The width. */
	Integer width;
	
	/** The height. */
	Integer height;
	
	/**
	 * From domain.
	 *
	 * @param topPagePart the top page part
	 * @return the top page part dto
	 */
	public static TopPagePartDto fromDomain(TopPagePart topPagePart)
	{
		TopPagePartDto topPagePartDto = new TopPagePartDto();
		topPagePartDto.topPagePartType = topPagePart.getType().value;
		topPagePartDto.topPagePartCode = topPagePart.getCode().v();
		topPagePartDto.topPagePartName = topPagePart.getName().v();
		topPagePartDto.width = topPagePart.getSize().getWidth().v();
		topPagePartDto.height = topPagePart.getSize().getHeight().v();
		return  topPagePartDto;
	}
}
