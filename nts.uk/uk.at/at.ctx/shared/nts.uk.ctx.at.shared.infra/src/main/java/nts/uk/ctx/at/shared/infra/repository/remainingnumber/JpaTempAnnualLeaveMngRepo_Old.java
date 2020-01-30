package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngRepository_Old;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea.KrcdtAnnleaMngTemp;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea.KrcdtAnnleaMngTempPK;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 繝ｪ繝昴ず繝医Μ螳溯｣��ｼ壽圻螳壼ｹｴ莨醍ｮ｡逅�繝�繝ｼ繧ｿ
 * @author shuichu_ishida
 */
@Stateless
public class JpaTempAnnualLeaveMngRepo_Old extends JpaRepository implements TempAnnualLeaveMngRepository_Old {

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
	
	/** 讀懃ｴ｢ */
	@Override
	public Optional<TempAnnualLeaveManagement> find(String employeeId, GeneralDate ymd) {
		
		return this.queryProxy()
				.find(new KrcdtAnnleaMngTempPK(employeeId, ymd), KrcdtAnnleaMngTemp.class)
				.map(c -> c.toDomain());
	}
	
	/** 讀懃ｴ｢縲��ｼ域悄髢難ｼ� */
	@Override
	public List<TempAnnualLeaveManagement> findByPeriodOrderByYmd(String employeeId, DatePeriod period) {

		return this.queryProxy().query(SELECT_BY_PERIOD, KrcdtAnnleaMngTemp.class)
				.setParameter("employeeId", employeeId)
				.setParameter("startYmd", period.start())
				.setParameter("endYmd", period.end())
				.getList(c -> c.toDomain());
	}

	/** 逋ｻ骭ｲ縺翫ｈ縺ｳ譖ｴ譁ｰ */
	@Override
	public void persistAndUpdate(TempAnnualLeaveManagement domain) {

		// 繧ｭ繝ｼ
		val key = new KrcdtAnnleaMngTempPK(domain.getEmployeeId(), domain.getYmd());
		
		// 逋ｻ骭ｲ繝ｻ譖ｴ譁ｰ
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
	
	/** 蜑企勁 */
	@Override
	public void remove(String employeeId, GeneralDate ymd) {

		this.commandProxy().remove(KrcdtAnnleaMngTemp.class, new KrcdtAnnleaMngTempPK(employeeId, ymd));
	}
	
	/** 蜑企勁縲��ｼ域悄髢難ｼ� */
	@Override
	public void removeByPeriod(String employeeId, DatePeriod period) {
		
		this.getEntityManager().createQuery(DELETE_BY_PERIOD)
				.setParameter("employeeId", employeeId)
				.setParameter("startYmd", period.start())
				.setParameter("endYmd", period.end())
				.executeUpdate();
	}
	
	/** 蜑企勁縲��ｼ亥渕貅匁律莉･蜑搾ｼ� */
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
