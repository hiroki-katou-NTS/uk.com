package command.person.info;


import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class PersonCommand {
	
	/** The Birthday */
	// 生年月日
	@PeregItem("")
	private GeneralDate birthDate;

	/** The BloodType */
	// 血液型
	@PeregItem("")
	private int bloodType;

	/** The Gender - 性別 */
	@PeregItem("")
	private int gender;

	/** The person id - 個人ID */
	private String personId;

	/** The Person Mail Address - 個人メールアドレス */
	@PeregItem("")
	private String mailAddress;

	/** The Person Mobile - 個人携帯*/
	@PeregItem("")
	private String personMobile;


	/** The Hobby - 趣味 */
	@PeregItem("")
	private String hobby;

	/** The countryId - 国籍    chua xac dinh duoc PrimitiveValue*/
	@PeregItem("")
	private String countryId;

	/** The Taste - 嗜好 */
	@PeregItem("")
	private String taste;
	
	/** The PersonNameGroup - 個人名グループ*/
	@PeregItem("")
	private String personName;

	@PeregItem("")
	private String personNameKana;

	@PeregItem("")
	private String businessName;

	@PeregItem("")
	private String businessEnglishName;

	@PeregItem("")
	private String businessOtherName;

	@PeregItem("")
	private String personRomanji;
	
	@PeregItem("")
	private String personRomanjiKana;

	@PeregItem("")
	private String oldName;
	
	@PeregItem("")
	private String oldNameKana;

	@PeregItem("")
	private String todokedeFullName;
	
	@PeregItem("")
	private String todokedeFullNameKana;

	@PeregItem("")
	private String todokedeOldFullName;
	
	@PeregItem("")
	private String todokedeOldFullNameKana;

}
