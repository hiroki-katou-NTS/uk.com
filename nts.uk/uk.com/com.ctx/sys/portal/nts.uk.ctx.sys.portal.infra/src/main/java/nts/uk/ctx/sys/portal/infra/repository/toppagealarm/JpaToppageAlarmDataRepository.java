package nts.uk.ctx.sys.portal.infra.repository.toppagealarm;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.portal.dom.toppagealarm.AlarmClassification;
import nts.uk.ctx.sys.portal.dom.toppagealarm.DisplayAtr;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmData;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmDataRepository;
import nts.uk.ctx.sys.portal.infra.entity.toppagealarm.SptdtToppageAlarm;

@Stateless
public class JpaToppageAlarmDataRepository extends JpaRepository implements ToppageAlarmDataRepository {
	// Select all
	private static final String QUERY_SELECT_ALL = "SELECT m FROM SptdtToppageAlarm m";
	private static final String QUERY_SELECT_ONE = QUERY_SELECT_ALL
			+ " WHERE m.pk.cId = :cId"
			+ " AND m.pk.alarmCls = :alarmCls"
			+ " AND m.pk.idenKey = :idenKey"
			+ " AND m.pk.dispSid = :dispSid"
			+ " AND m.pk.dispAtr = :dispAtr";
	// Select unread
	private static final String QUERY_SELECT_UNREAD = QUERY_SELECT_ALL
			+ " LEFT JOIN SptdtToppageKidoku h ON m.pk.cId = h.pk.cId"
			+ " AND m.pk.alarmCls = h.pk.alarmCls"
			+ " AND m.pk.idenKey = h.pk.idenKey"
			+ " AND m.pk.dispSid = h.pk.dispSid"
			+ " AND m.pk.dispAtr = h.pk.dispAtr"
			+ " WHERE m.pk.cId = :cId"
			+ " AND m.pk.dispSid = :sId"
			+ " AND m.crtDatetime >= :afterDateTime"
			+ " AND (h.alreadyDatetime = NULL OR m.crtDatetime > h.alreadyDatetime)";
	// Select unread + read
	private static final String QUERY_SELECT_UNREAD_READ = QUERY_SELECT_ALL
			+ " LEFT JOIN SptdtToppageKidoku h ON m.pk.cId = h.pk.cId"
			+ " AND m.pk.alarmCls = h.pk.alarmCls"
			+ " AND m.pk.idenKey = h.pk.idenKey"
			+ " AND m.pk.dispSid = h.pk.dispSid"
			+ " AND m.pk.dispAtr = h.pk.dispAtr"
			+ " WHERE m.pk.cId = :cId"
			+ " AND m.pk.dispSid = :sId"
			+ " AND m.crtDatetime >= :afterDateTime";
	
	@Override
	public void insert(String contractCd, ToppageAlarmData domain) {
		// Convert data to entity
		SptdtToppageAlarm entity = SptdtToppageAlarm.toEntity(contractCd, domain);
		// Insert entity
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(String contractCd, ToppageAlarmData domain) {
		// Convert data to entity
		SptdtToppageAlarm entity = SptdtToppageAlarm.toEntity(contractCd, domain);
		SptdtToppageAlarm oldEntity = this.queryProxy().find(entity.getPk(), SptdtToppageAlarm.class).get();
		oldEntity.setCrtDatetime(entity.getCrtDatetime());
		oldEntity.setMessege(entity.getMessege());
		oldEntity.setLinkUrl(entity.getLinkUrl());
		// Update entity
		this.commandProxy().update(oldEntity);
	}

	@Override
	public Optional<ToppageAlarmData> get(String companyId, AlarmClassification alarmCls, String idenKey, String sId,
			DisplayAtr dispAtr) {
		return this.queryProxy()
				.query(QUERY_SELECT_ONE, SptdtToppageAlarm.class)
				.setParameter("cId", companyId)
				.setParameter("alarmCls", String.valueOf(alarmCls.value))
				.setParameter("idenKey", idenKey)
				.setParameter("dispSid", sId)
				.setParameter("dispAtr", String.valueOf(dispAtr.value))
				.getSingle(SptdtToppageAlarm::toDomain);
	}

	@Override
	public List<ToppageAlarmData> getUnread(String companyId, String sId, GeneralDateTime afterDateTime) {
		return this.queryProxy()
				.query(QUERY_SELECT_UNREAD, SptdtToppageAlarm.class)
				.setParameter("cId", companyId)
				.setParameter("sId", sId)
				.setParameter("afterDateTime", afterDateTime)
				.getList(SptdtToppageAlarm::toDomain);
	}

	@Override
	public List<ToppageAlarmData> getAll(String companyId, String sId, GeneralDateTime afterDateTime) {
		return this.queryProxy()
				.query(QUERY_SELECT_UNREAD_READ, SptdtToppageAlarm.class)
				.setParameter("cId", companyId)
				.setParameter("sId", sId)
				.setParameter("afterDateTime", afterDateTime)
				.getList(SptdtToppageAlarm::toDomain);
	}
}
