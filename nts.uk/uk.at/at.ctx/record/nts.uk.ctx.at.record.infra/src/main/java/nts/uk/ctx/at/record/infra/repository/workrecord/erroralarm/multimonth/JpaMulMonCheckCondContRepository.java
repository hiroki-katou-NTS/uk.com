package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.multimonth;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonCheckCondContRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonthCheckCondContinue;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.multimonth.KrcmtAlstChkmltUdcont;
@Stateless
public class JpaMulMonCheckCondContRepository extends JpaRepository implements MulMonCheckCondContRepository {

	private static final String SELECT_COND_BY_ID = " SELECT c FROM KrcmtAlstChkmltUdcont c"
			+ " WHERE c.errorAlarmCheckID = :errorAlarmCheckID ";
	

	@Override
	public Optional<MulMonthCheckCondContinue> getMulMonthCheckCondContById(String errorAlarmCheckID) {
		Optional<MulMonthCheckCondContinue> data = this.queryProxy().query(SELECT_COND_BY_ID,KrcmtAlstChkmltUdcont.class)
				.setParameter("errorAlarmCheckID", errorAlarmCheckID)
				.getSingle(c->c.toDomain());
		return data;
	}

	@Override
	public void addMulMonthCheckCondCont(MulMonthCheckCondContinue mulMonthCheckCondContinue) {
		this.commandProxy().insert(KrcmtAlstChkmltUdcont.toEntity(mulMonthCheckCondContinue));
		this.getEntityManager().flush();
	}

	@Override
	public void updateMulMonthCheckCondCont(MulMonthCheckCondContinue mulMonthCheckCondContinue) {
		KrcmtAlstChkmltUdcont newEntity = KrcmtAlstChkmltUdcont.toEntity(mulMonthCheckCondContinue);
		KrcmtAlstChkmltUdcont updateEntity = this.queryProxy().find(
				mulMonthCheckCondContinue.getErrorAlarmCheckID(), KrcmtAlstChkmltUdcont.class).get();
		updateEntity.isUseFlg = newEntity.isUseFlg;
		updateEntity.continousMonth = newEntity.continousMonth;
		updateEntity.krcmtEralstCndgrp = newEntity.krcmtEralstCndgrp;
		this.commandProxy().update(updateEntity);
		this.getEntityManager().flush();
	}

	@Override
	public void deleteMulMonthCheckCondCont(String errorAlarmCheckID) {
		KrcmtAlstChkmltUdcont newEntity = this.queryProxy().query(SELECT_COND_BY_ID,KrcmtAlstChkmltUdcont.class)
				.setParameter("errorAlarmCheckID", errorAlarmCheckID)
				.getSingle().get();
		this.commandProxy().remove(newEntity);
		this.getEntityManager().flush();
	}
}
