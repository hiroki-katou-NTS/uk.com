package nts.uk.ctx.at.record.infra.repository.daily.ouen;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDaily;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDailyRepo;
import nts.uk.ctx.at.record.infra.entity.daily.ouen.KrcdtDayOuenTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.EngravingMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.TimeSheetOfAttendanceEachOuenSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.WorkContent;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record.WorkplaceOfWorkEachOuen;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkGroup;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class OuenWorkTimeSheetOfDailyRepoImpl extends JpaRepository implements OuenWorkTimeSheetOfDailyRepo {

	@Override
	public OuenWorkTimeSheetOfDaily find(String empId, GeneralDate ymd) {
		
		List<KrcdtDayOuenTimeSheet> entitis = queryProxy()
				.query("SELECT o FROM KrcdtDayOuenTimeSheet o WHERE o.pk.sid = :sid AND o.ok.ymd = :ymd", KrcdtDayOuenTimeSheet.class)
				.setParameter("sid", empId).setParameter("ymd", ymd).getList();
		
		if(entitis.isEmpty())
			return null;
		
		OuenWorkTimeSheetOfDaily rs = toDomain(entitis);
		
		return rs;
	}

	@Override
	public void update(List<OuenWorkTimeSheetOfDaily> domain) {
		domain.stream().map(c -> KrcdtDayOuenTimeSheet.convert(c)).forEach(e -> {
			commandProxy().update(e);
		});
	}

	@Override
	public void insert(List<OuenWorkTimeSheetOfDaily> domain) {
		domain.stream().map(c -> KrcdtDayOuenTimeSheet.convert(c)).forEach(e -> {
			commandProxy().insert(e);
		});
	}

	@Override
	public void delete(List<OuenWorkTimeSheetOfDaily> domain) {
		domain.stream().map(c -> KrcdtDayOuenTimeSheet.convert(c)).forEach(e -> {
			commandProxy().remove(e);
		});
	}
	
	public OuenWorkTimeSheetOfDaily toDomain(List<KrcdtDayOuenTimeSheet> es) {
		List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheet = es.stream().map(ots -> OuenWorkTimeSheetOfDailyAttendance.create(
				es.get(0).pk.ouenNo, 
				WorkContent.create(
						ots.cid, 
						WorkplaceOfWorkEachOuen.create(ots.workplaceId, new WorkLocationCD(ots.workLocationCode)), 
						Optional.ofNullable(ots.workCd1 == null ? null : 
							WorkGroup.create(ots.workCd1, ots.workCd2, ots.workCd3, ots.workCd4, ots.workCd5))), 
				TimeSheetOfAttendanceEachOuenSheet.create(
						new WorkNo(ots.workNo), 
						Optional.ofNullable(ots.startTime == null ? null : 
							new WorkTimeInformation(
									new ReasonTimeChange(
											EnumAdaptor.valueOf(ots.startTimeChangeWay, TimeChangeMeans.class), 
											ots.startStampMethod == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(ots.startStampMethod, EngravingMethod.class))), 
									ots.startTime == null ? null : new TimeWithDayAttr(ots.startTime))), 
						Optional.ofNullable(ots.endTime == null ? null : 
							new WorkTimeInformation(
									new ReasonTimeChange(
											EnumAdaptor.valueOf(ots.endTimeChangeWay, TimeChangeMeans.class), 
											ots.endStampMethod == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(ots.endStampMethod, EngravingMethod.class))), 
									ots.endTime == null ? null : new TimeWithDayAttr(ots.endTime)))))).collect(Collectors.toList());
		
		return OuenWorkTimeSheetOfDaily.create(es.get(0).pk.sid, es.get(0).pk.ymd, ouenTimeSheet);
	}

}
