package nts.uk.ctx.bs.employee.app.find.employee.item;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.employee.category.CategoryItemFixDto;

public class ItemCurrentAddressDto extends CategoryItemFixDto {

	/**
	 * domain : 現住所
	 */
	/** 現住所ID */
	private String currentAddressId;
	/** 個人ID */
	private String pid;
	/** 国 */
	private String countryId;
	/** 郵便番号 */
	private String postalCode;
	/** 電話番号 */
	private String phoneNumber;
	/** 都道府県 */
	private String prefectures;
	/** 社宅家賃 */
	private String houseRent;

	private GeneralDate startDate;

	private GeneralDate endDate;
	/** 住所１ */
	private String address1;

	private String addressKana1;
	/** 住所2 */
	private String address2;
	/** 住宅状況種別 */
	private String addressKana2;

	private String homeSituationType;
	/** メールアドレス */
	private String personMailAddress;
	/** 住居区分 */
	private String houseType;
	/** 最寄り駅 */
	private String nearestStation;

	public ItemCurrentAddressDto(String currentAddressId, String pid, String countryId, String postalCode,
			String phoneNumber, String prefectures, String houseRent, GeneralDate startDate, GeneralDate endDate,
			String address1, String addressKana1, String address2, String addressKana2, String homeSituationType,
			String personMailAddress, String houseType, String nearestStation) {
		super();
		this.currentAddressId = currentAddressId;
		this.pid = pid;
		this.countryId = countryId;
		this.postalCode = postalCode;
		this.phoneNumber = phoneNumber;
		this.prefectures = prefectures;
		this.houseRent = houseRent;
		this.startDate = startDate;
		this.endDate = endDate;
		this.address1 = address1;
		this.address2 = address2;
		this.addressKana1 = addressKana1;
		this.addressKana2 = addressKana2;
		this.homeSituationType = homeSituationType;
		this.personMailAddress = personMailAddress;
		this.houseType = houseType;
		this.nearestStation = nearestStation;
	}

	public static ItemCurrentAddressDto createFromJavaType(String currentAddressId, String pid, String countryId,
			String postalCode, String phoneNumber, String prefectures, String houseRent, GeneralDate startDate,
			GeneralDate endDate, String address1, String addressKana1,  String address2, String addressKana2, String homeSituationType, String personMailAddress,
			String houseType, String nearestStation) {
		return new ItemCurrentAddressDto(currentAddressId, pid, countryId, postalCode, phoneNumber, prefectures,
				houseRent, startDate, endDate, address1, addressKana1, address2, addressKana2, homeSituationType, personMailAddress, houseType,
				nearestStation);

	}

}
