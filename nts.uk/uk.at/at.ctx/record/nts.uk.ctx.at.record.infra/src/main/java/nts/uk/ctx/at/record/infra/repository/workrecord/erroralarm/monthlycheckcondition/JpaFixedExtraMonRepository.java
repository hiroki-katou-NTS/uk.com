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

	private final String SELECT_FROM_FIXED_EXTRA = " SELECT c FROM KrcmtFixedExtraMon c "
			+ " WHERE c.krcmtFixedExtraMonPK.errorAlarmCheckID = :errorAlarmCheckID";
	
	@Override
	public List<FixedExtraMon> getByEralCheckID(String errorAlarmCheckID) {
		List<FixedExtraMon> data = this.queryProxy().query(SELECT_FROM_FIXED_EXTRA,KrcmtFixedExtraMon.class)
				.setParameter("errorAlarmCheckID", errorAlarmCheckID)
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
						newEntity.krcmtFixedExtraMonPK.errorAlarmCheckID,
						newEntity.krcmtFixedExtraMonPK.fixedExtraItemMonNo
						),
				KrcmtFixedExtraMon.class).get();
		updateEntity.message = newEntity.message;
		updateEntity.useAtr = newEntity.useAtr;
		this.commandProxy().update(updateEntity);
		
	}

	private final String DELETE_FIXED_BY_ERAL_ID =  "DELETE FROM KrcmtFixedExtraMon c "
			+ " WHERE c.krcmtFixedExtraMonPK.errorAlarmCheckID = :errorAlarmCheckID ";
	
	@Override
	public void deleteFixedExtraMon(String errorAlarmCheckID) {
		this.getEntityManager().createQuery(DELETE_FIXED_BY_ERAL_ID)
		.setParameter("errorAlarmCheckID", errorAlarmCheckID).executeUpdate();
		
	}

}
