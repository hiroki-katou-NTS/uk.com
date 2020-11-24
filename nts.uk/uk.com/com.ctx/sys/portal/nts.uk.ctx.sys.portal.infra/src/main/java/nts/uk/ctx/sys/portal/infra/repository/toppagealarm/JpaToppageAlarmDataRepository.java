package nts.uk.ctx.sys.portal.infra.repository.toppagealarm;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmData;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmDataRepository;
import nts.uk.ctx.sys.portal.infra.entity.toppagealarm.SptdtToppageAlarm;

@Stateless
public class JpaToppageAlarmDataRepository extends JpaRepository implements ToppageAlarmDataRepository {
	// Select all
	private static final String QUERY_SELECT_ALL = "SELECT m FROM SptdtToppageAlarm m";
	// Select unread
	private static final String QUERY_SELECT_UNREAD = QUERY_SELECT_ALL
			+ " LEFT JOIN SptdtToppageKidoku h ON m.pk.cId = h.pk.cId"
			+ " AND m.pk.alarmCls = h.pk.alarmCls"
			+ " AND m.pk.idenKey = h.pk.idenKey"
			+ " AND m.pk.dispSid = h.pk.dispSid"
			+ " AND m.pk.dispAtr = h.pk.dispAtr"
			+ " WHERE m.pk.cId = :cId"
			+ " AND m.pk.dispSid = :sId"
			+ " AND m.crtDatetime >= :lastYear"
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
			+ " AND m.crtDatetime >= :lastYear";
	
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
	public List<ToppageAlarmData> getUnread(String companyId, String sId) {
		return this.queryProxy()
				.query(QUERY_SELECT_UNREAD, SptdtToppageAlarm.class)
				.setParameter("cId", companyId)
				.setParameter("sId", sId)
				.setParameter("lastYear", GeneralDateTime.now().addYears(-1))
				.getList(SptdtToppageAlarm::toDomain);
	}

	@Override
	public List<ToppageAlarmData> getAll(String companyId, String sId) {
		return this.queryProxy()
				.query(QUERY_SELECT_UNREAD_READ, SptdtToppageAlarm.class)
				.setParameter("cId", companyId)
				.setParameter("sId", sId)
				.setParameter("lastYear", GeneralDateTime.now().addYears(-1))
				.getList(SptdtToppageAlarm::toDomain);
	}

}
