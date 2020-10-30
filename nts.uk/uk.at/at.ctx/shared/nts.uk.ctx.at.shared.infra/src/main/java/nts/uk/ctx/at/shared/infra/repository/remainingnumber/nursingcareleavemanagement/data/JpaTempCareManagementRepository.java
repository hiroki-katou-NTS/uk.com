package nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.data;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagementRepository;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.care.interimdata.KrcdtInterimCareData;

/**
 * リポジトリ実装：暫定介護管理データ
 * @author yuri_tamakoshi
 */
@Stateless
public class JpaTempCareManagementRepository extends JpaRepository implements TempCareManagementRepository{

	private static final String SELECT_BY_PERIOD = "SELECT a FROM KrcdtInterimCareData a "
			+ "WHERE a.sId = :employeeId "
			+ "AND a.ymd >= :startYmd "
			+ "AND a.ymd <= :endYmd "
			+ "ORDER BY a.ymd ";

	private static final String SELECT_BY_EMPLOYEEID_YMD = "SELECT a FROM KrcdtInterimCareData a"
			+ " WHERE a.sId = :employeeID"
			+ "AND a.ymd =  : ymd "
			+ " ORDER BY a.ymd ASC";


	/** 検索 */
	@Override
	public List<TempCareManagement> find(String employeeId, GeneralDate ymd){

		return this.queryProxy().query(SELECT_BY_EMPLOYEEID_YMD, KrcdtInterimCareData.class)
				.setParameter("employeeId", employeeId)
				.setParameter("ymd",ymd)
				.getList(c -> c.toDomain());
	}

	/** 検索　（期間） */
	@Override
	public List<TempCareManagement> findByPeriodOrderByYmd(String employeeId, DatePeriod period) {

		return this.queryProxy().query(SELECT_BY_PERIOD, KrcdtInterimCareData.class)
				.setParameter("employeeId", employeeId)
				.setParameter("startYmd", period.start())
				.setParameter("endYmd", period.end())
				.getList(c -> c.toDomain());
	}


	/** 登録および更新 */
	@Override
	public void persistAndUpdate(TempCareManagement domain) {

		val key = domain.getRemainManaID();

		// 登録・更新
		KrcdtInterimCareData entity = this.getEntityManager().find(KrcdtInterimCareData.class, key);
		if (entity == null){
			entity = new KrcdtInterimCareData();
			entity.fromDomainForPersist(domain);
			this.getEntityManager().persist(entity);
		}
		else {
			entity.fromDomainForUpdate(domain);
		}
	}


	/** 削除 */
	@Override
	public void remove(String employeeId, GeneralDate ymd, TempCareManagement domain) {

		val key = domain.getRemainManaID();

		this.commandProxy().remove(KrcdtInterimCareData.class, key);
	}

	/**
	 * 暫定子の看護管理データの取得
	 * @param 社員ID employeeId
	 * @param 期間 period
	 */
	@Override
	public List<TempCareManagement> findBySidPeriod(String employeeId, DatePeriod period){

		return queryProxy().query(SELECT_BY_PERIOD, KrcdtInterimCareData.class)
				.setParameter("employeeId", employeeId)
				.setParameter("startYmd", period.start())
				.setParameter("endYmd", period.end())
				.getList(c -> c.toDomain());
	}
}
