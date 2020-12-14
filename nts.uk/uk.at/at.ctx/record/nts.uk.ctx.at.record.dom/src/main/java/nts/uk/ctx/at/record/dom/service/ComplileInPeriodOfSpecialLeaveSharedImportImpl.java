package nts.uk.ctx.at.record.dom.service;

import java.util.List;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.ComplileInPeriodOfSpecialLeaveParam;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.InPeriodOfSpecialLeaveResultInfor;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export.SpecialLeaveManagementService;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveError;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.ComplileInPeriodOfSpecialLeaveSharedImport;
//import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RequireM5;

public class ComplileInPeriodOfSpecialLeaveSharedImportImpl implements ComplileInPeriodOfSpecialLeaveSharedImport{

	// 要修正 jinno
//	@Override
//	public List<SpecialLeaveError> complileInPeriodOfSpecialLeave(RequireM5 require, CacheCarrier cacheCarrier,
//			String cid, String sid, DatePeriod complileDate, boolean mode, GeneralDate baseDate, int specialLeaveCode,
//			boolean mngAtr, boolean overwriteFlg, List<InterimRemain> remainData,
//			List<InterimSpecialHolidayMng> interimSpecialData) {
//
//		ComplileInPeriodOfSpecialLeaveParam speParam = new ComplileInPeriodOfSpecialLeaveParam(
//				cid, sid, complileDate, mode,baseDate,specialLeaveCode, mngAtr, overwriteFlg, remainData,
//				interimSpecialData);
//
//		InPeriodOfSpecialLeaveResultInfor speOutCheck = SpecialLeaveManagementService
//				.complileInPeriodOfSpecialLeave(require, cacheCarrier, speParam).getAggSpecialLeaveResult();
//
//		return speOutCheck.getSpecialLeaveErrors();
//	}

}
