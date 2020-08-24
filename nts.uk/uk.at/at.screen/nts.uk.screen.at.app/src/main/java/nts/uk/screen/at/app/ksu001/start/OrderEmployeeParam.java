/**
 * 
 */
package nts.uk.screen.at.app.ksu001.start;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author laitv
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderEmployeeParam {
	
	public String endDate; 
	public List<String> sids;
}
