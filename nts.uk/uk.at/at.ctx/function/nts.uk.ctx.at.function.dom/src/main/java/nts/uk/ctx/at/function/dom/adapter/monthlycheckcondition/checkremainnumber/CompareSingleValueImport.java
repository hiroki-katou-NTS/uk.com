package nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.checkremainnumber;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompareSingleValueImport {
	private int compareOpertor;
	
	private CheckConValueRemainNumberImport value;

	public CompareSingleValueImport(int compareOpertor, CheckConValueRemainNumberImport value) {
		super();
		this.compareOpertor = compareOpertor;
		this.value = value;
	}
	
	
}
