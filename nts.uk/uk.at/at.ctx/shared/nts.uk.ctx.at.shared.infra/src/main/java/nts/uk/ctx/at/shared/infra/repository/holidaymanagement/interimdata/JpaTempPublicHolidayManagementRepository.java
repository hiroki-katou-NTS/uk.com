package nts.uk.ctx.at.shared.infra.repository.holidaymanagement.interimdata;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.interimdata.TempPublicHolidayManagement;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.interimdata.TempPublicHolidayManagementRepository;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.interimdata.KshdtInterimHdpub;

public class JpaTempPublicHolidayManagementRepository  extends JpaRepository implements TempPublicHolidayManagementRepository{

	private static final String SELECT_BY_EMPLOYEEID_YMD = "SELECT a FROM KshdtInterimHdpub a"
			+ " WHERE a.pk.sID = :employeeID"
			+ "AND a.pk.ymd =  : ymd "
			+ " ORDER BY a.pk.ymd ASC";
	
	private static final String SELECT_BY_PERIOD = "SELECT a FROM KshdtInterimHdpub a "
			+ "WHERE a.pk.sID = :employeeId "
			+ "AND a.pk.ymd >= :startYmd "
			+ "AND a.pk.ymd <= :endYmd "
			+ "ORDER BY a.pk.ymd ";
	
	private static final String REMOVE_BY_SID_PERIOD = "DELETE FROM KshdtInterimHdpub a"
			+ " WHERE a.pk.sID = :sid"
			+ "AND a.pk.ymd >= :startYmd "
			+ "AND a.pk.ymd <= :endYmd ";
	
	/** 検索 */
	@Override
	public List<TempPublicHolidayManagement> find(String employeeId, GeneralDate ymd){

		return this.queryProxy().query(SELECT_BY_EMPLOYEEID_YMD, KshdtInterimHdpub.class)
				.setParameter("employeeID", employeeId)
				.setParameter("ymd",ymd)
				.getList(c -> c.toDomain());
	}
	
	/** 検索　（期間） */
	@Override
	public List<TempPublicHolidayManagement> findByPeriodOrderByYmd(String employeeId, DatePeriod period) {

		return this.queryProxy().query(SELECT_BY_PERIOD, KshdtInterimHdpub.class)
				.setParameter("employeeId", employeeId)
				.setParameter("startYmd", period.start())
				.setParameter("endYmd", period.end())
				.getList(c -> c.toDomain());
	}
	
	/** 削除 （期間） */
	public void deleteByPeriod(String employeeId, DatePeriod period){
		 this.queryProxy().query(REMOVE_BY_SID_PERIOD, KshdtInterimHdpub.class)
				.setParameter("employeeId", employeeId)
				.setParameter("startYmd", period.start())
				.setParameter("endYmd", period.end());
	}
	
	
}
