package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.paymana.KrcmtPayoutSubOfHDMana;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.paymana.KrcmtPayoutSubOfHDManaPK;

@Stateless
public class JpaPayoutSubofHDManaRepository extends JpaRepository implements PayoutSubofHDManaRepository {

	private static final String QUERY = "SELECT ps FROM KrcmtPayoutSubOfHDMana ps ";

	private static final String QUERY_BY_PAYOUTID = String.join(" ", QUERY,
			" WHERE ps.krcmtPayoutSubOfHDManaPK.sid =:sid and ps.krcmtPayoutSubOfHDManaPK.occDate =:occDate");

	private static final String QUERY_BY_SUBID = String.join(" ", QUERY,
			" WHERE ps.krcmtPayoutSubOfHDManaPK.sid =:sid and ps.krcmtPayoutSubOfHDManaPK.digestDate =:digestDate");

	private static final String QUERY_BY_LIST_PAYOUT_ID = String.join(" ", QUERY,
			" WHERE ps.krcmtPayoutSubOfHDManaPK.sid = :sid and ps.krcmtPayoutSubOfHDManaPK.occDate <= :endDate and ps.krcmtPayoutSubOfHDManaPK.occDate >= :startDate");

	private static final String QUERY_BY_LIST_SUB_ID = String.join(" ", QUERY,
			" WHERE ps.krcmtPayoutSubOfHDManaPK.sid = :sid and ps.krcmtPayoutSubOfHDManaPK.digestDate <= :endDate and ps.krcmtPayoutSubOfHDManaPK.digestDate >= :startDate");
	
	private static final String GET_BY_LISTDATE = " SELECT ps FROM KrcmtPayoutSubOfHDMana ps WHERE ps.krcmtPayoutSubOfHDManaPK.sid = :sid and ps.krcmtPayoutSubOfHDManaPK.digestDate IN :lstDate";
	
	private static final String GET_BY_LIST_OCC_DATE = " SELECT ps FROM KrcmtPayoutSubOfHDMana ps WHERE ps.krcmtPayoutSubOfHDManaPK.sid = :sid and ps.krcmtPayoutSubOfHDManaPK.occDate IN :lstDate";

	private static final String DELETE_BY_PAYOUTID = "DELETE FROM KrcmtPayoutSubOfHDMana ps WHERE ps.krcmtPayoutSubOfHDManaPK.sid =:sid and ps.krcmtPayoutSubOfHDManaPK.occDate =:occDate";

	private static final String DELETE_BY_SUBID = "DELETE FROM KrcmtPayoutSubOfHDMana ps WHERE ps.krcmtPayoutSubOfHDManaPK.sid =:sid and ps.krcmtPayoutSubOfHDManaPK.digestDate =:digestDate";

	private static final String DELETE_BY_SID = "DELETE FROM KrcmtPayoutSubOfHDMana ps"
			+ " WHERE (ps.krcmtPayoutSubOfHDManaPK.sid = :sid1 OR ps.krcmtPayoutSubOfHDManaPK.sid = :sid2)"
			+ " AND ps.krcmtPayoutSubOfHDManaPK.digestDate IN :digestDates"
			+ " AND ps.krcmtPayoutSubOfHDManaPK.occDate IN :occDates";
	
