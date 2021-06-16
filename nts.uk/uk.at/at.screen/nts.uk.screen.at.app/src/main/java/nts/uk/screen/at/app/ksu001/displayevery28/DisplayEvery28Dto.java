package nts.uk.screen.at.app.ksu001.displayevery28;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.ksu001.getsendingperiod.ChangePeriodInShiftResult;
import nts.uk.screen.at.app.ksu001.getsendingperiod.ChangePeriodInWorkInfoResult;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DisplayEvery28Dto {

	public ChangePeriodInShiftResult changePeriodInShiftResult_New;
	
	public ChangePeriodInWorkInfoResult changePeriodInWorkInfoResult_New;
	
	
}
