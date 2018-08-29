package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.multimonth;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonCheckCondContRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonthCheckCondContinue;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.multimonth.KrcmtMulMonCondCont;
@Stateless
public class JpaMulMonCheckCondContRepository extends JpaRepository implements MulMonCheckCondContRepository {

	private static final String SELECT_COND_BY_ID = " SELECT c FROM KrcmtMulMonCondCont c"
			+ " WHERE c.errorAlarmCheckID = :errorAlarmCheckID ";
	

	@Override
	public Optional<MulMonthCheckCondContinue> getMulMonthCheckCondContById(String errorAlarmCheckID) {
		Optional<MulMonthCheckCondContinue> data = this.queryProxy().query(SELECT_COND_BY_ID,KrcmtMulMonCondCont.class)
				.setParameter("errorAlarmCheckID", errorAlarmCheckID)
				.getSingle(c->c.toDomain());
		return data;
	}

	@Override
	public void addMulMonthCheckCondCont(MulMonthCheckCondContinue mulMonthCheckCondContinue) {
		this.commandProxy().insert(KrcmtMulMonCondCont.toEntity(mulMonthCheckCondContinue));
		this.getEntityManager().flush();
	}

	@Override
	public void updateMulMonthCheckCondCont(MulMonthCheckCondContinue mulMonthCheckCondContinue) {
		KrcmtMulMonCondCont newEntity = KrcmtMulMonCondCont.toEntity(mulMonthCheckCondContinue);
		KrcmtMulMonCondCont updateEntity = this.queryProxy().find(
				mulMonthCheckCondContinue.getErrorAlarmCheckID(), KrcmtMulMonCondCont.class).get();
		updateEntity.isUseFlg = newEntity.isUseFlg;
		updateEntity.continousMonth = newEntity.continousMonth;
		updateEntity.krcmtErAlAtdItemCon = newEntity.krcmtErAlAtdItemCon;
		this.commandProxy().update(updateEntity);
		this.getEntityManager().flush();
	}

	@Override
	public void deleteMulMonthCheckCondCont(String errorAlarmCheckID) {
		KrcmtMulMonCondCont newEntity = this.queryProxy().query(SELECT_COND_BY_ID,KrcmtMulMonCondCont.class)
				.setParameter("errorAlarmCheckID", errorAlarmCheckID)
				.getSingle().get();
		this.commandProxy().remove(newEntity);
		this.getEntityManager().flush();
	}
}
