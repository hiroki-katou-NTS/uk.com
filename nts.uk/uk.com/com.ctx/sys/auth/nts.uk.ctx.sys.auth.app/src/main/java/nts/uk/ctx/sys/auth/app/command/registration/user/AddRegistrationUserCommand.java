package nts.uk.ctx.sys.auth.app.command.registration.user;

import lombok.Data;

@Data
public class AddRegistrationUserCommand {

	// 繝ｭ繧ｰ繧､繝ｳID
	/** The login id. */
	private String loginID;
	// 螂醍ｴ�繧ｳ繝ｼ繝�
	/** The contract code. */
	private String contractCode;
	// 繝代せ繝ｯ繝ｼ繝�
	/** The password. */
	private String password;
	// 譛牙柑譛滄剞
	/** The expiration date. */
	private String expirationDate;
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
