package nts.uk.ctx.sys.auth.app.find.registration.user;

import java.util.Optional;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.user.ContractCode;
import nts.uk.ctx.sys.auth.dom.user.DisabledSegment;
import nts.uk.ctx.sys.auth.dom.user.HashPassword;
import nts.uk.ctx.sys.auth.dom.user.LoginID;
import nts.uk.ctx.sys.auth.dom.user.MailAddress;
import nts.uk.ctx.sys.auth.dom.user.SearchUser;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserName;

@Value
public class UserDto {
	 
	private String loginID;

	private String userName;

	private String userID;
	// 繝代せ繝ｯ繝ｼ繝�
	/** The password. */
	private String password;
	// 螂醍ｴ�繧ｳ繝ｼ繝�
	/** The contract code. */
	private String contractCode;
	// 譛牙柑譛滄剞
	/** The expiration date. */
	private String expirationDate;
	// 迚ｹ蛻･蛻ｩ逕ｨ閠�
	/** The special user. */
	private Boolean specialUser;
	// 隍�謨ｰ莨夂､ｾ繧貞�ｼ蜍吶☆繧�
	/** The multi company concurrent. */
	private Boolean multiCompanyConcurrent;
	// 繝｡繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ
	/** The mail address. */
	private String mailAddress;
	// 邏蝉ｻ倥￠蜈亥�倶ｺｺID
	/** The associated employee id. */
	private String associatedPersonID;

	 
	public static UserDto fromDomain(User domain) {
		return new UserDto(domain.getLoginID().toString(), domain.getUserName().get().toString(), domain.getUserID(),
				domain.getPassword().toString(), domain.getContractCode().toString(), domain.getExpirationDate().toString(), Boolean.valueOf(domain.getSpecialUser().name()),
				Boolean.valueOf(domain.getMultiCompanyConcurrent().name()), domain.getMailAddress().get().toString(), domain.getAssociatedPersonID().get().toString());
	}
	public UserDto(String loginID, String userName, String userID, String password, String contractCode,
			String expirationDate, Boolean specialUser, Boolean multiCompanyConcurrent, String mailAddress,
			String associatedPersonID) {
		super();
		this.loginID = loginID;
		this.userName = userName;
		this.userID = userID;
		this.password = password;
		this.contractCode = contractCode;
		this.expirationDate = expirationDate;
		this.specialUser = specialUser;
		this.multiCompanyConcurrent = multiCompanyConcurrent;
		this.mailAddress = mailAddress;
		this.associatedPersonID = associatedPersonID;
	}
}
