package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.multimonth;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonCheckCondCospRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonthCheckCondCosp;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.multimonth.KrcmtMulMonCondCosp;
@Stateless
public class JpaMulMonCheckCondCospRepository extends JpaRepository implements MulMonCheckCondCospRepository {

	private static final String SELECT_COND_BY_ID = " SELECT c FROM KrcmtMulMonCondCosp c"
			+ " WHERE c.errorAlarmCheckID = :errorAlarmCheckID ";
	
	@Override
	public Optional<MulMonthCheckCondCosp> getMulMonthCheckCondCospById(String errorAlarmCheckID) {
		Optional<MulMonthCheckCondCosp> data = this.queryProxy().query(SELECT_COND_BY_ID, KrcmtMulMonCondCosp.class)
				.setParameter("errorAlarmCheckID", errorAlarmCheckID)
				.getSingle(c->c.toDomain());
		return data;
	}

	@Override
	public void addMulMonthCheckCondCosp(MulMonthCheckCondCosp mulMonthCheckCondCosp) {
		this.commandProxy().insert(KrcmtMulMonCondCosp.toEntity(mulMonthCheckCondCosp));
		this.getEntityManager().flush();
	}

	@Override
	public void updateMulMonthCheckCondCosp(MulMonthCheckCondCosp mulMonthCheckCondCosp) {
		KrcmtMulMonCondCosp newEntity = KrcmtMulMonCondCosp.toEntity(mulMonthCheckCondCosp);
		KrcmtMulMonCondCosp updateEntity = this.queryProxy().find(
				mulMonthCheckCondCosp.getErrorAlarmCheckID(), KrcmtMulMonCondCosp.class).get();
		updateEntity.isUseFlg = newEntity.isUseFlg;
		updateEntity.times = newEntity.times;
		updateEntity.compareOperator = newEntity.compareOperator;
		updateEntity.krcmtErAlAtdItemCon = newEntity.krcmtErAlAtdItemCon;
		this.commandProxy().update(updateEntity);
		this.getEntityManager().flush();
	}

	@Override
	public void deleteMulMonthCheckCondCosp(String errorAlarmCheckID) {
		KrcmtMulMonCondCosp newEntity = this.queryProxy().query(SELECT_COND_BY_ID,KrcmtMulMonCondCosp.class)
				.setParameter("errorAlarmCheckID", errorAlarmCheckID)
				.getSingle().get();
		this.commandProxy().remove(newEntity);
		this.getEntityManager().flush();
	}
}
