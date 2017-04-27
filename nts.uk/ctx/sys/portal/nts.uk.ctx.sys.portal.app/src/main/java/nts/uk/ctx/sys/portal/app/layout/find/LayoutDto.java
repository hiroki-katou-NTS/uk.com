package nts.uk.ctx.sys.portal.app.layout.find;

import lombok.Value;
import nts.uk.ctx.sys.portal.dom.layout.Layout;

/**
 * @author LamDT
 */
@Value
public class LayoutDto {
	
	/** Company ID */
	String companyID;

	/** Layout GUID */
	String layoutID;

	/** Enum PG Type */
	int pgType;

	/** Create Dto from Domain */
	public static LayoutDto fromDomain(Layout layout) {
		return new LayoutDto(layout.getCompanyID(), layout.getLayoutID(), layout.getPgType().value);
	}
}