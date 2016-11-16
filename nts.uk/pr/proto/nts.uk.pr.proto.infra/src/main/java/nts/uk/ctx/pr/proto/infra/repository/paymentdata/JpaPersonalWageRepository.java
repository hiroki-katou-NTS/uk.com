package nts.uk.ctx.pr.proto.infra.repository.paymentdata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.personalinfo.wage.PersonalWage;
import nts.uk.ctx.pr.proto.dom.personalinfo.wage.PersonalWageRepository;
import nts.uk.ctx.pr.proto.infra.entity.personalinfo.wage.PprmtPersonWage;

@RequestScoped
public class JpaPersonalWageRepository extends JpaRepository implements PersonalWageRepository {

	private final String SELECT_BY_YEAR_MONTH = "SELECT c FROM PPRMT_PERSON_WAGE c WHERE c.CCD = :CCD and c.PID = :PID and c.STR_YM <= :BASEYM and c.END_YM >= :BASEYM";
	
	@Override
	public List<PersonalWage> find(String companyCode, List<String> personIdList, Date baseYm) {
		List<PersonalWage> lstPersonalWage = new ArrayList<>();
		for (int i = 0; i < personIdList.size(); i++) {
			Optional<PersonalWage> tmpPersonalWage = this
					.queryProxy().query(SELECT_BY_YEAR_MONTH, PprmtPersonWage.class)
					.setParameter("CCD", companyCode).setParameter("PID", personIdList.get(i))
					.setParameter("BASEYM", baseYm).getSingle(c -> toDomain(c));
			if (tmpPersonalWage.isPresent()) {
				lstPersonalWage.add(tmpPersonalWage.get());
			}
		}
		return lstPersonalWage;
	}
	
	private static PersonalWage toDomain(PprmtPersonWage entity){
		PersonalWage domain = PersonalWage.createFromJavaType(entity.val, entity.pprmtPersonWagePK.pId, entity.pprmtPersonWagePK.ccd, entity.pprmtPersonWagePK.ctgAtr, entity.pprmtPersonWagePK.strYm, entity.endYm);
		entity.toDomain(domain);
		return domain;
	}

}
