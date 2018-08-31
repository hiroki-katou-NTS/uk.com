package nts.uk.ctx.at.function.dom.alarm.alarmlist.monthly;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompareOperatorText {
	private String compareLeft;
	private String compareright;
	public CompareOperatorText(String compareLeft, String compareright) {
		super();
		this.compareLeft = compareLeft;
		this.compareright = compareright;
	}
	
}
