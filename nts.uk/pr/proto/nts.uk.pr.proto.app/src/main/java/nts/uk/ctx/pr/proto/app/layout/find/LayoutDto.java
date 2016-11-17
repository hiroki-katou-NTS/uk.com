package nts.uk.ctx.pr.proto.app.layout.find;

import lombok.Value;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;

/** Finder DTO of layout */
@Value
public class LayoutDto {

	/**
	 * Company Code
	 */
	String companyCode;

	/**
	 * Layout Code
	 */
	String stmtCode;

	/**
	 * Start Year Month
	 */
	int strYm;

	public static LayoutDto fromDomain(LayoutMaster domain) {
		return new LayoutDto(
				domain.getCompanyCode().v(), 
				domain.getStmtCode().v(), 
				domain.getStartYM().v());
	}

}
