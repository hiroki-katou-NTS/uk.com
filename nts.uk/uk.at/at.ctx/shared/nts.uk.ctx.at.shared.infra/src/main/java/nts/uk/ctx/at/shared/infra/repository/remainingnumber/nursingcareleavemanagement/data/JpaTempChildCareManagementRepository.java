package nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.data;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareManagementRepository;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.childcare.interimdata.KrcdtInterimChildCare;

/**
 * リポジトリ実装：暫定子の看護管理データ
 * @author yuri_tamakoshi
 */
@Stateless
public class JpaTempChildCareManagementRepository extends JpaRepository implements TempChildCareManagementRepository{

	private static final String SELECT_BY_PERIOD = "SELECT a FROM KrcdtInterimChildCare a "
			+ "WHERE a.sID = :employeeId "
			+ "AND a.ymd >= :startYmd "
			+ "AND a.ymd <= :endYmd "
			+ "ORDER BY a.ymd ";

	private static final String SELECT_BY_EMPLOYEEID_YMD = "SELECT a FROM KrcdtInterimChildCare a"
			+ " WHERE a.sID = :employeeID"
			+ "AND a.ymd =  : ymd "
			+ " ORDER BY a.ymd ASC";


	/** 検索 */
	@Override
	public List<TempChildCareManagement> find(String employeeId, GeneralDate ymd){

		return this.queryProxy().query(SELECT_BY_EMPLOYEEID_YMD, KrcdtInterimChildCare.class)
				.setParameter("employeeId", employeeId)
				.setParameter("ymd",ymd)
				.getList(c -> c.toDomain());
	}

	/** 検索　（期間） */
	@Override
	public List<TempChildCareManagement> findByPeriodOrderByYmd(String employeeId, DatePeriod period) {

		return this.queryProxy().query(SELECT_BY_PERIOD, KrcdtInterimChildCare.class)
				.setParameter("employeeId", employeeId)
				.setParameter("startYmd", period.start())
				.setParameter("endYmd", period.end())
				.getList(c -> c.toDomain());
	}


	/** 登録および更新 */
	@Override
	public void persistAndUpdate(TempChildCareManagement domain) {

		val key = domain.getRemainManaID();

		// 登録・更新
		KrcdtInterimChildCare entity = this.getEntityManager().find(KrcdtInterimChildCare.class, key);
		if (entity == null){
			entity = new KrcdtInterimChildCare();
			entity.fromDomainForPersist(domain);
			this.getEntityManager().persist(entity);
		}
		else {
			entity.fromDomainForUpdate(domain);
		}
	}

	/** 削除 */
	@Override
	public void remove(String employeeId, GeneralDate ymd, TempChildCareManagement domain) {

		val key = domain.getRemainManaID();

		this.commandProxy().remove(KrcdtInterimChildCare.class, key);
	}

	/**
	 * 暫定子の看護管理データの取得
	 * @param 社員ID employeeId
	 * @param 期間 period
	 */
	@Override
	public List<TempChildCareManagement> findBySidPeriod(String employeeId, DatePeriod period){

		return queryProxy().query(SELECT_BY_PERIOD, KrcdtInterimChildCare.class)
				.setParameter("employeeId", employeeId)
				.setParameter("startYmd", period.start())
				.setParameter("endYmd", period.end())
				.getList(c -> c.toDomain());
	}
}
