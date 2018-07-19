package nts.uk.ctx.at.shared.infra.repository.specialholiday.specialholidayevent.grantdayperrelationship;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.relationship.primitives.RelationshipCode;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship.GrantDayPerRelationship;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship.GrantDayRelationship;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship.GrantDayRelationshipRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship.GrantedDay;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship.MorningHour;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.specialholidayevent.grantdayperrelationship.KshstGrantDayPerRelationship;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.specialholidayevent.grantdayperrelationship.KshstGrantDayPerRelationshipPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.specialholidayevent.grantdayperrelationship.KshstGrantDayRelationship;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.specialholidayevent.grantdayperrelationship.KshstGrantDayRelationshipPK;

@Stateless
public class JpaGrantDayRelationshipRepository extends JpaRepository implements GrantDayRelationshipRepository {

	private static final String SELECT_ITEM_BY_CD_QUERY = "SELECT a" + " FROM KshstGrantDayRelationship a"
			+ " INNER JOIN KshstGrantDayPerRelationship b" + " ON a.pk.sHolidayEventNo = b.pk.sHolidayEventNo"
			+ " AND b.pk.companyId = :companyId" + " WHERE b.pk.companyId = :companyId"
			+ " AND b.pk.sHolidayEventNo = :sHENo" + " AND a.pk.relationshipCd = :relpCd"
			+ " AND a.pk.companyId = :companyId";

	private static final String SELECT_ITEM_BY_NO_QUERY = "SELECT a" + " FROM KshstGrantDayRelationship a"
			+ " INNER JOIN KshstGrantDayPerRelationship b" + " ON a.pk.sHolidayEventNo = b.pk.sHolidayEventNo"
			+ " AND b.pk.companyId = :companyId" + " WHERE b.pk.companyId = :companyId"
			+ " AND b.pk.sHolidayEventNo = :sHENo" + " AND a.pk.companyId = :companyId";

	@Override
	public Optional<GrantDayRelationship> findByCd(String companyId, int sHENo, String relpCd) {
		return this.queryProxy().query(SELECT_ITEM_BY_CD_QUERY, KshstGrantDayRelationship.class)
				.setParameter("companyId", companyId).setParameter("sHENo", sHENo).setParameter("relpCd", relpCd)
				.getSingle(c -> toDomain(c));
	}

	private GrantDayRelationship toDomain(KshstGrantDayRelationship entity) {
		return new GrantDayRelationship(entity.pk.companyId, entity.pk.sHolidayEventNo,
				new RelationshipCode(entity.pk.relationshipCd), new GrantedDay(entity.grantedDay),
				new MorningHour(entity.morningHour));
	}

	@Override
	public Optional<GrantDayPerRelationship> findPerByCd(String companyId, int sHENo) {

		return this.queryProxy()
				.find(new KshstGrantDayPerRelationshipPK(companyId, sHENo), KshstGrantDayPerRelationship.class)
				.map(x -> toPerDomain(x));

	}

	private GrantDayPerRelationship toPerDomain(KshstGrantDayPerRelationship entity) {
		return new GrantDayPerRelationship(entity.pk.companyId, entity.pk.sHolidayEventNo,
				EnumAdaptor.valueOf(entity.makeInvitation, UseAtr.class));
	}

	@Override
	public void insertPerRelp(GrantDayPerRelationship domain) {
		this.commandProxy().insert(toPerEntity(domain));
	}

	private KshstGrantDayPerRelationship toPerEntity(GrantDayPerRelationship domain) {
		return new KshstGrantDayPerRelationship(
				new KshstGrantDayPerRelationshipPK(domain.getCompanyId(), domain.getSpecialHolidayEventNo()),
				domain.getMakeInvitation().value);
	}

	@Override
	public void insertRelp(GrantDayRelationship domain) {
		this.commandProxy().insert(toEntity(domain));

	}

	private KshstGrantDayRelationship toEntity(GrantDayRelationship domain) {

		return new KshstGrantDayRelationship(new KshstGrantDayRelationshipPK(domain.getCompanyId(),
				domain.getRelationshipCd().v(), domain.getSHolidayEventNo()), domain.getGrantedDay().v(),
				domain.getMorningHour().v());
	}

	@Override
	public void updateRelp(String companyId, GrantDayRelationship domain) {

		Optional<KshstGrantDayRelationship> entityOpt = this.queryProxy()
				.query(SELECT_ITEM_BY_CD_QUERY, KshstGrantDayRelationship.class).setParameter("companyId", companyId)
				.setParameter("sHENo", domain.getSHolidayEventNo()).setParameter("relpCd", domain.getRelationshipCd())
				.getSingle();

		if (entityOpt.isPresent()) {
			this.commandProxy().update(entityOpt.get().updateData(domain));
		}
	}

	@Override
	public void deletePerRelp(String companyId, int sHENo) {
		this.commandProxy().remove(KshstGrantDayPerRelationship.class,
				new KshstGrantDayPerRelationshipPK(companyId, sHENo));

	}

	@Override
	public void deleteRelp(int sHENo, String relationshipCd, String companyId) {
		this.commandProxy().remove(KshstGrantDayRelationship.class,
				new KshstGrantDayRelationshipPK(companyId, relationshipCd, sHENo));

	}

	@Override
	public List<GrantDayRelationship> findBySHENo(String companyId, int sHENo) {
		return this.queryProxy().query(SELECT_ITEM_BY_NO_QUERY, KshstGrantDayRelationship.class)
				.setParameter("companyId", companyId).setParameter("sHENo", sHENo).getList(c -> toDomain(c));
	}

}
