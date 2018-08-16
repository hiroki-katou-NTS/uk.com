package nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.checkremainnumber;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompareSingleValueImport {
	private int compareOperator;
	
	private CheckConValueRemainNumberImport value;

	public CompareSingleValueImport(int compareOperator, CheckConValueRemainNumberImport value) {
		super();
		this.compareOperator = compareOperator;
		this.value = value;
	}
	
	
}
