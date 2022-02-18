package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.monthlycheckcondition;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.FixedExtraMon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.FixedExtraMonRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycheckcondition.KrcmtFixedExtraMon;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycheckcondition.KrcmtFixedExtraMonPK;

@Stateless
public class JpaFixedExtraMonRepository extends JpaRepository implements FixedExtraMonRepository {

	private static final String SELECT_FROM_FIXED_EXTRA = " SELECT c FROM KrcmtFixedExtraMon c "
			+ " WHERE c.krcmtFixedExtraMonPK.monAlarmCheckID = :monAlarmCheckID ";
	private static final String SELECT_FROM_FIXED_EXTRA_USEATR = " SELECT c FROM KrcmtFixedExtraMon c "
			+ " WHERE c.krcmtFixedExtraMonPK.monAlarmCheckID = :monAlarmCheckID "
			+ " AND c.useAtr = :useAtr";
	
	@Override
	public List<FixedExtraMon> getByEralCheckID(String monAlarmCheckID) {
		List<FixedExtraMon> data = this.queryProxy().query(SELECT_FROM_FIXED_EXTRA,KrcmtFixedExtraMon.class)
				.setParameter("monAlarmCheckID", monAlarmCheckID)
				.getList(c->c.toDomain());
		return data;
	}

	@Override
	public void addFixedExtraMon(FixedExtraMon fixedExtraMon) {
		this.commandProxy().insert(KrcmtFixedExtraMon.toEntity(fixedExtraMon));
		this.getEntityManager().flush();
		
	}

	@Override
	public void updateFixedExtraMon(FixedExtraMon fixedExtraMon) {
		KrcmtFixedExtraMon newEntity = KrcmtFixedExtraMon.toEntity(fixedExtraMon);
		KrcmtFixedExtraMon updateEntity = this.queryProxy().find(
				new KrcmtFixedExtraMonPK(
						newEntity.krcmtFixedExtraMonPK.monAlarmCheckID,
						newEntity.krcmtFixedExtraMonPK.fixedExtraItemMonNo
						),
				KrcmtFixedExtraMon.class).get();
		updateEntity.message = newEntity.message;
		updateEntity.useAtr = newEntity.useAtr;
		this.commandProxy().update(updateEntity);
		
	}

	private static final String DELETE_FIXED_BY_ERAL_ID =  "DELETE FROM KrcmtFixedExtraMon c "
			+ " WHERE c.krcmtFixedExtraMonPK.monAlarmCheckID = :monAlarmCheckID ";
	
	@Override
	public void deleteFixedExtraMon(String monAlarmCheckID) {
		this.getEntityManager().createQuery(DELETE_FIXED_BY_ERAL_ID)
		.setParameter("monAlarmCheckID", monAlarmCheckID).executeUpdate();
		
	}

	@Override
	public List<FixedExtraMon> getFixedItem(String anyId, boolean useAtr) {
		List<FixedExtraMon> data = this.queryProxy().query(SELECT_FROM_FIXED_EXTRA_USEATR,KrcmtFixedExtraMon.class)
				.setParameter("monAlarmCheckID", anyId)
				.setParameter("useAtr", useAtr)
				.getList(c->c.toDomain());
		return data;
	}

	@Override
	public Optional<FixedExtraMon> getForKey(String id, int no) {
		KrcmtFixedExtraMonPK pk = new KrcmtFixedExtraMonPK(id, no);
		Optional<KrcmtFixedExtraMon> result = this.queryProxy().find(pk, KrcmtFixedExtraMon.class);
		if(result.isPresent()) {
			FixedExtraMon domain = result.get().toDomain();
			return Optional.ofNullable(domain);
		}
		return Optional.empty();
	}

	@Override
	public void persistFixedExtraMon(FixedExtraMon fixedExtraMon) {
		KrcmtFixedExtraMonPK pk = new KrcmtFixedExtraMonPK(fixedExtraMon.getMonAlarmCheckID(), fixedExtraMon.getFixedExtraItemMonNo().value);
		Optional<KrcmtFixedExtraMon> optKrcmtFixedExtraMon = this.queryProxy().find(pk, KrcmtFixedExtraMon.class);
		if(optKrcmtFixedExtraMon.isPresent()) {
			KrcmtFixedExtraMon newEntity = KrcmtFixedExtraMon.toEntity(fixedExtraMon);
			this.commandProxy().update(newEntity);
		} else {
			this.commandProxy().insert(KrcmtFixedExtraMon.toEntity(fixedExtraMon));
		}
	}

}
