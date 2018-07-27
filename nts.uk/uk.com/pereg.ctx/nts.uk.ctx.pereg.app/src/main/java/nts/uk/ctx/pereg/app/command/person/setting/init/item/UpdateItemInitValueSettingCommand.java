package nts.uk.ctx.pereg.app.command.person.setting.init.item;

import java.math.BigDecimal;

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
	
	private String ctgCode;
	
	private String itemName;
	
	// chỉ số để xem thuộc 1 trong 8 kiểu
	// nosetting or save as login employee
	private int refMethodType;
	
	private int saveDataType;
	
	private int itemType;
	
	private int isRequired;
	
	// dataType của item
	private int dataType;
	
//	// những trường này có thể bằng null
//	private BigDecimal timePoint;
	
	private BigDecimal time;
	
	// trường này dùng để xác đinh dateType  thuộc
	// kiểu YYY-MM-DD, YY -MM, YY
	private Integer dateType;
	
	// giá trị của date
	private String dateVal;
	
	private String stringValue;
	
	private int intValue;
	// luu tru selecttionId vao kieu IntValue trong bang InitItemValue
	private String selectionId;
	
	// trường  này lấy từ bảng item common xác định đc môt list hiển thị ở cột 3 của list item
	private String selectionItemId;
	
	private int selectedRuleCode;
	
	private BigDecimal numberValue;
	
	

}
