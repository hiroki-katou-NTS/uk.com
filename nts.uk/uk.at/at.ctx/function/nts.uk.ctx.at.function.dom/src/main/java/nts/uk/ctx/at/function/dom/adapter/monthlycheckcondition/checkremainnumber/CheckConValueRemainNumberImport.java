package nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.checkremainnumber;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class CheckConValueRemainNumberImport {
	
	private int daysValue;
	
	private Integer timeValue;

	public CheckConValueRemainNumberImport(int daysValue, Integer timeValue) {
		super();
		this.daysValue = daysValue;
		this.timeValue = timeValue;
	}
	
}
