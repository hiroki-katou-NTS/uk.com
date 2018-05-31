package nts.uk.ctx.at.function.infra.repository.monthlycorrection.fixedformatmonthly;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.BusinessTypeSortedMon;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.BusinessTypeSortedMonRepository;
import nts.uk.ctx.at.function.infra.entity.monthlycorrection.fixedformatmonthly.KrcmtBusinessTypeSortedMon;

@Stateless
public class JpaBusinessTypeSortedMonRepo extends JpaRepository implements BusinessTypeSortedMonRepository  {

	private static final String Get_ALL_BY_CID = "SELECT c FROM KrcmtBusinessTypeSortedMon c"
			+ " WHERE c.krcmtBusinessTypeSortedMonPK.companyID = :companyID";
	
	@Override
	public Optional<BusinessTypeSortedMon> getOrderReferWorkType(String companyID) {
		List<KrcmtBusinessTypeSortedMon> data = this.queryProxy().query(Get_ALL_BY_CID,KrcmtBusinessTypeSortedMon.class)
				.setParameter("companyID", companyID)
				.getList();
		if(data.isEmpty())
			return Optional.empty();
		BusinessTypeSortedMon businessTypeSortedMon = new BusinessTypeSortedMon(
				companyID,
				data.stream().map(c->c.toDomain()).collect(Collectors.toList())
				);
		return Optional.of(businessTypeSortedMon);
	}

	@Override
	public void addBusinessTypeSortedMon(BusinessTypeSortedMon businessTypeSortedMon) {
		List<KrcmtBusinessTypeSortedMon> newEntity = businessTypeSortedMon.getListOrderReferWorkType().stream()
				.map(c->KrcmtBusinessTypeSortedMon.toEntity(businessTypeSortedMon.getCompanyID(), c)).collect(Collectors.toList());
		this.commandProxy().insertAll(newEntity);
		
	}

	@Override
	public void updateBusinessTypeSortedMon(BusinessTypeSortedMon businessTypeSortedMon) {
		List<KrcmtBusinessTypeSortedMon> newEntity = businessTypeSortedMon.getListOrderReferWorkType().stream()
				.map(c->KrcmtBusinessTypeSortedMon.toEntity(businessTypeSortedMon.getCompanyID(), c)).collect(Collectors.toList());
		List<KrcmtBusinessTypeSortedMon> updateEntity = this.queryProxy().query(Get_ALL_BY_CID,KrcmtBusinessTypeSortedMon.class)
				.setParameter("companyID", businessTypeSortedMon.getCompanyID())
				.getList();
		List<KrcmtBusinessTypeSortedMon> toAdd = new ArrayList<>();
		List<KrcmtBusinessTypeSortedMon> toupdate = new ArrayList<>();
		List<KrcmtBusinessTypeSortedMon> toRemove = new ArrayList<>();
		
		//check add amd update
		for(KrcmtBusinessTypeSortedMon nBusiness: newEntity) {
			boolean checkExist = false;
			for(KrcmtBusinessTypeSortedMon uBusiness: updateEntity) {
				if(nBusiness.krcmtBusinessTypeSortedMonPK.companyID.equals(uBusiness.krcmtBusinessTypeSortedMonPK.companyID)
						&& nBusiness.krcmtBusinessTypeSortedMonPK.attendanceItemID == uBusiness.krcmtBusinessTypeSortedMonPK.attendanceItemID) {
					checkExist=true;
					toupdate.add(nBusiness);
					break;
				}
			}
			if(!checkExist) {
				toAdd.add(nBusiness);
			}
			
		}
		//check remove
		for(KrcmtBusinessTypeSortedMon uBusiness: updateEntity ) {
			boolean checkExist = false;
			for(KrcmtBusinessTypeSortedMon nBusiness: newEntity) {
				if(uBusiness.krcmtBusinessTypeSortedMonPK.companyID.equals(nBusiness.krcmtBusinessTypeSortedMonPK.companyID)
						&& uBusiness.krcmtBusinessTypeSortedMonPK.attendanceItemID == nBusiness.krcmtBusinessTypeSortedMonPK.attendanceItemID) {
					checkExist=true;
				}
			}
			if(!checkExist) {
				toRemove.add(uBusiness);
			}
			
		}
		this.commandProxy().insertAll(toAdd);
		this.commandProxy().updateAll(toupdate);
		this.commandProxy().removeAll(toRemove);
		
	}

	@Override
	public void deleteBusinessTypeSortedMon(String companyID) {
		List<KrcmtBusinessTypeSortedMon> newEntity = this.queryProxy().query(Get_ALL_BY_CID,KrcmtBusinessTypeSortedMon.class)
				.setParameter("companyID", companyID)
				.getList();
		this.commandProxy().removeAll(newEntity);
	}


}
