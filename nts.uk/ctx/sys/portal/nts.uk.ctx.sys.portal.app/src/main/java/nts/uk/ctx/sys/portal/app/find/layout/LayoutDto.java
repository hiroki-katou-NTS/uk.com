package nts.uk.ctx.sys.portal.app.find.layout;

import lombok.Value;
import nts.uk.ctx.sys.portal.dom.layout.Layout;

/**
 * @author LamDT
 */
@Value
public class LayoutDto {
	
	/** Company ID */
	private String companyID;

	/** Layout GUID */
	private String layoutID;

	/** Enum PG Type */
	private int pgType;

	/** Create Dto from Domain */
	public static LayoutDto fromDomain(Layout layout) {
		return new LayoutDto(layout.getCompanyID(), layout.getLayoutID(), layout.getPgType().value);
	}
}