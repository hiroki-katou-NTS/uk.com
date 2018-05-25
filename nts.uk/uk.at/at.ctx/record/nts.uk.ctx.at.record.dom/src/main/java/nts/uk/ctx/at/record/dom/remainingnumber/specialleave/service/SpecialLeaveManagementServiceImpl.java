package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfoRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
@Stateless
public class SpecialLeaveManagementServiceImpl implements SpecialLeaveManagementService{
	@Inject
	private SpecialLeaveGrantRepository speLeaveRepo;
	@Inject
	private SpecialLeaveBasicInfoRepository leaveBasicInfoRepo;
	@Inject
	private SpecialHolidayRepository holidayRepo;
	@Override
	public InPeriodOfSpecialLeave complileInPeriodOfSpecialLeave(String cid, String sid, Period complileDate,
			boolean model, GeneralDate baseDate, int specialLeaveCode, NextMonthMngDataAtr mngAtr) {
		//管理データを取得する		
		
		return null;
	}

	@Override
	public Optional<SpecialLeaveGrantRemainingData> getMngData(String cid, String sid, int specialLeaveCode,
			Period complileDate) {
		//ドメインモデル「特別休暇付与残数データ」を取得する
		List<SpecialLeaveGrantRemainingData> lstDataSpe = speLeaveRepo.getByPeriodStatus(sid, specialLeaveCode, LeaveExpirationStatus.AVAILABLE,
				complileDate.getEndDate(), complileDate.getStartDate());
		//社員の特別休暇情報を取得する
		
		return null;
	}

	@Override
	public InforSpecialLeaveOfEmployee getInforSpecialLeaveOfEmployee(String cid, String sid, int specialLeaveCode,
			Period complileDate) {
		InforSpecialLeaveOfEmployee outputData = new InforSpecialLeaveOfEmployee(InforStatus.NOTUSE, 0, false, false);
		//ドメインモデル「特別休暇基本情報」を取得する
		Optional<SpecialLeaveBasicInfo> optBasicInfor = leaveBasicInfoRepo.getBySidLeaveCdUser(sid, specialLeaveCode, UseAtr.USE);
		if(!optBasicInfor.isPresent()) {
			return outputData;
		}
		//ドメインモデル「特別休暇」を取得する
		Optional<SpecialHoliday> optSpecialHoliday = holidayRepo.findByCidHolidayCd(cid, specialLeaveCode);
		if(!optSpecialHoliday.isPresent()) {
			return outputData;
		}
		SpecialHoliday specialHoliday = optSpecialHoliday.get();
		//対応するドメインモデル「定期付与」を取得する
		//TODO
		
		return null;
	}

	@Override
	public List<GeneralDate> askGrantDayOnTable(String sid, Period dateData, SpecialLeaveBasicInfo specialInfo) {
		//取得している「特別休暇基本情報．適用設定」をチェックする
		//TODO
		
		return null;
	}
	
}
