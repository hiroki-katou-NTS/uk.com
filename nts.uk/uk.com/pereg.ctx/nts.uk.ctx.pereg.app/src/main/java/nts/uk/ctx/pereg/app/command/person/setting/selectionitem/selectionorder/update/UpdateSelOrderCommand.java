package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selectionorder.update;

import lombok.Value;

/**
 * 
 * @author tuannv
 *
 */
@Value
public class UpdateSelOrderCommand {

	private String selectionID;
	private String histId;
	private int dispOrder;
	private boolean initSelection; 
}
