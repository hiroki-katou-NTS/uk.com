package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.compensatoryholiday.updateremainnum;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataDaysAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataHours;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.UnOffSetOfDayOff;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.UnUserOfBreak;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author HungTT - <<Work>> 代休残数更新
 *
 */

@Stateless
public class RemainCompensatoryHolidayUpdating {

	@Inject
	private LeaveManaDataRepository leaveMngRepo;

	@Inject
	private ComDayOffManaDataRepository compensatoryDataRepo;


	// 代休残数更新
	public void updateRemainCompensatoryHoliday(List<BreakDayOffDetail> lstDetailData,AggrPeriodEachActualClosure period, String empId) {
		String companyId = AppContexts.user().companyId();
		this.deleteLeaveManaData(period.getPeriod(), empId);
		this.updateLeaveMngData(companyId, lstDetailData);
		this.deleteDayOffManaData(period.getPeriod(), empId);
		this.updateCompensatoryDayData(companyId, lstDetailData);
	}
	
	// ドメインモデル 「休出管理データ」 を削除する
	public void deleteLeaveManaData(DatePeriod period, String empId){
		List<LeaveManagementData> managementDatas = leaveMngRepo.getByHoliday(empId, false, period);
		if (CollectionUtil.isEmpty(managementDatas))
			return;
		List<String> leaveManaId = managementDatas.stream().map(r -> r.getID()).collect(Collectors.toList());
		leaveMngRepo.deleteById(leaveManaId);
	}
	
	// ドメインモデル 「代休管理データ」 を削除する
	public void deleteDayOffManaData(DatePeriod period, String empId){
		List<CompensatoryDayOffManaData> dayOffDatas = compensatoryDataRepo.getByHoliday(empId, false, period);
		if (CollectionUtil.isEmpty(dayOffDatas))
			return;
		List<String> dayOffId = dayOffDatas.stream().map(r -> r.getComDayOffID()).collect(Collectors.toList());
		compensatoryDataRepo.deleteById(dayOffId);
	}

	// 休出管理データの更新
	private void updateLeaveMngData(String companyId, List<BreakDayOffDetail> lstDetailData) {
		if (CollectionUtil.isEmpty(lstDetailData))
			return;
		lstDetailData = lstDetailData.stream().filter(a -> a.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE)
				.collect(Collectors.toList());
		if (CollectionUtil.isEmpty(lstDetailData))
			return;
		for (BreakDayOffDetail data : lstDetailData) {
			Optional<UnUserOfBreak> optUnUserOfBreak = data.getUnUserOfBreak();
			if (!optUnUserOfBreak.isPresent())
				continue;
			UnUserOfBreak unUserOfBreak = optUnUserOfBreak.get();
			Optional<LeaveManagementData> optLeaveMngData = leaveMngRepo.getByLeaveId(unUserOfBreak.getBreakId());
			if (optLeaveMngData.isPresent()) {
				// update
				LeaveManagementData leaveMng = new LeaveManagementData(optLeaveMngData.get().getID(),
						optLeaveMngData.get().getCID(), optLeaveMngData.get().getSID(),
						optLeaveMngData.get().getComDayOffDate(), unUserOfBreak.getExpirationDate(),
						new ManagementDataDaysAtr(unUserOfBreak.getOccurrenceDays()),
						new ManagementDataHours(unUserOfBreak.getOccurrenceTimes()),
						new ManagementDataRemainUnit(unUserOfBreak.getUnUsedDays()),
						new ManagementDataHours(unUserOfBreak.getUnUsedTimes()), unUserOfBreak.getDigestionAtr(),
						new AttendanceTime(unUserOfBreak.getOnedayTime()),
						new AttendanceTime(unUserOfBreak.getHaftDayTime()), unUserOfBreak.getDisappearanceDate());
				leaveMngRepo.update(leaveMng);
			} else {
				// insert
				LeaveManagementData leaveMng = new LeaveManagementData(unUserOfBreak.getBreakId(), companyId,
						data.getSid(), data.getYmdData(), unUserOfBreak.getExpirationDate(),
						new ManagementDataDaysAtr(unUserOfBreak.getOccurrenceDays()),
						new ManagementDataHours(unUserOfBreak.getOccurrenceTimes()),
						new ManagementDataRemainUnit(unUserOfBreak.getUnUsedDays()),
						new ManagementDataHours(unUserOfBreak.getUnUsedTimes()), unUserOfBreak.getDigestionAtr(),
						new AttendanceTime(unUserOfBreak.getOnedayTime()),
						new AttendanceTime(unUserOfBreak.getHaftDayTime()), unUserOfBreak.getDisappearanceDate());
				leaveMngRepo.create(leaveMng);
			}
		}
	}

	// 代休管理データの更新
	private void updateCompensatoryDayData(String companyId, List<BreakDayOffDetail> lstDetailData) {
		if (CollectionUtil.isEmpty(lstDetailData))
			return;
		lstDetailData = lstDetailData.stream().filter(a -> a.getOccurrentClass() == OccurrenceDigClass.DIGESTION)
				.collect(Collectors.toList());
		if (CollectionUtil.isEmpty(lstDetailData))
			return;
		for (BreakDayOffDetail data : lstDetailData) {
			Optional<UnOffSetOfDayOff> optUnOffsetOfDayoff = data.getUnOffsetOfDayoff();
			if (!optUnOffsetOfDayoff.isPresent())
				continue;
			UnOffSetOfDayOff unOffsetOfDayoff = optUnOffsetOfDayoff.get();
			Optional<CompensatoryDayOffManaData> optCompensatoryData = compensatoryDataRepo
					.getBycomdayOffId(unOffsetOfDayoff.getDayOffId());
			if (optCompensatoryData.isPresent()) {
				// update
				CompensatoryDayOffManaData compDayOffData = new CompensatoryDayOffManaData(
						optCompensatoryData.get().getComDayOffID(), optCompensatoryData.get().getSID(),
						optCompensatoryData.get().getCID(), optCompensatoryData.get().getDayOffDate(),
						new ManagementDataDaysAtr(unOffsetOfDayoff.getRequiredDay()),
						new ManagementDataHours(unOffsetOfDayoff.getRequiredTime()),
						new ManagementDataRemainUnit(unOffsetOfDayoff.getUnOffsetDay()),
						new ManagementDataHours(unOffsetOfDayoff.getUnOffsetTimes()));
				compensatoryDataRepo.update(compDayOffData);
			} else {
				// insert
				CompensatoryDayOffManaData compDayOffData = new CompensatoryDayOffManaData(
						unOffsetOfDayoff.getDayOffId(), data.getSid(), companyId, data.getYmdData(),
						new ManagementDataDaysAtr(unOffsetOfDayoff.getRequiredDay()),
						new ManagementDataHours(unOffsetOfDayoff.getRequiredTime()),
						new ManagementDataRemainUnit(unOffsetOfDayoff.getUnOffsetDay()),
						new ManagementDataHours(unOffsetOfDayoff.getUnOffsetTimes()));
				compensatoryDataRepo.create(compDayOffData);
			}
		}
	}

}
