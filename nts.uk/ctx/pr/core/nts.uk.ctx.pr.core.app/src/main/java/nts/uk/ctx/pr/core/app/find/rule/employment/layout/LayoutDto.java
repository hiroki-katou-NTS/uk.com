package nts.uk.ctx.pr.core.app.find.rule.employment.layout;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.LayoutMaster;

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
	int startYm;
	
	/**
	 * Layout Name
	 */
	String stmtName;
	
	/**
	 * End Year Month
	 */
	int endYm;
	
	/**
	 * layout attribute
	 */
	int layoutAtr;
	
	String historyId;

	public static LayoutDto fromDomain(LayoutMaster domain) {
		return new LayoutDto(
				domain.getCompanyCode().v(), 
				domain.getStmtCode().v(), 
				domain.getStartYM().v(),
				domain.getStmtName().v(),
				domain.getEndYm().v(),
				domain.getLayoutAtr().value,
				domain.getHistoryId());
	}

}
