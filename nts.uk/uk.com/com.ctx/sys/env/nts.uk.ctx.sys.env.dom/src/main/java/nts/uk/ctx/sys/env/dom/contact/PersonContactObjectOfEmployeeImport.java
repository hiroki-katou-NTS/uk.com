package nts.uk.ctx.sys.env.dom.contact;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonContactObjectOfEmployeeImport {

	//社員ID
	private String employeeId;

	// 個人ID
	private String personId;

	// 携帯電話番号
	private String cellPhoneNumber;
	
	/**
	 * 携帯電話番号が在席照会に表示するか
	 */
	private boolean isPhoneNumberDisplay;

	// メールアドレス
	private String mailAdress;
	
	/**
	 * メールアドレスが在席照会に表示するか
	 */
	private boolean isMailAddressDisplay;

	// 携帯メールアドレス
	private String mobileMailAdress;
	
	/**
	 * 携帯メールアドレスが在席照会に表示するか
	 */
	private boolean isMobileEmailAddressDisplay;

	// 緊急連絡先１
	private String memo1;

	private String contactName1;

	private String phoneNumber1;
	
	/**
	 * 緊急連絡先１が在席照会に表示するか
	 */
	private boolean isEmergencyContact1Display;

	// 緊急連絡先2
	private String memo2;

	private String contactName2;

	private String phoneNumber2;
	
	/**
	 * 緊急連絡先２が在席照会に表示するか
	 */
	private boolean isEmergencyContact2Display;
}
