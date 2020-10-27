package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.multimonth;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonCheckCondCospRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonthCheckCondCosp;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.multimonth.KrcmtAlstChkmltUdccrsp;
@Stateless
public class JpaMulMonCheckCondCospRepository extends JpaRepository implements MulMonCheckCondCospRepository {

	private static final String SELECT_COND_BY_ID = " SELECT c FROM KrcmtAlstChkmltUdccrsp c"
			+ " WHERE c.errorAlarmCheckID = :errorAlarmCheckID ";
	
	@Override
	public Optional<MulMonthCheckCondCosp> getMulMonthCheckCondCospById(String errorAlarmCheckID) {
		Optional<MulMonthCheckCondCosp> data = this.queryProxy().query(SELECT_COND_BY_ID, KrcmtAlstChkmltUdccrsp.class)
				.setParameter("errorAlarmCheckID", errorAlarmCheckID)
				.getSingle(c->c.toDomain());
		return data;
	}

	@Override
	public void addMulMonthCheckCondCosp(MulMonthCheckCondCosp mulMonthCheckCondCosp) {
		this.commandProxy().insert(KrcmtAlstChkmltUdccrsp.toEntity(mulMonthCheckCondCosp));
		this.getEntityManager().flush();
	}

	@Override
	public void updateMulMonthCheckCondCosp(MulMonthCheckCondCosp mulMonthCheckCondCosp) {
		KrcmtAlstChkmltUdccrsp newEntity = KrcmtAlstChkmltUdccrsp.toEntity(mulMonthCheckCondCosp);
		KrcmtAlstChkmltUdccrsp updateEntity = this.queryProxy().find(
				mulMonthCheckCondCosp.getErrorAlarmCheckID(), KrcmtAlstChkmltUdccrsp.class).get();
		updateEntity.isUseFlg = newEntity.isUseFlg;
		updateEntity.times = newEntity.times;
		updateEntity.compareOperator = newEntity.compareOperator;
		updateEntity.krcmtEralstCndgrp = newEntity.krcmtEralstCndgrp;
		this.commandProxy().update(updateEntity);
		this.getEntityManager().flush();
	}

	@Override
	public void deleteMulMonthCheckCondCosp(String errorAlarmCheckID) {
		KrcmtAlstChkmltUdccrsp newEntity = this.queryProxy().query(SELECT_COND_BY_ID,KrcmtAlstChkmltUdccrsp.class)
				.setParameter("errorAlarmCheckID", errorAlarmCheckID)
				.getSingle().get();
		this.commandProxy().remove(newEntity);
		this.getEntityManager().flush();
	}
}
