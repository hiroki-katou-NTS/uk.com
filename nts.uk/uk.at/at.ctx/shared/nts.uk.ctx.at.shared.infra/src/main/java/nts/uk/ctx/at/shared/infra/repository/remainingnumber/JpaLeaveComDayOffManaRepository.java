package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.subhdmana.KrcmtLeaveDayOffMana;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.subhdmana.KrcmtLeaveDayOffManaPK;

@Stateless
public class JpaLeaveComDayOffManaRepository extends JpaRepository implements LeaveComDayOffManaRepository{

	private static final String QUERY = "SELECT lc FROM KrcmtLeaveDayOffMana lc";
	
	private static final String QUERY_BY_LEAVEID = String.join(" ", QUERY,
			" WHERE lc.krcmtLeaveDayOffManaPK.sid =:sid and lc.krcmtLeaveDayOffManaPK.occDate = :occDate");

	private static final String QUERY_BY_COMDAYOFFID = String.join(" ", QUERY,
			" WHERE lc.krcmtLeaveDayOffManaPK.sid =:sid and lc.krcmtLeaveDayOffManaPK.digestDate = :digestDate");

	private static final String QUERY_BY_LIST_LEAVEID = String.join(" ", QUERY,
			" WHERE lc.krcmtLeaveDayOffManaPK.sid = :sid and lc.krcmtLeaveDayOffManaPK.occDate <= : endDate and  lc.krcmtLeaveDayOffManaPK.occDate >= startDate");

	private static final String QUERY_BY_LIST_COMID = String.join(" ", QUERY,
			" WHERE lc.krcmtLeaveDayOffManaPK.sid = :sid and lc.krcmtLeaveDayOffManaPK.digestDate <= : endDate and  lc.krcmtLeaveDayOffManaPK.digestDate >= startDate");

	private static final String QUERY_BY_LIST_DATE = String.join(" ", QUERY,
			" WHERE lc.krcmtLeaveDayOffManaPK.sid = :sid and lc.krcmtLeaveDayOffManaPK.digestDate IN lstDate");

	private static final String GET_LEAVE_COM  = "SELECT c FROM KrcmtLeaveDayOffMana c "
			+ " WHERE c.krcmtLeaveDayOffManaPK.sid =:sid and cc.krcmtLeaveDayOffManaPK.occDate = :occDate";
	
	private static final String QUERY_BY_SID = String.join(" ", QUERY, " WHERE lc.krcmtLeaveDayOffManaPK.sid = :sid");
	
