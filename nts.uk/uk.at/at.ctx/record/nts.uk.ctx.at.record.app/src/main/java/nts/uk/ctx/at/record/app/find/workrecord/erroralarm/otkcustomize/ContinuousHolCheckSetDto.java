package nts.uk.ctx.at.record.app.find.workrecord.erroralarm.otkcustomize;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContinuousHolCheckSetDto {
	
	/** The target work type. */
	private List<String> targetWorkType;
	
	/** The ignore work type. */
	private List<String> ignoreWorkType;
	
	/** The use atr. */
	private boolean useAtr;
	
	/** The display messege. */
	private String displayMessage;
	
	/** The max continuous days. */
	private int maxContinuousDays;

}