	@Override
	public void add(PayoutSubofHDManagement domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(PayoutSubofHDManagement domain) {
		KrcmtPayoutSubOfHDManaPK key = new KrcmtPayoutSubOfHDManaPK(domain.getSid(),
				domain.getAssocialInfo().getOutbreakDay(), domain.getAssocialInfo().getDateOfUse());
		Optional<KrcmtPayoutSubOfHDMana> existed = this.queryProxy().find(key, KrcmtPayoutSubOfHDMana.class);
		if (existed.isPresent()) {
			this.commandProxy().update(toEntity(domain));
		}
	}

	@Override
	public void delete(String sid, GeneralDate occDate, GeneralDate digestDate) {
		KrcmtPayoutSubOfHDManaPK key = new KrcmtPayoutSubOfHDManaPK(sid, occDate, digestDate);
		Optional<KrcmtPayoutSubOfHDMana> existed = this.queryProxy().find(key, KrcmtPayoutSubOfHDMana.class);
		if (existed.isPresent()) {
			this.commandProxy().remove(KrcmtPayoutSubOfHDMana.class, key);
		}

	}
	
	@Override
	public void delete(String sid1, String sid2, List<GeneralDate> occDates, List<GeneralDate> digestDates) {
		this.getEntityManager().createQuery(DELETE_BY_SID)
			.setParameter("sid1", sid1)
			.setParameter("sid2", sid2)
			.setParameter("occDates", occDates)
			.setParameter("digestDates", digestDates)
			.executeUpdate();

	}

	/**
	 * Convert from enity to domain
	 * 
	 * @param entity
	 * @return
	 */
	private PayoutSubofHDManagement toDomain(KrcmtPayoutSubOfHDMana entity) {
		return new PayoutSubofHDManagement(entity.krcmtPayoutSubOfHDManaPK.sid, entity.krcmtPayoutSubOfHDManaPK.occDate,
				entity.krcmtPayoutSubOfHDManaPK.digestDate, entity.usedDays, entity.targetSelectionAtr);
	}

	/**
	 * Convert from domain to entity
	 * 
	 * @param domain
	 * @return
	 */
	private KrcmtPayoutSubOfHDMana toEntity(PayoutSubofHDManagement domain) {
		KrcmtPayoutSubOfHDManaPK key = new KrcmtPayoutSubOfHDManaPK(domain.getSid(),
				domain.getAssocialInfo().getOutbreakDay(), domain.getAssocialInfo().getDateOfUse());
		return new KrcmtPayoutSubOfHDMana(key, domain.getAssocialInfo().getDayNumberUsed().v(),
				domain.getAssocialInfo().getTargetSelectionAtr().value);
	}

	@Override
	public List<PayoutSubofHDManagement> getByPayoutId(String sid, GeneralDate occDate) {
		List<KrcmtPayoutSubOfHDMana> listpayoutSub = this.queryProxy()
				.query(QUERY_BY_PAYOUTID, KrcmtPayoutSubOfHDMana.class).setParameter("sid", sid)
				.setParameter("occDate", occDate).getList();
		return listpayoutSub.stream().map(item -> toDomain(item)).collect(Collectors.toList());
	}

	@Override
	public List<PayoutSubofHDManagement> getBySubId(String sid, GeneralDate digestDate) {
		List<KrcmtPayoutSubOfHDMana> listpayoutSub = this.queryProxy()
				.query(QUERY_BY_SUBID, KrcmtPayoutSubOfHDMana.class).setParameter("sid", sid)
				.setParameter("digestDate", digestDate).getList();
		return listpayoutSub.stream().map(item -> toDomain(item)).collect(Collectors.toList());
	}

	@Override
	public void delete(String sid, GeneralDate occDate) {
		this.getEntityManager().createQuery(DELETE_BY_PAYOUTID).setParameter("sid", sid)
				.setParameter("occDate", occDate).executeUpdate();
	}

	@Override
	public void deleteBySubID(String sid, GeneralDate digestDate) {
		this.getEntityManager().createQuery(DELETE_BY_SUBID).setParameter("sid", sid)
				.setParameter("digestDate", digestDate).executeUpdate();
	}

	@Override
	public List<PayoutSubofHDManagement> getByListPayoutID(String sid, DatePeriod date) {
		return this.queryProxy().query(QUERY_BY_LIST_PAYOUT_ID, KrcmtPayoutSubOfHDMana.class).setParameter("sid", sid)
				.setParameter("startDate", date.start()).setParameter("endDate", date.end()).getList().stream()
				.map(item -> toDomain(item)).collect(Collectors.toList());

	}
	
	@Override
	public List<PayoutSubofHDManagement> getByListDate(String sid, List<GeneralDate> lstDate) {
		List<PayoutSubofHDManagement> result = new ArrayList<PayoutSubofHDManagement>();
		if (!lstDate.isEmpty()) {
			result = this.queryProxy().query(GET_BY_LISTDATE, KrcmtPayoutSubOfHDMana.class)
						.setParameter("sid", sid)
						.setParameter("lstDate", lstDate)
						.getList()
						.stream()
						.map(item -> toDomain(item)).collect(Collectors.toList());
		}
		return result;
	}
	
	@Override
	public List<PayoutSubofHDManagement> getByListOccDate(String sid, List<GeneralDate> lstDate) {
		List<PayoutSubofHDManagement> result = new ArrayList<PayoutSubofHDManagement>();
		if (!lstDate.isEmpty()) {
			result = this.queryProxy().query(GET_BY_LIST_OCC_DATE, KrcmtPayoutSubOfHDMana.class)
						.setParameter("sid", sid)
						.setParameter("lstDate", lstDate)
						.getList()
						.stream()
						.map(item -> toDomain(item)).collect(Collectors.toList());
		}
		return result;
	}

	@Override
	public List<PayoutSubofHDManagement> getByListSubID(String sid, DatePeriod date) {
		return this.queryProxy().query(QUERY_BY_LIST_SUB_ID, KrcmtPayoutSubOfHDMana.class).setParameter("sid", sid)
				.setParameter("startDate", date.start()).setParameter("endDate", date.end()).getList().stream()
				.map(item -> toDomain(item)).collect(Collectors.toList());
	}

}
