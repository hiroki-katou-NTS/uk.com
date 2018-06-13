package nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.checkremainnumber;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompareRangeImport {
	private int compareOperator;
	
	private CheckConValueRemainNumberImport startValue;
	
	private CheckConValueRemainNumberImport endValue;

	public CompareRangeImport(int compareOperator, CheckConValueRemainNumberImport startValue, CheckConValueRemainNumberImport endValue) {
		super();
		this.compareOperator = compareOperator;
		this.startValue = startValue;
		this.endValue = endValue;
	}
	
	
	
}
