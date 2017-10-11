package nts.uk.ctx.bs.person.dom.person.currentdddress;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.info.PersonMailAddress;
import nts.uk.ctx.bs.person.dom.person.info.PersonMobile;
import nts.uk.ctx.bs.person.dom.person.info.currentaddress.Perfectures;

@Getter
@AllArgsConstructor
public class CurrentAddress extends AggregateRoot {

	private String currentAddressId;

	private String pid;

	private String countryId;

	private PostalCode postalCode;

	private PersonMobile phoneNumber;

	private Prefectures prefectures;

	private HouseRent houseRent;

	private Period period;

	private AddressSet1 address1;

	private AddressSet2 address2;

	private HomeSituationType homeSituationType;

	private PersonMailAddress personMailAddress;

	private HouseType houseType;

	private NearestStation nearestStation;

	public static CurrentAddress createFromJavaType(String currentAddressId, String pid, String countryId,
			String postalCode, String phoneNumber, String prefectures, String houseRent, GeneralDate StartDate,
			GeneralDate endDate, String address1, String addresskana1, String address2, String addresskana2,
			String homeSituationType, String personMailAddress, String houseType, String nearestStation) {
		return new CurrentAddress(currentAddressId, pid, countryId, new PostalCode(postalCode),
				new PersonMobile(phoneNumber), new Perfectures(prefectures), new HouseRent(houseRent),
				new Period(StartDate, endDate),
				new AddressSet1(new PersonAddress1(address1), new PersonAddressKana1(addresskana1)),
				new AddressSet2(new PersonAddress2(address2), new PersonAddressKana2(addresskana2)),
				new HomeSituationType(homeSituationType), new PersonMailAddress(personMailAddress),
				new HouseType(houseType), new NearestStation(nearestStation));
	}

	public CurrentAddress(String currentAddressId2, String pid2, String countryId2, PostalCode postalCode2,
			PersonMobile phoneNumber2, Perfectures perfectures, HouseRent houseRent2, Period period2,
			AddressSet1 address12, AddressSet2 address22, HomeSituationType homeSituationType2,
			PersonMailAddress personMailAddress2, HouseType houseType2, NearestStation nearestStation2) {
	}

}
