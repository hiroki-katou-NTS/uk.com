package command.person.setting.init;

import java.util.List;

import command.person.setting.init.item.UpdateItemInitValueSettingCommand;
import lombok.Value;
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
	
	// những item của ctg
	List<UpdateItemInitValueSettingCommand> itemLst;
	
	
	
	

	

	
	
	

}
