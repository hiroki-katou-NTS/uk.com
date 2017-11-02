package nts.uk.ctx.bs.employee.app.find.employee.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.employee.item.ItemCurrentAddressDto;
import nts.uk.ctx.bs.employee.app.find.person.item.CtgItemType;

/**
 * Person Category Item Fix Dto
 * 
 * @author laitv
 *
 */

@NoArgsConstructor
public class CategoryItemFixDto {

	@Getter
	protected CtgItemType ctgItemType;

	public static CategoryItemFixDto createCurrentAddress(String currentAddressId, String pid, String countryId,
			String postalCode, String phoneNumber, String prefectures, String houseRent, GeneralDate startDate,
			GeneralDate endDate, String address1, String addressKana1,String address2, String addressKana2, String homeSituationType, String personMailAddress,
			String houseType, String nearestStation) {
		return ItemCurrentAddressDto.createFromJavaType(currentAddressId, pid, countryId, postalCode, phoneNumber,
				prefectures, houseRent, startDate, endDate, address1, addressKana1, address2, addressKana2,homeSituationType, personMailAddress,
				houseType, nearestStation);
	}

}
