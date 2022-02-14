package nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;

/**
 * 承認レベル数
 */
@AllArgsConstructor
public enum ApprovalLevelNo {
	
	/** 1つレベル */
	ONE_LEVEL(1),
																						
	/** 2つレベル */
	TWO_LEVEL(2),
	
	/** 3つレベル */
	THREE_LEVEL(3),
	
	/** 4つレベル */
	FOUR_LEVEL(4),
	
	/** 5つレベル */
	FIVE_LEVEL(5);
	
	public int value;
	
	/**
	 * [1] 表示される承認フェーズの順序を判断する	
	 */
	public List<Integer> determineOrderOfPhases() {
		List<Integer> phasesOrder = new ArrayList<Integer>();
		switch (this.value) {
			case 1:
				phasesOrder = Arrays.asList(5);
				break;
			case 2:
				phasesOrder = Arrays.asList(5, 4);
				break;
			case 3:
				phasesOrder = Arrays.asList(5, 4, 3);
				break;
			case 4:
				phasesOrder = Arrays.asList(5, 4, 3, 2);
				break;
			case 5:
				phasesOrder = Arrays.asList(5, 4, 3, 2, 1);
				break;
			default:
				break;
		}
		
		return phasesOrder;
	}
}
