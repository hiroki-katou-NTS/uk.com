package nts.uk.ctx.sys.auth.app.command.registration.user;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class UpdateRegistrationUserCommand {
	
	// 繝ｭ繧ｰ繧､繝ｳID
	/** The login id. */
	private String loginID;
	private String userID;
	// 有効期間
	private GeneralDate validityPeriod;
	/** The password. */
	private String password;
	// 迚ｹ蛻･蛻ｩ逕ｨ閠�
	/** The special user. */
	private boolean specialUser;
	// 隍�謨ｰ莨夂､ｾ繧貞�ｼ蜍吶☆繧�
	/** The multi company concurrent. */
	private boolean multiCompanyConcurrent;
	// 繝ｦ繝ｼ繧ｶ蜷�
	/** The user name. */
	private String userName;
	// 繝｡繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ
	/** The mail address. */
	private String mailAddress;
	// 邏蝉ｻ倥￠蜈亥�倶ｺｺID
	/** The associated employee id. */
	private String associatedPersonID;
}
