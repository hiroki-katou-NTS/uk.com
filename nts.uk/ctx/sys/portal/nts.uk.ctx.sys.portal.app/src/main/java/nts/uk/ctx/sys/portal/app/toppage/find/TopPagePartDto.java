package nts.uk.ctx.sys.portal.app.toppage.find;

import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;

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
	
	public static TopPagePartDto fromDomain(TopPagePart topPagePart)
	{
		TopPagePartDto topPagePartDto = new TopPagePartDto();
		topPagePartDto.topPagePartType = topPagePart.getTopPagePartType().value;
		topPagePartDto.topPagePartCode = topPagePart.getTopPagePartCode().v();
		topPagePartDto.topPagePartName = topPagePart.getTopPagePartName().v();
		topPagePartDto.width = topPagePart.getSize().getWidth().v();
		topPagePartDto.height = topPagePart.getSize().getHeight().v();
		return  topPagePartDto;
	}
}
