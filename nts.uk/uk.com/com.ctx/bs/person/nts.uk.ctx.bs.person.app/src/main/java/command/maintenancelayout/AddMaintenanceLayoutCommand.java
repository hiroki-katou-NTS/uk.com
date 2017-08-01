/**
 * 
 */
package command.maintenancelayout;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author laitv
 *
 */
@Getter
@Setter
public class AddMaintenanceLayoutCommand {
	
	String companyId;
	String layoutCode;
	String layoutName;
	String maintenanceLayoutID;
}
