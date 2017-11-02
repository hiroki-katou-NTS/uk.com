/**
 * 
 */
package repository.person.currentaddress;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddressRepository;

/**
 * @author danpv
 *
 */
@Stateless
public class CurrentAddRepoImpl implements CurrentAddressRepository{

//	private CurrentAddress toDomain(){
//		return CurrentAddress.createFromJavaType(currentAddressId, pid, countryId, postalCode, phoneNumber, prefectures, 
//				houseRent, StartDate, endDate, address1, addresskana1, address2, addresskana2, 
//				homeSituationType, personMailAddress, houseType, nearestStation)
//	}
	
	@Override
	public CurrentAddress get(String personId, GeneralDate standandDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CurrentAddress getCurAddById(String curAddId) {
		// TODO Auto-generated method stub
		return null;
	}

}
