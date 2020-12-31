package nts.uk.ctx.bs.person.pub.contact;


import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonContactObject {

	// 個人ID
	private String personId;

	// 携帯電話番号
	private String cellPhoneNumber;
	
	// 携帯電話番号が在席照会に表示するか
	private Optional<Boolean> isPhoneNumberDisplay;

	// メールアドレス
	private String mailAdress;
	
	// メールアドレスが在席照会に表示するか
	private Optional<Boolean> isMailAddressDisplay;

	// 携帯メールアドレス
	private String mobileMailAdress;
	
	// 携帯メールアドレス
	private Optional<Boolean> isMobileEmailAddressDisplay;

	// 緊急連絡先１
	private String memo1;
	
	// 緊急連絡先１が在席照会に表示するか
	private Optional<Boolean> isEmergencyContact1Display;
	
	private String contactName1;
	
	private String phoneNumber1;

	// 緊急連絡先2
	private String memo2;
	
	// 緊急連絡先２が在席照会に表示するか
	private Optional<Boolean> isEmergencyContact2Display;
	
	private String contactName2;
	
	private String phoneNumber2;
	
	// 他の連絡先
	private List<OtherContact> otherContacts;

}
