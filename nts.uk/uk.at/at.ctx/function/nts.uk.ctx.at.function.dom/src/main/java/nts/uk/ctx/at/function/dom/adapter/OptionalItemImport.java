package nts.uk.ctx.at.function.dom.adapter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OptionalItemImport {

	/** The optional item no. */
	// 任意項目NO
	private int optionalItemNo;

	/** The optional item name. */
	// 任意項目名称
	private String optionalItemName;

	public OptionalItemImport(int optionalItemNo, String optionalItemName) {
		super();
		this.optionalItemNo = optionalItemNo;
		this.optionalItemName = optionalItemName;
	}
	
}
