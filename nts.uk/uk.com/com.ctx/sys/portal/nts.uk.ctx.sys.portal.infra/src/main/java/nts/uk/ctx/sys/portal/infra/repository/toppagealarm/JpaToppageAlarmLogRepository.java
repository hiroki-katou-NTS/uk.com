package nts.uk.ctx.sys.portal.infra.repository.toppagealarm;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.toppagealarm.AlarmClassification;
import nts.uk.ctx.sys.portal.dom.toppagealarm.DisplayAtr;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmLog;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmLogRepository;
import nts.uk.ctx.sys.portal.infra.entity.toppagealarm.SptdtToppageKidoku;

@Stateless
public class JpaToppageAlarmLogRepository extends JpaRepository implements ToppageAlarmLogRepository {
	// Select all
	private static final String QUERY_SELECT_ALL = "SELECT m FROM SptdtToppageKidoku m";
	private static final String QUERY_SELECT_ONE = QUERY_SELECT_ALL
			+ " WHERE m.pk.cId = :cId"
			+ " AND m.pk.alarmCls = :alarmCls"
			+ " AND m.pk.idenKey = :idenKey"
			+ " AND m.pk.dispSid = :dispSid"
			+ " AND m.pk.dispAtr = :dispAtr";
	
	@Override
	public void insert(String contractCd, ToppageAlarmLog domain) {
		// Convert data to entity
		SptdtToppageKidoku entity = SptdtToppageKidoku.toEntity(contractCd, domain);
		// Insert entity
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(String contractCd, ToppageAlarmLog domain) {
		// Convert data to entity
		SptdtToppageKidoku entity = SptdtToppageKidoku.toEntity(contractCd, domain);
		SptdtToppageKidoku oldEntity = this.queryProxy().find(entity.getPk(), SptdtToppageKidoku.class).get();
		oldEntity.setAlreadyDatetime(entity.getAlreadyDatetime());
		// Update entity
		this.commandProxy().update(oldEntity);
	}

	@Override
	public Optional<ToppageAlarmLog> get(String companyId, AlarmClassification alarmCls, String idenKey, String sId,
			DisplayAtr dispAtr) {
		return this.queryProxy()
				.query(QUERY_SELECT_ONE, SptdtToppageKidoku.class)
				.setParameter("cId", companyId)
				.setParameter("alarmCls", String.valueOf(alarmCls.value))
				.setParameter("idenKey", idenKey)
				.setParameter("dispSid", sId)
				.setParameter("dispAtr", String.valueOf(dispAtr.value))
				.getSingle(SptdtToppageKidoku::toDomain);
	}

}
