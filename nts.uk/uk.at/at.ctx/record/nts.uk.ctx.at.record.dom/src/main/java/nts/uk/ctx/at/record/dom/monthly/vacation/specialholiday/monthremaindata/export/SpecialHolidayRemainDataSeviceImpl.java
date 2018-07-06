package nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.export;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialHolidayRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialHolidayRemainDataRepository;

@Stateless
public class SpecialHolidayRemainDataSeviceImpl implements SpecialHolidayRemainDataSevice{
	@Inject
	private SpecialHolidayRemainDataRepository speRemainDataRepo;
	@Override
	public List<SpecialHolidayRemainDataOutput> getSpeHoliOfConfirmedMonthly(String sid, YearMonth startMonth,
			YearMonth endMonth) {
		List<SpecialHolidayRemainDataOutput> lstOutData = new ArrayList<>();
		for (YearMonth month = startMonth; month.lessThanOrEqualTo(endMonth); month = month.addMonths(1)) {
			
			//ドメインモデル「特別休暇月別残数データ」を取得
			List<SpecialHolidayRemainData> lstRemainData = speRemainDataRepo.getByYmStatus(sid, month, ClosureStatus.PROCESSED);
			for (SpecialHolidayRemainData remainData : lstRemainData) {
				SpecialHolidayRemainDataOutput dataOut = new SpecialHolidayRemainDataOutput();
				List<SpecialHolidayRemainDataOutput> lstTmp = new ArrayList<>();
				for (SpecialHolidayRemainDataOutput tmpData : lstOutData) {
					if(tmpData.getSpecialHolidayCd() == remainData.getSpecialHolidayCd() && tmpData.getYm() == month && tmpData.getSid() == remainData.getSid()) {
						lstTmp.add(tmpData);
					}
				}
				if(lstTmp.isEmpty()) {
					dataOut.setSid(sid);
					dataOut.setYm(month);
					dataOut.setSpecialHolidayCd(remainData.getSpecialHolidayCd());
					dataOut.setUseDays(remainData.getSpecialLeave().getRemain().getDays().v());
					dataOut.setBeforeUseDays(remainData.getSpecialLeave().getBeforeRemainGrant().getDays().v());
					dataOut.setAfterUseDays(remainData.getSpecialLeave().getAfterRemainGrant().isPresent() ? remainData.getSpecialLeave().getAfterRemainGrant().get().getDays().v() : 0);
					dataOut.setUseTimes(remainData.getSpecialLeave().getUseNumber().getUseTimes().getUseTimes().v());
					dataOut.setBeforeUseTimes(remainData.getSpecialLeave().getUseNumber().getUseTimes().getBeforeUseGrantTimes().v());
					dataOut.setAfterUseTimes(remainData.getSpecialLeave().getUseNumber().getUseTimes().getAfterUseGrantTimes().isPresent() ? remainData.getSpecialLeave().getUseNumber().getUseTimes().getAfterUseGrantTimes().get().v() : 0 );
					dataOut.setUseNumber(remainData.getSpecialLeave().getUseNumber().getUseTimes().getUseNumber().v());
					dataOut.setFactUseDays(remainData.getActualSpecial().getUseNumber().getUseDays().getUseDays().v());
					dataOut.setFactUseNumber(remainData.getActualSpecial().getUseNumber().getUseTimes().getUseNumber().v());
					dataOut.setRemainDays(remainData.getSpecialLeave().getRemain().getDays().v());
					dataOut.setRemainTimes(remainData.getSpecialLeave().getRemain().getTime().isPresent() ? remainData.getSpecialLeave().getRemain().getTime().get().v() : 0);
					dataOut.setFactRemainDays(remainData.getActualSpecial().getRemain().getDays().v());
					dataOut.setFactRemainTimes(remainData.getActualSpecial().getRemain().getTime().isPresent() ? remainData.getActualSpecial().getRemain().getTime().get().v() : 0);
					dataOut.setBeforeRemainDays(remainData.getSpecialLeave().getBeforeRemainGrant().getDays().v());
					dataOut.setBeforeRemainTimes(remainData.getSpecialLeave().getBeforeRemainGrant().getTime().isPresent() ? remainData.getSpecialLeave().getBeforeRemainGrant().getTime().get().v() : 0);
					dataOut.setBeforeFactRemainDays(remainData.getActualSpecial().getBeforRemainGrant().getDays().v());
					dataOut.setBeforeFactRemainTimes(remainData.getActualSpecial().getBeforRemainGrant().getTime().isPresent() ? remainData.getActualSpecial().getBeforRemainGrant().getTime().get().v() : 0);
					dataOut.setAfterRemainDays(remainData.getSpecialLeave().getAfterRemainGrant().isPresent() ? remainData.getSpecialLeave().getAfterRemainGrant().get().getDays().v() : 0);
					dataOut.setAfterRemainTimes(remainData.getSpecialLeave().getAfterRemainGrant().isPresent() && remainData.getSpecialLeave().getAfterRemainGrant().get().getTime().isPresent() ? remainData.getSpecialLeave().getAfterRemainGrant().get().getTime().get().v() : 0);
					dataOut.setAfterFactRemainDays(remainData.getActualSpecial().getAfterRemainGrant().isPresent() ? remainData.getActualSpecial().getAfterRemainGrant().get().getDays().v() : 0);
					dataOut.setNotUseDays(remainData.getSpecialLeave().getUnDegestionNumber().getDays().v());
					dataOut.setNotUseTime(remainData.getSpecialLeave().getUnDegestionNumber().getTimes().isPresent() ? remainData.getSpecialLeave().getUnDegestionNumber().getTimes().get().v() : 0);
					dataOut.setGrantAtr(remainData.isGrantAtr() ? 1 : 0);
					dataOut.setGrantDays(remainData.getGrantDays().isPresent() ? remainData.getGrantDays().get().v() : 0);
					lstOutData.add(dataOut);
				} else {
					SpecialHolidayRemainDataOutput tmp = lstTmp.get(0);
					lstOutData.remove(tmp);
					tmp.setUseDays(tmp.getUseDays() + remainData.getSpecialLeave().getRemain().getDays().v());
					tmp.setBeforeUseDays(tmp.getBeforeUseDays() + remainData.getSpecialLeave().getBeforeRemainGrant().getDays().v());
					tmp.setAfterUseDays(tmp.getAfterUseDays() + (remainData.getSpecialLeave().getAfterRemainGrant().isPresent() ? remainData.getSpecialLeave().getAfterRemainGrant().get().getDays().v() : 0));
					tmp.setUseTimes(tmp.getUseTimes() + remainData.getSpecialLeave().getUseNumber().getUseTimes().getUseTimes().v());
					tmp.setBeforeUseTimes(tmp.getBeforeUseTimes() + remainData.getSpecialLeave().getUseNumber().getUseTimes().getBeforeUseGrantTimes().v());
					tmp.setAfterUseTimes(tmp.getAfterUseTimes() + (remainData.getSpecialLeave().getUseNumber().getUseTimes().getAfterUseGrantTimes().isPresent() ? remainData.getSpecialLeave().getUseNumber().getUseTimes().getAfterUseGrantTimes().get().v() : 0 ));
					tmp.setUseNumber(tmp.getUseNumber() + remainData.getSpecialLeave().getUseNumber().getUseTimes().getUseNumber().v());
					tmp.setFactUseDays(tmp.getFactUseDays() + remainData.getActualSpecial().getUseNumber().getUseDays().getUseDays().v());
					tmp.setFactUseNumber(tmp.getFactUseNumber() + remainData.getActualSpecial().getUseNumber().getUseTimes().getUseNumber().v());
					tmp.setRemainDays(tmp.getRemainDays() + remainData.getSpecialLeave().getRemain().getDays().v());
					tmp.setRemainTimes(tmp.getRemainTimes() + (remainData.getSpecialLeave().getRemain().getTime().isPresent() ? remainData.getSpecialLeave().getRemain().getTime().get().v() : 0));
					tmp.setFactRemainDays(tmp.getFactRemainDays() + remainData.getActualSpecial().getRemain().getDays().v());
					tmp.setFactRemainTimes(tmp.getAfterFactRemainTimes() + (remainData.getActualSpecial().getRemain().getTime().isPresent() ? remainData.getActualSpecial().getRemain().getTime().get().v() : 0));
					tmp.setBeforeRemainDays(tmp.getBeforeRemainDays() + remainData.getSpecialLeave().getBeforeRemainGrant().getDays().v());
					tmp.setBeforeRemainTimes(tmp.getBeforeRemainTimes() + (remainData.getSpecialLeave().getBeforeRemainGrant().getTime().isPresent() ? remainData.getSpecialLeave().getBeforeRemainGrant().getTime().get().v() : 0));
					tmp.setBeforeFactRemainDays(tmp.getBeforeFactRemainDays() + remainData.getActualSpecial().getBeforRemainGrant().getDays().v());
					tmp.setBeforeFactRemainTimes(tmp.getBeforeFactRemainTimes() + (remainData.getActualSpecial().getBeforRemainGrant().getTime().isPresent() ? remainData.getActualSpecial().getBeforRemainGrant().getTime().get().v() : 0));
					tmp.setAfterRemainDays(tmp.getAfterRemainDays() + (remainData.getSpecialLeave().getAfterRemainGrant().isPresent() ? remainData.getSpecialLeave().getAfterRemainGrant().get().getDays().v() : 0));
					tmp.setAfterRemainTimes(tmp.getAfterRemainTimes() + (remainData.getSpecialLeave().getAfterRemainGrant().isPresent() && remainData.getSpecialLeave().getAfterRemainGrant().get().getTime().isPresent() ? remainData.getSpecialLeave().getAfterRemainGrant().get().getTime().get().v() : 0));
					tmp.setAfterFactRemainDays(tmp.getAfterFactRemainDays() + (remainData.getActualSpecial().getAfterRemainGrant().isPresent() ? remainData.getActualSpecial().getAfterRemainGrant().get().getDays().v() : 0));
					tmp.setNotUseDays(tmp.getNotUseDays() + remainData.getSpecialLeave().getUnDegestionNumber().getDays().v());
					tmp.setNotUseTime(tmp.getNotUseTime() + (remainData.getSpecialLeave().getUnDegestionNumber().getTimes().isPresent() ? remainData.getSpecialLeave().getUnDegestionNumber().getTimes().get().v() : 0));
					tmp.setGrantAtr(tmp.getGrantAtr() == 1 ? 1 : (remainData.isGrantAtr() ? 1 : 0));
					tmp.setGrantDays(tmp.getGrantDays() + (remainData.getGrantDays().isPresent() ? remainData.getGrantDays().get().v() : 0));
					lstOutData.add(tmp);
				}
				
			}
		}
		return lstOutData;
	}

}
