package nts.uk.ctx.at.record.infra.repository.breakorgoout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.OutingFrameNo;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.OutingTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.record.infra.entity.breakorgoout.KrcdtDaiOutingTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class JpaOutingTimeOfDailyPerformanceRepository extends JpaRepository
		implements OutingTimeOfDailyPerformanceRepository {

	private static final String REMOVE_BY_EMPLOYEE;
	
	private static final String DEL_BY_LIST_KEY;
	
	private static final String SELECT_BY_EMPLOYEE_AND_DATE;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDaiOutingTime a ");
		builderString.append("WHERE a.krcdtDaiOutingTimePK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiOutingTimePK.ymd = :ymd ");
		REMOVE_BY_EMPLOYEE = builderString.toString();
		
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDaiOutingTime a ");
		builderString.append("WHERE a.krcdtDaiOutingTimePK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiOutingTimePK.ymd = :ymd ");
		SELECT_BY_EMPLOYEE_AND_DATE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDaiOutingTime a ");
		builderString.append("WHERE WHERE a.krcdtDaiOutingTimePK.employeeId IN :employeeIds ");
		builderString.append("AND a.krcdtDaiOutingTimePK.ymd IN :ymds ");
		DEL_BY_LIST_KEY = builderString.toString();
	}

	@Override
	public void delete(String employeeId, GeneralDate ymd) {
		this.getEntityManager().createQuery(REMOVE_BY_EMPLOYEE).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).executeUpdate();
	}

	@Override
	public void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds) {
		this.getEntityManager().createQuery(DEL_BY_LIST_KEY).setParameter("employeeIds", employeeIds)
		.setParameter("ymds", ymds).executeUpdate();	
	}

	@Override
	public Optional<OutingTimeOfDailyPerformance> findByEmployeeIdAndDate(String employeeId, GeneralDate ymd) {
		List<KrcdtDaiOutingTime> lstKrcdtDaiOutingTime = this.queryProxy().query(SELECT_BY_EMPLOYEE_AND_DATE,KrcdtDaiOutingTime.class).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).getList();
		 if(lstKrcdtDaiOutingTime==null || lstKrcdtDaiOutingTime.isEmpty()){
			 return Optional.empty();
		 }
		 List<OutingTimeSheet> lstOutingTimeSheet = new ArrayList<OutingTimeSheet>();
		 
		 lstKrcdtDaiOutingTime.forEach(x->{
			 WorkStamp outStamp  = new WorkStamp(new TimeWithDayAttr(x.outStampRoundingTimeDay.intValue()),new TimeWithDayAttr(x.outStampTime.intValue()),new WorkLocationCD(x.outStampPlaceCode) , EnumAdaptor.valueOf(x.outStampSourceInfo.intValue(), StampSourceInfo.class));
			 WorkStamp outActualStamp = new WorkStamp(new TimeWithDayAttr(x.outActualRoundingTimeDay.intValue()),new TimeWithDayAttr(x.outActualTime.intValue()),new WorkLocationCD(x.outActualPlaceCode) , EnumAdaptor.valueOf(x.outActualSourceInfo.intValue(), StampSourceInfo.class));
			 TimeActualStamp goOut = new TimeActualStamp(outActualStamp, outStamp, x.outNumberStamp.intValue());
			 WorkStamp backStamp  = new WorkStamp(new TimeWithDayAttr(x.backStampRoundingTimeDay.intValue()),new TimeWithDayAttr(x.backStampTime.intValue()),new WorkLocationCD(x.backStampPlaceCode) , EnumAdaptor.valueOf(x.backStampSourceInfo.intValue(), StampSourceInfo.class));
			 WorkStamp backActualStamp = new WorkStamp(new TimeWithDayAttr(x.backActualRoundingTimeDay.intValue()),new TimeWithDayAttr(x.backActualTime.intValue()),new WorkLocationCD(x.backActualPlaceCode) , EnumAdaptor.valueOf(x.backActualSourceInfo.intValue(), StampSourceInfo.class));
			 TimeActualStamp comeBack = new TimeActualStamp(backActualStamp, backStamp, x.backNumberStamp.intValue());
			 GoingOutReason reasonForGoOut = EnumAdaptor.valueOf(x.outingReason.intValue(), GoingOutReason.class);
			 AttendanceTime outingTimeCalculation = new AttendanceTime(x.outingTimeCalculation.intValue());
			 AttendanceTime outingTime = new AttendanceTime(x.outingTime.intValue());
			 OutingTimeSheet outingTimeSheet = new OutingTimeSheet(new OutingFrameNo(x.krcdtDaiOutingTimePK.outingFrameNo),goOut,outingTimeCalculation, outingTime, reasonForGoOut , comeBack);
			 lstOutingTimeSheet.add(outingTimeSheet);
		 });
		 return Optional.ofNullable(new OutingTimeOfDailyPerformance(employeeId, ymd, lstOutingTimeSheet));
	}
	
	

}
