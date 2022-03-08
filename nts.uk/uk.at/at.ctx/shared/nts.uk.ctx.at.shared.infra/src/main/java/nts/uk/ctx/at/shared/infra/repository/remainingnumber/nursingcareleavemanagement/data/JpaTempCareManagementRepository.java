package nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.data;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagementRepository;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.care.interimdata.KshdtInterimCareData;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.care.interimdata.KshdtInterimCareDataPK;
import nts.uk.shr.com.context.AppContexts;

/**
 * リポジトリ実装：暫定介護管理データ
 * @author yuri_tamakoshi
 */
@Stateless
public class JpaTempCareManagementRepository extends JpaRepository implements TempCareManagementRepository {

	private static final String SELECT_BY_PERIOD = "SELECT a FROM KshdtInterimCareData a "
			+ "WHERE a.pk.sid = :employeeId "
			+ "AND a.pk.ymd >= :startYmd "
			+ "AND a.pk.ymd <= :endYmd "
			+ "ORDER BY a.pk.ymd ";

	private static final String SELECT_BY_EMPLOYEEID_YMD = "SELECT a FROM KshdtInterimCareData a"
			+ " WHERE a.pk.sid = :employeeID"
			+ "AND a.pk.ymd =  : ymd "
			+ " ORDER BY a.pk.ymd ASC";

	private static final String DELETE_BY_SID_YMD = "DELETE FROM KshdtInterimCareData a"
			+ " WHERE a.pk.sid = :sid"
			+ "	AND a.pk.ymd =  :ymd ";
	
	private static final String DELETE_BY_SID_PERIOD = "DELETE FROM KshdtInterimCareData a"
			+ " WHERE a.pk.sid = :sid"
			+ " AND a.pk.ymd >= :startYmd "
			+ " AND a.pk.ymd <= :endYmd ";
	
	private static final String DELETE_BY_SID_BEFORETHEYMD = "DELETE FROM KshdtInterimCareData a"
			+ " WHERE a.pk.sid = :sid"
			+ " AND a.pk.ymd <= :ymd ";


	/** 検索 */
	@Override
	public List<TempCareManagement> find(String employeeId, GeneralDate ymd){

		return this.queryProxy().query(SELECT_BY_EMPLOYEEID_YMD, KshdtInterimCareData.class)
				.setParameter("employeeId", employeeId)
				.setParameter("ymd",ymd)
				.getList(c -> c.toDomain());
	}

	/** 検索　（期間） */
	@Override
	public List<TempCareManagement> findByPeriodOrderByYmd(String employeeId, DatePeriod period) {

		return this.queryProxy().query(SELECT_BY_PERIOD, KshdtInterimCareData.class)
				.setParameter("employeeId", employeeId)
				.setParameter("startYmd", period.start())
				.setParameter("endYmd", period.end())
				.getList(c -> c.toDomain());
	}


	/** 登録および更新 */
	@Override
	public void persistAndUpdate(TempCareManagement domain) {

		KshdtInterimCareDataPK pk = new KshdtInterimCareDataPK(
				AppContexts.user().companyId(),
				domain.getSID(),
				domain.getYmd(),
				domain.getAppTimeType().map(x -> x.isHourlyTimeType() ? 1 : 0).orElse(0),
				domain.getAppTimeType().flatMap(c -> c.getAppTimeType()).map(c -> c.value + 1).orElse(0));

		// 登録・更新
		Optional<KshdtInterimCareData> entityOpt = this.queryProxy().find(pk, KshdtInterimCareData.class);

		if (entityOpt.isPresent()) {
			entityOpt.get().fromDomainForUpdate(domain);
			this.commandProxy().update(entityOpt.get());
			this.getEntityManager().flush();
			return;
		}

		KshdtInterimCareData entity = new KshdtInterimCareData();
		entity.pk = pk;
		entity.fromDomainForPersist(domain);
		this.commandProxy().insert(entity);
		this.getEntityManager().flush();
	}


	/** 削除 */
	@Override
	public void remove(TempCareManagement domain) {

		val key = domain.getRemainManaID();

		this.commandProxy().remove(KshdtInterimCareData.class, key);
	}



	@Override
	public void deleteBySidAndYmd(String sid, GeneralDate ymd) {
		this.getEntityManager().createQuery(DELETE_BY_SID_YMD)
		.setParameter("sid", sid)
		.setParameter("ymd", ymd)
		.executeUpdate();
	}
	
	/*
	 * (非 Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagementRepository#deleteBySidDatePeriod(java.lang.String, nts.arc.time.calendar.period.DatePeriod)
	 */
	@Override
	public void deleteBySidDatePeriod(String sid, DatePeriod period){
		this.getEntityManager().createQuery(DELETE_BY_SID_PERIOD)
		.setParameter("sid", sid)
		.setParameter("startYmd", period.start())
		.setParameter("endYmd", period.end())
		.executeUpdate();
	}

	@Override
	public void deleteBySidBeforeTheYmd(String sid, GeneralDate ymd) {
		this.getEntityManager().createQuery(DELETE_BY_SID_BEFORETHEYMD)
		.setParameter("sid", sid)
		.setParameter("ymd", ymd)
		.executeUpdate();
		
	}

}
