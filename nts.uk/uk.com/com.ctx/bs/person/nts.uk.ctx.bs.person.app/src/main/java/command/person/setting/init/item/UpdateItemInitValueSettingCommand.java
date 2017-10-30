package command.person.setting.init.item;

import lombok.Value;

/**
 * The class UpdateInitValueSettingCommand
 * 
 * @author lanlt
 *
 */
@Value
public class UpdateItemInitValueSettingCommand {
	
	// thằng con itemId của ctgId
	private String perInfoItemDefId;
	
	// chỉ số để xem thuộc 1 trong 8 kiểu
	// nosetting or save as login employee
	private int refMethodType;
	
	private int saveDataType;
	
	private int itemType;
	
	// dataType của item
	private int dataType;
	
	// những trường này có thể bằng null
	private String timepoint;
	private String dateVal;
	
	private String stringValue;
	
	private int intValue;
	
	private String selectedCode;
	
	private int selectedRuleCode;
	
	

}
