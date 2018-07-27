package nts.uk.ctx.sys.auth.app.find.registration.user;

import lombok.Value;
import nts.uk.ctx.sys.auth.dom.user.User;

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Value
public class UserDto {
	 
	// 
	/** The login ID. */
	private String loginID;

	/** The user name. */
	private String userName;

	/** The user ID. */
	private String userID;
	// 螂醍ｴ�繧ｳ繝ｼ繝�
	/** The contract code. */
	private String contractCode;
	// 譛牙柑譛滄剞
	/** The expiration date. */
	private String expirationDate;
	// 迚ｹ蛻･蛻ｩ逕ｨ閠�
	/** The special user. */
	private String specialUser;
	// 隍�謨ｰ莨夂､ｾ繧貞�ｼ蜍吶☆繧�
	/** The multi company concurrent. */
	private String multiCompanyConcurrent;
	// 繝｡繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ
	/** The mail address. */
	private String mailAddress;
	// 邏蝉ｻ倥￠蜈亥�倶ｺｺID
	/** The associated employee id. */
	private String associatedPersonID;

	 
	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the user dto
	 */
	public static UserDto fromDomain(User domain) {
		String userName = "";
		String mailAddress = "";
		String personId = "";
		if(domain.getUserName().isPresent())
			userName = domain.getUserName().get().toString();
		if(domain.getMailAddress().isPresent())
			mailAddress = domain.getMailAddress().get().toString();
		if(domain.getAssociatedPersonID().isPresent())
			personId = domain.getAssociatedPersonID().get().toString();
		return new UserDto(domain.getLoginID().toString(), userName, domain.getUserID(),
				domain.getContractCode().toString(), domain.getExpirationDate().toString(), domain.getSpecialUser().name(),
				domain.getMultiCompanyConcurrent().name(), mailAddress, personId);
	}
	
	/**
	 * Instantiates a new user dto.
	 *
	 * @param loginID the login ID
	 * @param userName the user name
	 * @param userID the user ID
	 * @param contractCode the contract code
	 * @param expirationDate the expiration date
	 * @param specialUser the special user
	 * @param multiCompanyConcurrent the multi company concurrent
	 * @param mailAddress the mail address
	 * @param associatedPersonID the associated person ID
	 */
	public UserDto(String loginID, String userName, String userID, String contractCode,
			String expirationDate, String specialUser, String multiCompanyConcurrent, String mailAddress,
			String associatedPersonID) {
		super();
		this.loginID = loginID;
		this.userName = userName;
		this.userID = userID;
		this.contractCode = contractCode;
		this.expirationDate = expirationDate;
		this.specialUser = specialUser;
		this.multiCompanyConcurrent = multiCompanyConcurrent;
		this.mailAddress = mailAddress;
		this.associatedPersonID = associatedPersonID;
	}
}
