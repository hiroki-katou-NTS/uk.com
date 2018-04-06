package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManaDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.paymana.KrcmtSubOfHDManaData;

@Stateless
public class JpaSubstitutionOfHDManaDataRepo extends JpaRepository implements SubstitutionOfHDManaDataRepository {

	private String QUERY_BYSID = "SELECT s FROM KrcmtSubOfHDManaData s WHERE s.sID = :sid AND s.cID = :cid" ;
	
	private String QUERY_BYSID_REM_COD = String.join(" ",QUERY_BYSID, "AND s.remainDays > 0") ;
	
	@Override
	public List<SubstitutionOfHDManagementData> getBysiD(String cid, String sid) {
		List<KrcmtSubOfHDManaData> list = this.queryProxy().query(QUERY_BYSID,KrcmtSubOfHDManaData.class)
				.setParameter("sid", sid)
				.setParameter("cid", cid)
				.getList();
		return list.stream().map(i->toDomain(i)).collect(Collectors.toList());
	}
	
	@Override
	public List<SubstitutionOfHDManagementData> getBysiDRemCod(String cid, String sid) {
		List<KrcmtSubOfHDManaData> list = this.queryProxy().query(QUERY_BYSID_REM_COD,KrcmtSubOfHDManaData.class)
				.setParameter("sid", sid)
				.setParameter("cid", cid)
				.getList();
		return list.stream().map(i->toDomain(i)).collect(Collectors.toList());
	}

	/**
	 * Convert to domain
	 * @param entity
	 * @return
	 */
	private SubstitutionOfHDManagementData toDomain(KrcmtSubOfHDManaData entity) {
		return new SubstitutionOfHDManagementData(entity.subOfHDID,entity.cID, entity.sID, entity.unknownDate,
				entity.dayOff, entity.requiredDays, entity.remainDays);
	}
	
	@Override
	public void create(SubstitutionOfHDManagementData domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	private KrcmtSubOfHDManaData toEntity(SubstitutionOfHDManagementData domain){
		KrcmtSubOfHDManaData entity = new KrcmtSubOfHDManaData();
		entity.subOfHDID = domain.getSubOfHDID();
		entity.sID = domain.getSID();
		entity.cID = domain.getCid();
		entity.unknownDate = domain.getHolidayDate().isUnknownDate();
		if (domain.getHolidayDate().getDayoffDate().isPresent()){
			entity.dayOff = domain.getHolidayDate().getDayoffDate().get();
		}
		entity.requiredDays = domain.getRequiredDays().v();
		entity.remainDays = domain.getRemainDays().v();
		return entity;
	}
}
