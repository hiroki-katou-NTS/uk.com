package nts.uk.ctx.pereg.app.command.person.setting.init;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.pereg.app.command.person.setting.init.item.UpdateItemInitValueSettingCommand;
/**
 * The Class UpdateInitValueSettingCommand
 * @author lanlt
 *
 */
@Value
public class UpdateInitValueSettingCommand {
	
	// thằng cha setting
	private String settingId;
	private String settingName;
	
	// thằng con ctgId
	private String perInfoCtgId;
	
	private boolean isSetting;
	
	// những item của ctg
	List<UpdateItemInitValueSettingCommand> itemLst;
	
	
	
	

	

	
	
	

}
