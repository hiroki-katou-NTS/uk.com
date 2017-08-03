/**
 * 
 */
package command.maintenancelayout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author laitv
 *
 */

@Data
@AllArgsConstructor
public class AddMaintenanceLayoutCommand {
	
	String companyId;
	String layoutCode;
	String layoutName;
	String maintenanceLayoutID;
}
