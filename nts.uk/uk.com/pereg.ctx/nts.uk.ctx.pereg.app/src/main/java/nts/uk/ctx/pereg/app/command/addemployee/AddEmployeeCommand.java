package nts.uk.ctx.pereg.app.command.addemployee;

import java.util.List;
import java.util.Optional;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.command.ItemsByCategory;

@Data
public class AddEmployeeCommand {

	private String employeeCopyId;
	private String initSettingId;
	private String employeeName;
	private String employeeCode;
	private GeneralDate hireDate;
	private String cardNo;
	private int createType;
	// パスワード
	/** The password. */
	private String password;

	// ログインID
	/** The login id. */
	private String loginId;
	
	// info Log Correction Avatar
	private String avatarOrgId;
	private String avatarCropedId;
	private String categoryName; // get from resource {categoryName：#CPS001_152（quản ly file）}
	private String itemName; // get from resource {itemName：#CPS001_150（ảnh chan dung）}
	private String fileName;
	
	private final List<ItemsByCategory> inputs;
	
	public Optional<ItemsByCategory> getCategoryData(String categoryCode) {
		return this.inputs.stream().filter(itemByCategory -> itemByCategory.getCategoryCd().equals(categoryCode)).findFirst();
	}
	
	public ItemValue getItemValue(String itemCode, String categoryCode) {
		for (ItemsByCategory category : inputs) {
			if (category.getCategoryCd().equals(categoryCode)) {
				return category.getByItemCode(itemCode);
			}
		}
		return null;

	}

}
