package nts.uk.screen.at.app.stamp.personalengraving.query;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.stamp.management.DisplayScreenStampingResultDto;
import nts.uk.ctx.at.record.app.find.stamp.management.DisplayScreenStampingResultFinder;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ConfirmStatusActualDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service.CompanyDailyItemService;

/**
 * @author anhdt
 * 打刻結果の確認及び実績の確認画面を取得する
 */
@Stateless
public class StampResultConfirmationQuery {
	
	@Inject
	private DisplayScreenStampingResultFinder displayScreenStamping;
	
	@Inject
	private ConfirmStatusActualDay confirmStatusActualDay;
	
	@Inject
	private CompanyDailyItemService companyDailyItemService;
	
	private Object getStampResultConfirm(StampResultConfirmRequest param) {
		// 1
		List<DisplayScreenStampingResultDto> screenDisplay = displayScreenStamping.getDisplay(param.toStampDatePeriod());
		// 2
//		List<ConfirmStatusActualResult> confirmStatusAcResults = confirmStatusActualDay.
		
//		List<AttItemName> getDailyItems = companyDailyItemService.getDailyItems(cid, authorityId, attendanceItemIds, itemAtrs);
		
		return null;
	}
	
}
