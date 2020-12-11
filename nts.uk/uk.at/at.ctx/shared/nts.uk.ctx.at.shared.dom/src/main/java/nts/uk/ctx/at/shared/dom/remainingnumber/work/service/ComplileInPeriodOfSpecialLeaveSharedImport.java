package nts.uk.ctx.at.shared.dom.remainingnumber.work.service;

import java.util.List;

import SpecialLeaveManagementService.RequireM5;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveError;

public interface ComplileInPeriodOfSpecialLeaveSharedImport {

	List<SpecialLeaveError> complileInPeriodOfSpecialLeave(
			RequireM5 require,
			CacheCarrier cacheCarrier,
			String cid,String sid,DatePeriod complileDate,boolean mode,GeneralDate baseDate,
			int specialLeaveCode,boolean mngAtr,boolean overwriteFlg,List<InterimRemain> remainData,
			List<InterimSpecialHolidayMng> interimSpecialData);
}
