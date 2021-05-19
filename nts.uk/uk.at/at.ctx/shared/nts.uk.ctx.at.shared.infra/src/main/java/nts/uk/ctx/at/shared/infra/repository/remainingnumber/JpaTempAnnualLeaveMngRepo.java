package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea.KrcdtAnnleaMngTemp;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea.KrcdtAnnleaMngTempPK;

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
	public Optional<TempAnnualLeaveMngs> find(String employeeId, GeneralDate ymd) {

		return this.queryProxy()
				.find(new KrcdtAnnleaMngTempPK(employeeId, ymd), KrcdtAnnleaMngTemp.class)
				.map(c -> c.toDomain());
	}

	/** 検索　（期間） */
	@Override
	public List<TempAnnualLeaveMngs> findByPeriodOrderByYmd(String employeeId, DatePeriod period) {

		return this.queryProxy().query(SELECT_BY_PERIOD, KrcdtAnnleaMngTemp.class)
				.setParameter("employeeId", employeeId)
				.setParameter("startYmd", period.start())
				.setParameter("endYmd", period.end())
				.getList(c -> c.toDomain());
	}

	/** 登録および更新 */
	@Override
	public void persistAndUpdate(TempAnnualLeaveMngs domain) {

		// キー
		val key = new KrcdtAnnleaMngTempPK(domain.getSID(), domain.getYmd());

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
	public List<TempAnnualLeaveMngs> findBySidWorkTypePeriod(String employeeId, String workTypeCode,
			DatePeriod period) {
		return this.queryProxy().query(SELECT_BY_WORKTYPE_PERIOD, KrcdtAnnleaMngTemp.class)
				.setParameter("employeeId", employeeId)
				.setParameter("workTypeCode", workTypeCode)
				.setParameter("startYmd", period.start())
				.setParameter("endYmd", period.end())
				.getList(c -> c.toDomain());
	}

	@Override
	public List<TempAnnualLeaveMngs> findByEmployeeID(String employeeID) {
		return this.queryProxy().query(SELECT_BY_EMPLOYEEID, KrcdtAnnleaMngTemp.class)
				.setParameter("employeeID", employeeID)
				.getList(c -> c.toDomain());
	}
}
