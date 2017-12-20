package nts.uk.ctx.pereg.app.command.addemployee;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.command.ItemsByCategory;

@Data
public class AddEmployeeCommand {

	private String employeeCopyId;
	private String initSettingId;
	private String employeeName;
	private String employeeCode;
	private GeneralDate hireDate;
	private String cardNo;
	private String avatarId;
	private int createType;
	// パスワード
	/** The password. */
	private String password;

	// ログインID
	/** The login id. */
	private String loginId;

	private final List<ItemsByCategory> inputs;

}
