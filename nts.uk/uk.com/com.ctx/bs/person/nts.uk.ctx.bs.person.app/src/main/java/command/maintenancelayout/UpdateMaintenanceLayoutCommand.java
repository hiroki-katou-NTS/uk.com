/**
 * 
 */
package command.maintenancelayout;

import lombok.Getter;
import lombok.Setter;

/**
 * Object command to update or add Maintenance Layout
 * 
 * @author laitv
 *
 */
@Getter
@Setter
public class UpdateMaintenanceLayoutCommand {

	String companyId;
	String layoutCode_sou;
	String layoutCode_des;
	String layoutName;
	String maintenanceLayoutID;
	boolean checked;
}
