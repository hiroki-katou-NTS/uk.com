package nts.uk.ctx.at.record.app.find.optitem.language;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OptionalItemNameOther {

	// 会社ID
	private String companyId;

	/** The optional item no. */
	// 任意項目NO
	private int optionalItemNo;

	// 言語ID
	private String langId;

	/** The optional item name. */
	// 任意項目名称
	private String optionalItemName;

}
