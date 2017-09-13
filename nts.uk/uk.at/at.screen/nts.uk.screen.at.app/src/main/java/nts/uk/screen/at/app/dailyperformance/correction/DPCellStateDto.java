/**
 * 2:32:21 PM Sep 5, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hungnm
 *
 */
@Getter
@Setter
public class DPCellStateDto {

	private Integer rowId;
	
    private String columnKey;
    
    //0: Alarm, 1: Error
    private List<Integer> state;
    
}
