package nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.export;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialHolidayRemainData;

@Stateless
public class SpecialHolidayRemainDataSeviceImpl implements SpecialHolidayRemainDataSevice{

	@Override
	public List<SpecialHolidayRemainData> getSpeHoliOfConfirmedMonthly(String sid, YearMonth startMonth,
			YearMonth endMonth) {
		List<SpecialHolidayRemainData> lstOutData = new ArrayList<>();
		// TODO Auto-generated method stub
		return lstOutData;
	}

}
