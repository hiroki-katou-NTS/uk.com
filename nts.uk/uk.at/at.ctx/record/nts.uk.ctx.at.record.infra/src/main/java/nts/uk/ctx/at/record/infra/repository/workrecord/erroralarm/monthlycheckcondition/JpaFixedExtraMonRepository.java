package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.monthlycheckcondition;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.FixedExtraMon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.FixedExtraMonRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycheckcondition.KrcmtFixedExtraMon;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycheckcondition.KrcmtFixedExtraMonPK;

@Stateless
public class JpaFixedExtraMonRepository extends JpaRepository implements FixedExtraMonRepository {

	private static final String SELECT_FROM_FIXED_EXTRA = " SELECT c FROM KrcmtFixedExtraMon c "
			+ " WHERE c.krcmtFixedExtraMonPK.monAlarmCheckID = :monAlarmCheckID";
	
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

}
