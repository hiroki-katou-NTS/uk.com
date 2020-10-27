package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.multimonth;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonCheckCondRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonthCheckCond;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.multimonth.KrcmtAlstChkmltUdcsum;
@Stateless
public class JpaMulMonCheckCondRepository extends JpaRepository implements MulMonCheckCondRepository {

	private static final String SELECT_COND_BY_ID = " SELECT c FROM KrcmtAlstChkmltUdcsum c"
			+ " WHERE c.errorAlarmCheckID = :errorAlarmCheckID ";

	@Override
	public Optional<MulMonthCheckCond> getMulMonthCheckCondById(String errorAlarmCheckID) {
		Optional<MulMonthCheckCond> data = this.queryProxy().query(SELECT_COND_BY_ID,KrcmtAlstChkmltUdcsum.class)
				.setParameter("errorAlarmCheckID", errorAlarmCheckID)
				.getSingle(c->c.toDomain());
		return data;
	}

	@Override
	public void addMulMonthCheckCond(MulMonthCheckCond mulMonthCheckCond) {
		this.commandProxy().insert(KrcmtAlstChkmltUdcsum.toEntity(mulMonthCheckCond));
		this.getEntityManager().flush();
	}

	@Override
	public void updateMulMonthCheckCond(MulMonthCheckCond mulMonthCheckCond) {
		KrcmtAlstChkmltUdcsum newEntity = KrcmtAlstChkmltUdcsum.toEntity(mulMonthCheckCond);
		KrcmtAlstChkmltUdcsum updateEntity = this.queryProxy().find(
				mulMonthCheckCond.getErrorAlarmCheckID(), KrcmtAlstChkmltUdcsum.class).get();
		updateEntity.isUseFlg = newEntity.isUseFlg;
		updateEntity.krcmtEralstCndgrp = newEntity.krcmtEralstCndgrp;
		this.commandProxy().update(updateEntity);
		this.getEntityManager().flush();
	}

	@Override
	public void deleteMulMonthCheckCond(String errorAlarmCheckID) {
		KrcmtAlstChkmltUdcsum newEntity = this.queryProxy().query(SELECT_COND_BY_ID,KrcmtAlstChkmltUdcsum.class)
				.setParameter("errorAlarmCheckID", errorAlarmCheckID)
				.getSingle().get();
		this.commandProxy().remove(newEntity);
		this.getEntityManager().flush();
	}
}
