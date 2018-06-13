package nts.uk.ctx.sys.shared.infra.repository;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.shared.dom.toppagealarm.TopPageAlarm;
import nts.uk.ctx.sys.shared.dom.toppagealarm.TopPageAlarmDetail;
import nts.uk.ctx.sys.shared.dom.toppagealarm.TopPageAlarmRepository;
import nts.uk.ctx.sys.shared.infra.entity.KrcstToppageAlarm;
import nts.uk.ctx.sys.shared.infra.entity.KrcstToppageAlarmDetail;
@Stateless
public class JpaTopPageAlarmRepository extends JpaRepository implements TopPageAlarmRepository{
	// toppage alarm table
	private final String SELECT_BYCOM = "SELECT c FROM KrcstToppageAlarm c WHERE c.companyId = :companyId ";
	private final String SELECT_BYEMP = SELECT_BYCOM + "AND c.managerId = :managerId ";
	private final String SELECT_UNROGER = SELECT_BYEMP + "AND c.rogerFlag = :rogerFlag ";
	private final String SELECT_BYDATE = SELECT_UNROGER + "AND c.finishDateTime >= :dateStart AND c.finishDateTime <= :dateEnd ";
	
	// toppage alarm detail table 
	private final String SELECT_BYLOGID = "SELECT c FROM KrcstToppageAlarmDetail c WHERE c.krcstToppageAlarmDetailPK.executionLogId = :executionLogId ";
	private final String SELECT_SORT = SELECT_BYLOGID + "ORDER BY c.krcstToppageAlarmDetailPK.serialNo, c.targerEmployee ASC";
	
	// convert from entity to toppage alarm domain
	private TopPageAlarm toDomain(KrcstToppageAlarm entity){
		return TopPageAlarm.createFromJavaType(entity.companyId, entity.executionLogId, 
												entity.managerId, entity.finishDateTime, 
												entity.executionContent, entity.existenceError, 
												entity.rogerFlag);
	}
	
	// convert from entity to toppage alarm detail domain
	private TopPageAlarmDetail toDomainDetail(KrcstToppageAlarmDetail entity){
		return TopPageAlarmDetail.createFromJavaType(entity.companyId, 
														entity.krcstToppageAlarmDetailPK.executionLogId, 
														entity.managerId, entity.finishDateTime, 
														entity.krcstToppageAlarmDetailPK.serialNo, 
														entity.errorMessage, entity.targerEmployee);
	}
	
	// find toppage alarm by companyId, employeeid and rogerFlag = 0 (have not read)
	@Override
	public List<TopPageAlarm> findToppage(String companyId, String managerId, int rogerFlag) {
		GeneralDateTime dateEnd = GeneralDateTime.now();
		String dateStart = dateEnd.addDays(-3).toString();
		return this.queryProxy().query(SELECT_BYDATE, KrcstToppageAlarm.class)
								.setParameter("companyId", companyId)
								.setParameter("managerId", managerId)
								.setParameter("rogerFlag", rogerFlag)
								.setParameter("dateStart", dateStart)
								.setParameter("dateEnd", dateEnd)
								.getList(c -> toDomain(c));
	}
	
	// find toppage alarm detail by executionLogId and processingName
	@Override
	public List<TopPageAlarmDetail> findDetail(String executionLogId) {
		return this.queryProxy().query(SELECT_SORT, KrcstToppageAlarmDetail.class)
								.setParameter("executionLogId", executionLogId)
								.getList(x -> toDomainDetail(x));
	}

}
