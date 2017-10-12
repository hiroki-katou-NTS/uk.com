package nts.uk.ctx.bs.person.dom.person.info.currentaddress;



import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * *現住所
 * @author xuan vinh
 *
 */

@Getter
public class CurrentAddress extends AggregateRoot{
	//個人ID
	private String personId;
	//現住所ID
	private String currentAddressId;
	//住所１
	private AddressSet address1;
	//start date
	private GeneralDate startDate;
	//end date
	private GeneralDate endDate;
	//住所２
	private AddressSet address2;
	//国
	private CountryId country;
	//住宅状況種別
	private HomeSituationType homeSituationType;
	//社宅家賃
	private HouseRent houseRent;
	//電話番号
	private PhoneNumber phoneNumber;
	//郵便番号
	private PostalCode postalCode;
	//都道府県
	private Perfectures perfectures;
	//メールアドレス
	private MailAddress mailAddress;
	//住居区分
	private HouseType houseType;
	//最寄り駅
	private NearestStation nearestStation;
	
	private CurrentAddress(String personId, String currentAddressId, AddressSet address1, GeneralDate startDate, 
			GeneralDate endDate, AddressSet address2, String country, String homeSituationType, String houseRent, 
			String phoneNumber, String postalCode, String perfectures, String mailAddress, 
			String houseType, String nearestStation){
		this.personId = personId;
		this.currentAddressId = currentAddressId;
		this.address1 = address1;
		this.startDate = startDate;
		this.endDate = endDate;
		this.address2 = address2;
		this.country = new CountryId(country);
		this.homeSituationType = new HomeSituationType(homeSituationType);
		this.houseRent = new HouseRent(houseRent);
		this.phoneNumber = new PhoneNumber(phoneNumber);
		this.postalCode = new PostalCode(postalCode);
		this.perfectures = new Perfectures(perfectures);
		this.mailAddress = new MailAddress(mailAddress);
		this.houseType = new HouseType(houseType);
		this.nearestStation = new NearestStation(nearestStation);
	}
	public static CurrentAddress createObjectFormJavaType(String personId, String currentAddressId, AddressSet address1, GeneralDate startDate, 
			GeneralDate endDate, AddressSet address2, String country, String homeSituationType, String houseRent, 
			String phoneNumber, String postalCode, String perfectures, String mailAddress, 
			String houseType, String nearestStation){
		return new CurrentAddress(personId, currentAddressId, address1, startDate, 
				endDate, address2, country, homeSituationType, houseRent, 
				phoneNumber, postalCode, perfectures, mailAddress, 
				houseType, nearestStation);
	}
}
