package nts.uk.ctx.bs.employee.app.find.person.item;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemFixDto;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;

@Getter
public class ItemCurrentAddressDto extends CtgItemFixDto{
	private String currentAddressId;
	private String pid;
	private String countryId;
	private String postalCode;
	private String phoneNumber;
	private String prefectures;
	private String houseRent;
	private GeneralDate StartDate;
	private GeneralDate endDate;
	private String address1;
	private String addresskana1;
	private String address2;
	private String addresskana2;
	private String homeSituationType;
	private String personMailAddress;
	private String houseType;
	private String nearestStation;
	
	private ItemCurrentAddressDto(String currentAddressId, String pid, String countryId,
			String postalCode, String phoneNumber, String prefectures, String houseRent, GeneralDate StartDate,
			GeneralDate endDate, String address1, String addresskana1, String address2, String addresskana2,
			String homeSituationType, String personMailAddress, String houseType, String nearestStation){
		super();
		this.ctgItemType = CtgItemType.CURRENT_ADDRESS;
		this.currentAddressId = currentAddressId;
		this.pid = pid;
		this.countryId = countryId;
		this.postalCode = postalCode;
		this.phoneNumber = phoneNumber; 
		this.prefectures = prefectures;
		this.houseRent = houseRent;
		this.StartDate = StartDate;
		this.endDate = endDate;
		this.address1 = address1;
		this.addresskana1 = addresskana1;
		this.address2 = address2;
		this.addresskana2 = addresskana2;
		this.homeSituationType = homeSituationType;
		this.personMailAddress = personMailAddress;
		this.houseType = houseType;
		this.nearestStation = nearestStation;
	}
	
	public static ItemCurrentAddressDto createFromJavaType(String currentAddressId, String pid, String countryId,
			String postalCode, String phoneNumber, String prefectures, String houseRent, GeneralDate StartDate,
			GeneralDate endDate, String address1, String addresskana1, String address2, String addresskana2,
			String homeSituationType, String personMailAddress, String houseType, String nearestStation){
		return new ItemCurrentAddressDto(currentAddressId, pid, countryId, postalCode, phoneNumber, prefectures, 
				houseRent, StartDate, endDate, address1, addresskana1, address2, 
				addresskana2, homeSituationType, personMailAddress, houseType, nearestStation);
	}
	
	public CurrentAddress toDomainRequiredField(){
		return CurrentAddress.createFromJavaType(currentAddressId, pid, StartDate, endDate, address1, addresskana1);
	}
}
