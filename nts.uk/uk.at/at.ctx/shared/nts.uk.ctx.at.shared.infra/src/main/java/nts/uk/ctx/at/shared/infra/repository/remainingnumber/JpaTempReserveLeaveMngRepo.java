package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.TempReserveLeaveManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.TempReserveLeaveMngRepository;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.resvlea.KrcdtRsvleaMngTemp;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.resvlea.KrcdtRsvleaMngTempPK;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * リポジトリ実裼�暫定積立年休管球�ータ
 * @author shuichu_ishida
 */
@Stateless
public class JpaTempReserveLeaveMngRepo extends JpaRepository implements TempReserveLeaveMngRepository {

	private static final String SELECT_BY_PERIOD = "SELECT a FROM KrcdtRsvleaMngTemp a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "AND a.PK.ymd >= :startYmd "
			+ "AND a.PK.ymd <= :endYmd "
			+ "ORDER BY a.PK.ymd ";

	private static final String DELETE_PAST_YMD = "DELETE FROM KrcdtRsvleaMngTemp a "
	private static final String SELECT_BY_EMPLOYEEID = "SELECT a FROM KrcdtRsvleaMngTemp a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "ORDER BY a.PK.ymd ";
	
	/** 検索 */
	@Override
	public Optional<TempReserveLeaveManagement> find(String employeeId, GeneralDate ymd) {
		
		return this.queryProxy()
				.find(new KrcdtRsvleaMngTempPK(employeeId, ymd), KrcdtRsvleaMngTemp.class)
				.map(c -> c.toDomain());
	}
	
	/** 検索　�期間�*/
	@Override
	public List<TempReserveLeaveManagement> findByPeriodOrderByYmd(String employeeId, DatePeriod period) {

		return this.queryProxy().query(SELECT_BY_PERIOD, KrcdtRsvleaMngTemp.class)
				.setParameter("employeeId", employeeId)
				.setParameter("startYmd", period.start())
				.setParameter("endYmd", period.end())
				.getList(c -> c.toDomain());
	}
	
	/** 登録および更新 */
	@Override
	public void persistAndUpdate(TempReserveLeaveManagement domain) {

		// キー
		val key = new KrcdtRsvleaMngTempPK(domain.getEmployeeId(), domain.getYmd());
		
		// 登録・更新
		KrcdtRsvleaMngTemp entity = this.getEntityManager().find(KrcdtRsvleaMngTemp.class, key);
		if (entity == null){
			entity = new KrcdtRsvleaMngTemp();
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

		this.commandProxy().remove(KrcdtRsvleaMngTemp.class, new KrcdtRsvleaMngTempPK(employeeId, ymd));
	}
	
	/** 削除　�基準日以前�*/
	@Override
	public void removePastYmd(String employeeId, GeneralDate criteriaDate) {
		
		this.getEntityManager().createQuery(DELETE_PAST_YMD)
				.setParameter("employeeId", employeeId)
				.setParameter("criteriaDate", criteriaDate)
				.executeUpdate();
	}
	
	@Override
	public void removeBetweenPeriod(String employeeId, DatePeriod period) {
		List<KrcdtRsvleaMngTemp> listEntity = this.queryProxy().query(SELECT_BY_PERIOD, KrcdtRsvleaMngTemp.class)
				.setParameter("employeeId", employeeId).setParameter("startYmd", period.start())
				.setParameter("endYmd", period.end()).getList();
		this.commandProxy().removeAll(listEntity);
	}

	@Override
	public List<TempReserveLeaveManagement> findByEmployeeId(String employeeId) {
		return this.queryProxy().query(SELECT_BY_EMPLOYEEID, KrcdtRsvleaMngTemp.class)
				.setParameter("employeeId", employeeId)
				.getList(c -> c.toDomain());
	}
}
