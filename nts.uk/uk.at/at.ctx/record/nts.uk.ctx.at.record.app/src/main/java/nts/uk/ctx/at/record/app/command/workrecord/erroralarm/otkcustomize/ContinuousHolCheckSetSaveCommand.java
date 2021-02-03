package nts.uk.ctx.at.record.app.command.workrecord.erroralarm.otkcustomize;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContinuousHolCheckSetSaveCommand {
	
	/** The target work type. */
	private List<String> targetWorkType;
	
	/** The ignore work type. */
	private List<String> ignoreWorkType;
	
	/** The use atr. */
	private boolean useAtr;
	
	/** The display messege. */
	private DisplayMessage displayMessage;
	
	/** The max continuous days. */
	private int maxContinuousDays;
	
	/** The is update. */
	private boolean updateMode;

}
