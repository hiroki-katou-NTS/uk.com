package nts.uk.ctx.sys.portal.infra.repository.notice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.portal.dom.notice.DestinationClassification;
import nts.uk.ctx.sys.portal.dom.notice.MessageNotice;
import nts.uk.ctx.sys.portal.dom.notice.MessageNoticeRepository;
import nts.uk.ctx.sys.portal.infra.entity.notice.SptdtInfoMessage;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaMessageNoticeRepository extends JpaRepository implements MessageNoticeRepository {
	
	private static final String GET_BY_DEST_CATEGORY = String.join(" "
			, "SELECT m FROM SptdtInfoMessage m WHERE m.pk.sid = :sid"
			, "AND m.startDate <= :endDate"
			, "AND m.endDate >= :startDate"
			, "AND m.destination = :destination"
			, "ORDER BY m.startDate DESC, endDate DESC, m.pk.inputDate DESC");
	
	private static final String GET_BY_PERIOD_AND_SID = String.join(" "
			, "SELECT m FROM SptdtInfoMessage m WHERE m.pk.sid = :sid"
			, "AND m.startDate <= :endDate"
			, "AND m.endDate >= :startDate"
			, "ORDER BY m.startDate DESC, endDate DESC, m.pk.inputDate DESC");
	
	private static final String GET_FROM_LIST_WORKPLACE_ID = String.join(" "
			, "SELECT m FROM SptdtInfoMessage m JOIN SptdtInfoMessageTgt n"
			, "ON m.pk.sid = n.pk.sid AND m.pk.inputDate = n.pk.inputDate"
			, "WHERE m.startDate <= :endDate"
			, "AND m.endDate >= :startDate"
			, "AND m.destination = 0"
			, "OR (m.destination = 1 AND n.pk.tgtInfoId IN :tgtInfoId)"
			, "ORDER BY m.destination ASC, m.startDate DESC, m.endDate DESC, m.pk.inputDate DESC");
	
	private static final String GET_MSG_REF_BY_PERIOD = String.join(" "
			, "SELECT m, s.pk.readSid FROM SptdtInfoMessage m"
			, "LEFT JOIN SptdtInfoMessageTgt n"
			, "ON m.pk.sid = n.pk.sid AND m.pk.inputDate = n.pk.inputDate"
			, "LEFT JOIN SptdtInfoMessageRead s"
			, "ON m.pk.sid = s.pk.sid AND m.pk.inputDate = s.pk.inputDate"
			, "AND m.startDate <= :endDate"
			, "AND m.endDate >= :startDate"
			, "AND m.destination = 0"
			, "OR (m.destination = 1 AND n.pk.tgtInfoId = :wpId)"
			, "OR (m.destination = 2 AND n.pk.tgtInfoId = :sid)"
			, "ORDER BY m.startDate DESC, m.endDate DESC, m.inputDate DESC");
	
	private static final String GET_NEW_MSG_FOR_DAY = String.join(" "
			, "SELECT m from SptdtInfoMessage m"
			, "LEFT JOIN SptdtInfoMessageRead s"
			, "ON m.pk.sid = s.pk.sid AND m.pk.inputDate = s.pk.inputDate");
	
	private static final String GET_REF_BY_SID_FOR_PERIOD = String.join(" "
			, "SELECT m, s.pk.readSid"
			, "FROM SptdtInfoMessage m"
			, "LEFT JOIN SptdtInfoMessageRead s"
			, "ON m.pk.sid = s.pk.sid AND m.pk.inputDate = s.pk.inputDate"
			, "AND m.startDate <= :endDate"
			, "AND m.endDate >= :startDate"
			, "AND m.destination = 2 AND n.pk.tgtInfoId = :sid"
			, "AND s.pk.sid = :sid"
			, "ORDER BY m.startDate DESC, m.endDate DESC, m.inputDate DESC");
	
	/**
	 * Convert entity to domain
	 * @param entity
	 * @return MessageNotice
	 */
	public static MessageNotice toDomain(SptdtInfoMessage entity) {
		return MessageNotice.createFromMemento(entity);
	}
	
	/**
	 * Convert object to domain
	 * @param entity
	 * @return MessageNotice
	 */
	public static MessageNotice toDomain(Object[] entity) {
		MessageNotice domain = MessageNotice.createFromMemento((MessageNotice.MementoGetter) entity[0]);
		return domain;
	}
	
	/**
	 * Convert domain to entity
	 * @param entity
	 * @return SptdtInfoMessage
	 */
	public static SptdtInfoMessage toEntity(MessageNotice domain) {
		SptdtInfoMessage entity = new SptdtInfoMessage();
		domain.setMemento(entity);
		return entity;
	}

	@Override
	public void insert(MessageNotice msg) {
		this.commandProxy().insert(toEntity(msg));
	}

	@Override
	public void update(MessageNotice msg) {
		SptdtInfoMessage entity = toEntity(msg);
		SptdtInfoMessage oldEntity = this.queryProxy().find(entity.getPk(), SptdtInfoMessage.class).get();
		oldEntity.setContractCd(AppContexts.user().contractCode());
		oldEntity.setCompanyId(AppContexts.user().companyId());
		oldEntity.setStartDate(entity.getStartDate());
		oldEntity.setEndDate(entity.getEndDate());
		oldEntity.setUpdateDate(entity.getUpdateDate());
		oldEntity.setMessage(entity.getMessage());
		oldEntity.setDestination(entity.getDestination());
		// Update entity
		this.commandProxy().update(oldEntity);
	}

	@Override
	public void delete(MessageNotice msg) {
		SptdtInfoMessage entity = toEntity(msg);
		this.commandProxy().remove(entity.getPk());
	}

	@Override
	public List<MessageNotice> getMsgInDestinationCategory(DatePeriod period, DestinationClassification destination,
			String sid) {
		return this.queryProxy()
					.query(GET_BY_DEST_CATEGORY, SptdtInfoMessage.class)
					.setParameter("sid", sid)
					.setParameter("endDate", period.end())
					.setParameter("startDate", period.start())
					.setParameter("destination", destination.value)
					.getList()
					.stream()
					.map(entity -> MessageNotice.createFromMemento(entity))
					.collect(Collectors.toList());
	}

	@Override
	public List<MessageNotice> getMsgByPeriodAndSid(DatePeriod period, String sid) {
		return this.queryProxy()
				.query(GET_BY_PERIOD_AND_SID, SptdtInfoMessage.class)
				.setParameter("sid", sid)
				.setParameter("endDate", period.end())
				.setParameter("startDate", period.start())
				.getList()
				.stream()
				.map(entity -> MessageNotice.createFromMemento(entity))
				.collect(Collectors.toList());
	}

	@Override
	public List<MessageNotice> getMsgFromWpIdList(DatePeriod period, List<String> wpIds) {
		return this.queryProxy()
				.query(GET_FROM_LIST_WORKPLACE_ID, SptdtInfoMessage.class)
				.setParameter("endDate", period.end())
				.setParameter("startDate", period.start())
				.setParameter("tgtInfoId", wpIds)
				.getList()
				.stream()
				.map(entity -> MessageNotice.createFromMemento(entity))
				.collect(Collectors.toList());
	}

	@Override
	public List<MessageNotice> getMsgRefByPeriod(DatePeriod period, Optional<String> wpId, String sid) {
		List<SptdtInfoMessage> entities = new ArrayList<SptdtInfoMessage>();
		if (wpId.isPresent()) {
			entities = this.queryProxy().query(GET_MSG_REF_BY_PERIOD, SptdtInfoMessage.class)
							.setParameter("endDate", period.end())
							.setParameter("startDate", period.start())
							.setParameter("wpId", wpId.get())
							.setParameter("sid", sid)
							.getList();
		} else {
			String queryString = GET_MSG_REF_BY_PERIOD.replace("", " OR (m.destination = 1 AND n.pk.tgtInfoId = :wpId)");
			entities = this.queryProxy().query(queryString, SptdtInfoMessage.class)
							.setParameter("endDate", period.end())
							.setParameter("startDate", period.start())
							.setParameter("sid", sid)
							.getList();
		}
		
		return entities.stream()
				.map(entity -> MessageNotice.createFromMemento(entity)).collect(Collectors.toList());
	}

	@Override
	public List<MessageNotice> getNewMsgForDay(Optional<String> wpId) {
		// QA 112121
		// GET_NEW_MSG_FOR_DAY
		return null;
	}

	@Override
	public List<MessageNotice> getMsgRefBySidForPeriod(DatePeriod period, String sid) {
		
		// GET_REF_BY_SID_FOR_PERIOD
		return null;
	}

	@Override
	public void updateInforSawMessage(MessageNotice msg, String sid) {
		// TODO Auto-generated method stub
		
	}

}
