package nts.uk.ctx.at.shared.dom.scherec.optitem;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author ThanhNX 
 * 任意項目の他言語表示名
 */
@Getter
public class OptionalItemNameOther extends AggregateRoot {
	
	// 会社ID
	private CompanyId companyId;

	/** The optional item no. */
	// 任意項目NO
	private OptionalItemNo optionalItemNo;
	
	 //言語ID
	private String langId;

	/** The optional item name. */
	// 任意項目名称
	private OptionalItemName optionalItemName;

	public OptionalItemNameOther(CompanyId companyId, OptionalItemNo optionalItemNo, String langId,
			OptionalItemName optionalItemName) {
		super();
		this.companyId = companyId;
		this.optionalItemNo = optionalItemNo;
		this.langId = langId;
		this.optionalItemName = optionalItemName;
	}
	
	
}
