package nts.uk.screen.at.app.ksu001.displayevery28;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.ksu001.getsendingperiod.ChangePeriodInShiftResult_New;
import nts.uk.screen.at.app.ksu001.getsendingperiod.ChangePeriodInWorkInfoResult_New;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DisplayEvery28Dto {

	public ChangePeriodInShiftResult_New changePeriodInShiftResult_New;
	
	public ChangePeriodInWorkInfoResult_New changePeriodInWorkInfoResult_New;
	
	
}
