/**
 * 
 */
package repository.person.currentaddress;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import entity.person.currentaddress.BpsmtCurrentaddress;
import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddressRepository;

/**
 * @author danpv
 *
 */
@Stateless
public class CurrentAddRepoImpl extends JpaRepository implements CurrentAddressRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.person.dom.person.currentdddress.CurrentAddressRepository#get(
	 * java.lang.String, nts.arc.time.GeneralDate)
	 */
	public final String GET_ALL_BY_PID = "SELECT c FROM BpsmtCurrentaddress c WHERE c.pid = :pid";

	private List<CurrentAddress> toListCurrentAddress(List<BpsmtCurrentaddress> listEntity) {
		List<CurrentAddress> lstCurrentAddress = new ArrayList<>();
		if (!listEntity.isEmpty()) {
			listEntity.stream().forEach(c -> {
				CurrentAddress currentAddress = toDomainCurrentAddress(c);

				lstCurrentAddress.add(currentAddress);
			});
		}
		return lstCurrentAddress;
	}

	private CurrentAddress toDomainCurrentAddress(BpsmtCurrentaddress entity) {
		val domain = CurrentAddress.createFromJavaType(entity.bpsmtCurrentaddressPK.currentAddId, entity.pid,
				entity.countryId, entity.postalCode, entity.phoneNumber, entity.prefectures, entity.houseRent,
				entity.startDate, entity.endDate, entity.address1, entity.addressKana1, entity.address2,
				entity.addressKana2, entity.homeSituationType, entity.emailAddress, entity.houseType,
				entity.nearestStation);
		return domain;
	}

	public CurrentAddress get(String personId, GeneralDate standandDate) {
		// TODO Auto-generated method stub
		return null
				 
				
				    ;
	}

	@Override
	public List<CurrentAddress> getListByPid(String pid) {
		// TODO Auto-generated method stub
		List<BpsmtCurrentaddress> listEntity = this.queryProxy().query(GET_ALL_BY_PID, BpsmtCurrentaddress.class)
				.setParameter("pid", pid).getList();
		;
		return toListCurrentAddress(listEntity);
	}

	public CurrentAddress getCurAddById(String curAddId) {
		// TODO Auto-generated method stub
		return null;
	}
}
