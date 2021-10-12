package nts.uk.ctx.exio.app.command.exi.item;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.exio.app.command.exi.condset.StdAcceptCondSetCommand;

/**
 * 
 * @author HungTT
 *
 */

@Value
public class Cmf001DCommand {

	private StdAcceptCondSetCommand conditionSetting;

	private List<StdAcceptItemCommand> listItem;
	
	private String conditionSetCd;
	
	private int system;

}
