package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class SpecialLeaveManagementNotMinusServiceImpl implements SpecialLeaveManagementNotMinusService{
	@Inject
	private SpecialLeaveManagementService mngSevice;
	@Override
	public InPeriodOfSpecialLeave complileInPeriodOfSpecialLeaveNotMinus(ComplileInPeriodOfSpecialLeaveParam param) {
		InPeriodOfSpecialLeave processResult = mngSevice.complileInPeriodOfSpecialLeave(param);
		//○マイナスなしの残数・使用数を計算
		RemainDaysOfSpecialHoliday remainDayNotMinus = this.remainDaysNotMinus(processResult.getRemainDays());
		SpecialHolidayRemainInfor beforeInfor = processResult.getRemainDays().getGrantDetailBefore();
		beforeInfor.setRemainDays(remainDayNotMinus.getGrantDetailBefore().getRemainDays());
		beforeInfor.setUseDays(remainDayNotMinus.getGrantDetailBefore().getUseDays());
		processResult.getRemainDays().setGrantDetailBefore(beforeInfor);
		Optional<SpecialHolidayRemainInfor> afterInfor = processResult.getRemainDays().getGrantDetailAfter();
		afterInfor.ifPresent(x -> {
			x.setRemainDays(remainDayNotMinus.getGrantDetailAfter().get().getRemainDays());
			x.setUseDays(remainDayNotMinus.getGrantDetailAfter().get().getUseDays());
			processResult.getRemainDays().setGrantDetailAfter(Optional.ofNullable(x));
		});
		
		
		return processResult;
	}
	@Override
	public RemainDaysOfSpecialHoliday remainDaysNotMinus(RemainDaysOfSpecialHoliday remainDaysResult) {
		// マイナス分を削除した残数・使用数を計算
		SpecialHolidayRemainInfor deleteMinusRemainBefore = this.deleteMinusData(remainDaysResult.getGrantDetailBefore());
		//○マイナス分を削除した残数・使用数を計算
		SpecialHolidayRemainInfor deleteMinusRemainAfter = null;
		if(remainDaysResult.getGrantDetailAfter().isPresent()) {
			deleteMinusRemainAfter = this.deleteMinusData(remainDaysResult.getGrantDetailAfter().get());	
		}
		return new RemainDaysOfSpecialHoliday(deleteMinusRemainBefore, 0.0, Optional.ofNullable(deleteMinusRemainAfter));
	}
	/**
	 * マイナス分を削除した残数・使用数を計算
	 * @param remainInfor
	 * @return
	 */
	private SpecialHolidayRemainInfor deleteMinusData(SpecialHolidayRemainInfor remainInfor) {
		if(remainInfor.getRemainDays() >= 0) {
			return remainInfor;
		}
		//残数*-1と使用数を比較
		if(remainInfor.getRemainDays()*(-1) <= remainInfor.getUseDays()) {
			remainInfor.setUseDays(remainInfor.getUseDays() + remainInfor.getRemainDays());
		} else {
			remainInfor.setUseDays(0);
		}
		remainInfor.setRemainDays(0);
		return remainInfor;
	}

}