	@Override
	public void add(LeaveComDayOffManagement domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(LeaveComDayOffManagement domain) {
		KrcmtLeaveDayOffManaPK key = new KrcmtLeaveDayOffManaPK(domain.getSid(),
				domain.getAssocialInfo().getOutbreakDay(), domain.getAssocialInfo().getDateOfUse());
		Optional<KrcmtLeaveDayOffMana> existed = this.queryProxy().find(key, KrcmtLeaveDayOffMana.class);
		if (existed.isPresent()) {
			this.commandProxy().update(toEntity(domain));
		}
	}

	@Override
	public void delete(String sid, GeneralDate occDate, GeneralDate digestDate) {
		KrcmtLeaveDayOffManaPK key = new KrcmtLeaveDayOffManaPK(sid, occDate, digestDate);
		Optional<KrcmtLeaveDayOffMana> existed = this.queryProxy().find(key, KrcmtLeaveDayOffMana.class);
		if (existed.isPresent()){
			this.commandProxy().remove(KrcmtLeaveDayOffMana.class, key);
		}
		
	}
	
	/** 
	 * Convert from enity to domain
	 * @param entity
	 * @return
	 */
	private LeaveComDayOffManagement toDomain(KrcmtLeaveDayOffMana entity) {
		return new LeaveComDayOffManagement(entity.krcmtLeaveDayOffManaPK.sid, entity.krcmtLeaveDayOffManaPK.occDate,
				entity.krcmtLeaveDayOffManaPK.digestDate, entity.usedDays, entity.targetSelectionAtr);
	}
	
	/**
	 * Convert from domain to entity
	 * @param domain
	 * @return
	 */
	private KrcmtLeaveDayOffMana toEntity(LeaveComDayOffManagement domain){
		KrcmtLeaveDayOffManaPK key = new KrcmtLeaveDayOffManaPK(domain.getSid(),
				domain.getAssocialInfo().getOutbreakDay(), domain.getAssocialInfo().getDateOfUse());
		return new KrcmtLeaveDayOffMana(key, domain.getAssocialInfo().getDayNumberUsed().v(),
				domain.getAssocialInfo().getTargetSelectionAtr().value);
	}

	@Override
	public List<LeaveComDayOffManagement> getByLeaveID(String sid, GeneralDate occDate) {
		List<KrcmtLeaveDayOffMana> listLeaveD = this.queryProxy().query(QUERY_BY_LEAVEID,KrcmtLeaveDayOffMana.class)
				.setParameter("sid", sid)
				.setParameter("occDate", occDate)
				.getList();
		return listLeaveD.stream().map(item->toDomain(item)).collect(Collectors.toList());
	}

	@Override
	public List<LeaveComDayOffManagement> getBycomDayOffID(String sid,  GeneralDate digestDate) {
		List<KrcmtLeaveDayOffMana> listLeaveD = this.queryProxy().query(QUERY_BY_COMDAYOFFID,KrcmtLeaveDayOffMana.class)
				.setParameter("sid", sid)
				.setParameter("digestDate", digestDate)
				.getList();
		return listLeaveD.stream().map(item->toDomain(item)).collect(Collectors.toList());
	}
	
	@Override
	public void insertAll(List<LeaveComDayOffManagement> entitiesLeave) {
		List<KrcmtLeaveDayOffMana> entities = entitiesLeave.stream()
				.map(c -> newEntities(c.getSid(), c.getAssocialInfo().getOutbreakDay(),
						c.getAssocialInfo().getDateOfUse(), c.getAssocialInfo().getDayNumberUsed().v(),
						c.getAssocialInfo().getTargetSelectionAtr().value))
				.collect(Collectors.toList());
		commandProxy().insertAll(entities);
		this.getEntityManager().flush();
	}
	
	private KrcmtLeaveDayOffMana newEntities(String sid, GeneralDate occDate,  GeneralDate digestDate, Double usedDays, int targetSelectionAtr) {
		return new KrcmtLeaveDayOffMana(new KrcmtLeaveDayOffManaPK(sid, occDate, digestDate),
				usedDays, targetSelectionAtr);
	}

	@Override
	public void deleteByLeaveId(String sid, GeneralDate occDate) {
		List<KrcmtLeaveDayOffMana> data = this.queryProxy().query(GET_LEAVE_COM,KrcmtLeaveDayOffMana.class)
				.setParameter("sid", sid)
				.setParameter("occDate", occDate)
				.getList();
		this.commandProxy().removeAll(data);
		this.getEntityManager().flush();
	}
	
	@Override
	public List<LeaveComDayOffManagement> getByListComLeaveID(String sid, DatePeriod period) {
					return this.queryProxy()
					.query(QUERY_BY_LIST_LEAVEID,KrcmtLeaveDayOffMana.class)
					.setParameter("sid", sid)
					.setParameter("startDate", period.start())
					.setParameter("endDate", period.end())
					.getList().stream().map(item->toDomain(item)).collect(Collectors.toList());
	}
	
	@Override
	public List<LeaveComDayOffManagement> getByListComId(String sid, DatePeriod period) {
			return this.queryProxy().query(QUERY_BY_LIST_COMID,KrcmtLeaveDayOffMana.class)
					.setParameter("sid", sid)
					.setParameter("startDate", period.start())
					.setParameter("endDate", period.end())
				.getList().stream().map(item->toDomain(item)).collect(Collectors.toList());
	}
	
	@Override
	public List<LeaveComDayOffManagement> getByListDate(String sid, List<GeneralDate> lstDate) {
			return this.queryProxy().query(QUERY_BY_LIST_DATE,KrcmtLeaveDayOffMana.class)
					.setParameter("sid", sid)
					.setParameter("lstDate", lstDate)
				.getList().stream().map(item->toDomain(item)).collect(Collectors.toList());
	}

	@Override
	public void deleteByComDayOffID(String sid,  GeneralDate digestDate) {
		List<KrcmtLeaveDayOffMana> data = this.queryProxy().query(QUERY_BY_COMDAYOFFID,KrcmtLeaveDayOffMana.class)
				.setParameter("sid", sid)
				.setParameter("digestDate", digestDate)
				.getList();
		this.commandProxy().removeAll(data);
		this.getEntityManager().flush();
	}

	
	@Override
	public void deleteByComDayOffId(String sid,  GeneralDate digestDate) {
		Optional<KrcmtLeaveDayOffMana> entity = this.queryProxy()
				.query(QUERY_BY_COMDAYOFFID, KrcmtLeaveDayOffMana.class).setParameter("sid", sid)
				.setParameter("digestDate", digestDate)
				.getSingle();
		if(entity.isPresent()){
			this.commandProxy().remove(entity.get());
		}
	}
	
	/**
	 * ドメイン「休出代休紐付け管理」を取得する
	 * @param sid 社員ID
	 * @param lstOccDate 休出日
	 * @param lstDigestDate 代休日
	 * @return List leave company dayOff management 休出代休紐付け管理
	 */
	@Override
	public List<LeaveComDayOffManagement> getByListOccDigestDate(String sid, List<GeneralDate> lstOccDate, List<GeneralDate> lstDigestDate) {
		String query = "";
		List<KrcmtLeaveDayOffMana> lstEntity = new ArrayList<KrcmtLeaveDayOffMana>();
		if (!lstOccDate.isEmpty() && !lstDigestDate.isEmpty()) {
			query = String.join(" ", QUERY_BY_SID,
					"AND (lc.krcmtLeaveDayOffManaPK.occDate IN :lstOccDate",
					"OR lc.krcmtLeaveDayOffManaPK.digestDate IN :lstDigestDate)");
			lstEntity = this.queryProxy().query(query, KrcmtLeaveDayOffMana.class)
					.setParameter("sid", sid)
					.setParameter("lstOccDate", lstOccDate)
					.setParameter("lstDigestDate", lstDigestDate)
					.getList();
		} else if (lstOccDate.isEmpty() && !lstDigestDate.isEmpty()) {
			query = String.join(" ", QUERY_BY_SID, "AND lc.krcmtLeaveDayOffManaPK.digestDate IN :lstDigestDate");
			lstEntity = this.queryProxy().query(query, KrcmtLeaveDayOffMana.class)
					.setParameter("sid", sid)
					.setParameter("lstDigestDate", lstDigestDate)
					.getList();
		}
		else if (!lstOccDate.isEmpty() && lstDigestDate.isEmpty()) {
			query = String.join(" ", QUERY_BY_SID, "AND lc.krcmtLeaveDayOffManaPK.occDate IN :lstOccDate");
			lstEntity = this.queryProxy().query(query, KrcmtLeaveDayOffMana.class)
					.setParameter("sid", sid)
					.setParameter("lstOccDate", lstOccDate)
					.getList();
		}
		return lstEntity.stream()
				.map(item-> toDomain(item))
				.collect(Collectors.toList());
	}
}
