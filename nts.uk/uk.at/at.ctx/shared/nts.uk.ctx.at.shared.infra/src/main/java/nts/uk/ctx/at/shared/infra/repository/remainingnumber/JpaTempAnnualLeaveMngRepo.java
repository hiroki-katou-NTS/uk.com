package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea.KrcdtAnnleaMngTemp;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea.KrcdtAnnleaMngTempPK;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * リポジトリ実装：暫定年休管理データ
 * @author shuichu_ishida
 */
@Stateless
public class JpaTempAnnualLeaveMngRepo extends JpaRepository implements TempAnnualLeaveMngRepository {

	private static final String SELECT_BY_PERIOD = "SELECT a FROM KrcdtAnnleaMngTemp a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "AND a.PK.ymd >= :startYmd "
			+ "AND a.PK.ymd <= :endYmd "
			+ "ORDER BY a.PK.ymd ";

	private static final String DELETE_BY_PERIOD = "DELETE FROM KrcdtAnnleaMngTemp a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "AND a.PK.ymd >= :startYmd "
			+ "AND a.PK.ymd <= :endYmd ";
	
	private static final String DELETE_PAST_YMD = "DELETE FROM KrcdtAnnleaMngTemp a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "AND a.PK.ymd <= :criteriaDate ";

	private static final String SELECT_BY_WORKTYPE_PERIOD = "SELECT a FROM KrcdtAnnleaMngTemp a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "AND a.workTypeCode = :workTypeCode "			
			+ "AND a.PK.ymd >= :startYmd "
			+ "AND a.PK.ymd <= :endYmd "
			+ "ORDER BY a.PK.ymd ";

	private static final String SELECT_BY_EMPLOYEEID = "SELECT a FROM KrcdtAnnleaMngTemp a"
			+ " WHERE a.PK.employeeId = :employeeID"
			+ " ORDER BY a.PK.ymd ASC";
	
	/** 検索 */
	@Override
	public Optional<TempAnnualLeaveManagement> find(String employeeId, GeneralDate ymd) {
		
		return this.queryProxy()
				.find(new KrcdtAnnleaMngTempPK(employeeId, ymd), KrcdtAnnleaMngTemp.class)
				.map(c -> c.toDomain());
	}
	
	/** 検索　（期間） */
	@Override
	public List<TempAnnualLeaveManagement> findByPeriodOrderByYmd(String employeeId, DatePeriod period) {

		return this.queryProxy().query(SELECT_BY_PERIOD, KrcdtAnnleaMngTemp.class)
				.setParameter("employeeId", employeeId)
				.setParameter("startYmd", period.start())
				.setParameter("endYmd", period.end())
				.getList(c -> c.toDomain());
	}

	/** 登録および更新 */
	@Override
	public void persistAndUpdate(TempAnnualLeaveManagement domain) {

		// キー
		val key = new KrcdtAnnleaMngTempPK(domain.getEmployeeId(), domain.getYmd());
		
		// 登録・更新
		KrcdtAnnleaMngTemp entity = this.getEntityManager().find(KrcdtAnnleaMngTemp.class, key);
		if (entity == null){
			entity = new KrcdtAnnleaMngTemp();
			entity.fromDomainForPersist(domain);
			this.getEntityManager().persist(entity);
		}
		else {
			entity.fromDomainForUpdate(domain);
		}
	}
	
	/** 削除 */
	@Override
	public void remove(String employeeId, GeneralDate ymd) {

		this.commandProxy().remove(KrcdtAnnleaMngTemp.class, new KrcdtAnnleaMngTempPK(employeeId, ymd));
	}
	
	/** 削除　（期間） */
	@Override
	public void removeByPeriod(String employeeId, DatePeriod period) {
		
		this.getEntityManager().createQuery(DELETE_BY_PERIOD)
				.setParameter("employeeId", employeeId)
				.setParameter("startYmd", period.start())
				.setParameter("endYmd", period.end())
				.executeUpdate();
	}
	
	/** 削除　（基準日以前） */
	@Override
	public void removePastYmd(String employeeId, GeneralDate criteriaDate) {
		
		this.getEntityManager().createQuery(DELETE_PAST_YMD)
				.setParameter("employeeId", employeeId)
				.setParameter("criteriaDate", criteriaDate)
				.executeUpdate();
	}

	@Override
	public List<TempAnnualLeaveManagement> findBySidWorkTypePeriod(String employeeId, String workTypeCode,
			DatePeriod period) {
		return this.queryProxy().query(SELECT_BY_WORKTYPE_PERIOD, KrcdtAnnleaMngTemp.class)
				.setParameter("employeeId", employeeId)
				.setParameter("workTypeCode", workTypeCode)
				.setParameter("startYmd", period.start())
				.setParameter("endYmd", period.end())
				.getList(c -> c.toDomain());
	}

	@Override
	public List<TempAnnualLeaveManagement> findByEmployeeID(String employeeID) {
		return this.queryProxy().query(SELECT_BY_EMPLOYEEID, KrcdtAnnleaMngTemp.class)
				.setParameter("employeeID", employeeID)
				.getList(c -> c.toDomain());
	}
}
