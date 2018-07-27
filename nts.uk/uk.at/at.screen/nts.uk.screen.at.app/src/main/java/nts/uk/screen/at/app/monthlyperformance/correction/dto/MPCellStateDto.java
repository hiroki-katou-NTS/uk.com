/**
 * 2:32:21 PM Sep 5, 2017
 */
package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MPCellStateDto {

	private String rowId;
	
    private String columnKey;
    
    private List<String> state;
    
    public void addState(String state) {
    	this.state.add(state);
    }
    
}
