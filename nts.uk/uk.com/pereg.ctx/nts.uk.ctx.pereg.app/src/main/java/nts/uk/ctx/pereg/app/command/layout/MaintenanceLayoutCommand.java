/**
 * 
 */
package nts.uk.ctx.pereg.app.command.layout;

import java.util.List;

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
public class MaintenanceLayoutCommand {
	
	private String id;

	private String code;

	private String name;
	
	private List<ClassificationCommand> classifications; 

	int action;
}
/*
 * action: 
 * 0. Insert: layoutCode -> new 
 * 1. Update: * layoutCode -> old 
 * 2. Copy/Clone: * 
 * 		layoutCode -> new
 * 3. Overwrite: *
 *      layoutCode -> new 				 
 * * :Find in db for old layoutCode.
 *
 */