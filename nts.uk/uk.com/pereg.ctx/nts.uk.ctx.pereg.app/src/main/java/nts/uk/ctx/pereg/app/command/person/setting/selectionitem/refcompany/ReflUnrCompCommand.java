package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.refcompany;

import lombok.Value;

/**
 * 
 * @author tuannv
 *
 */
@Value
public class ReflUnrCompCommand {
	private String selectionId;
	private String histId;
	private String selectionCD;
	private String selectionName;
	private String externalCD;
	private String memoSelection;
	private int disporder;
	private int initSelection;
	private String selectionItemId;
}
