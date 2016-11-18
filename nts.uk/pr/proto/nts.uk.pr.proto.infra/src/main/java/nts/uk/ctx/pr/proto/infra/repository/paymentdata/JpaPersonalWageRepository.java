package nts.uk.ctx.pr.proto.infra.repository.paymentdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.personalinfo.wage.PersonalWage;
import nts.uk.ctx.pr.proto.dom.personalinfo.wage.PersonalWageRepository;
import nts.uk.ctx.pr.proto.infra.entity.personalinfo.wage.PprmtPersonWage;
import nts.uk.ctx.pr.proto.infra.entity.personalinfo.wage.PprmtPersonWagePK;

@RequestScoped
public class JpaPersonalWageRepository extends JpaRepository implements PersonalWageRepository {

	private final String SELECT_BY_YEAR_MONTH = "SELECT c FROM PprmtPersonWage WHERE c.pprmtPersonWagePK.ccd = :CCD and c.pprmtPersonWagePK.pId = :PID and c.pprmtPersonWagePK.strYm <= :BASEYM and c.endYm >= :BASEYM";

	@Override
	public List<PersonalWage> findAll(String companyCode, List<String> personIdList, int baseYm) {
		List<PersonalWage> lstPersonalWage = new ArrayList<>();
		for (int i = 0; i < personIdList.size(); i++) {
			Optional<PersonalWage> tmpPersonalWage = this.queryProxy()
					.query(SELECT_BY_YEAR_MONTH, PprmtPersonWage.class).setParameter("CCD", companyCode)
					.setParameter("PID", personIdList.get(i)).setParameter("BASEYM", baseYm)
					.getSingle(c -> toDomain(c));
			if (tmpPersonalWage.isPresent()) {
				lstPersonalWage.add(tmpPersonalWage.get());
			}
		}
		return lstPersonalWage;
	}

	private static PersonalWage toDomain(PprmtPersonWage entity) {
		PersonalWage domain = PersonalWage.createFromJavaType(entity.val, entity.pprmtPersonWagePK.pId,
				entity.pprmtPersonWagePK.ccd, entity.pprmtPersonWagePK.ctgAtr, entity.pprmtPersonWagePK.strYm,
				entity.endYm);
		entity.toDomain(domain);
		return domain;
	}

	@Override
	public Optional<PersonalWage> find(String companyCode, String personId, int categoryAttribute, String wageCode,
			int startYearMonth) {
		return this.queryProxy()
				.find(new PprmtPersonWagePK(companyCode, personId, categoryAttribute, wageCode, startYearMonth),
						PprmtPersonWage.class)
				.map(c -> toDomain(c));
	}

}
