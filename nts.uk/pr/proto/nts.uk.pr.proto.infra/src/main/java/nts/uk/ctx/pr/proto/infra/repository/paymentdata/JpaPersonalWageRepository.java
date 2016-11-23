package nts.uk.ctx.pr.proto.infra.repository.paymentdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.ListUtil;
import nts.uk.ctx.pr.proto.dom.personalinfo.wage.PersonalWage;
import nts.uk.ctx.pr.proto.dom.personalinfo.wage.PersonalWageRepository;
import nts.uk.ctx.pr.proto.infra.entity.personalinfo.wage.PprmtPersonWage;
import nts.uk.ctx.pr.proto.infra.entity.personalinfo.wage.PprmtPersonWagePK;

@RequestScoped
public class JpaPersonalWageRepository extends JpaRepository implements PersonalWageRepository {

	private final String SELECT_LIST_BY_YEAR_MONTH = "SELECT c FROM PprmtPersonWage WHERE c.pprmtPersonWagePK.ccd = :ccd and c.pprmtPersonWagePK.pId IN :pIds and c.pprmtPersonWagePK.strYm <= :baseYm and c.endYm >= :baseYm";

	@Override
	public List<PersonalWage> findAll(String companyCode, List<String> personIds, int baseYm) {
		List<PersonalWage> results = new ArrayList<>();
		ListUtil.split(personIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, personIdList -> {
			this.queryProxy().query(SELECT_LIST_BY_YEAR_MONTH, PprmtPersonWage.class)
			.setParameter("ccd", companyCode).setParameter("pIds", personIdList).setParameter("baseYm", baseYm)
			.getList().stream().forEach(e -> results.add(toDomain(e)));
		});
		
		return results;

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
