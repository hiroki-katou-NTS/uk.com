package nts.uk.screen.at.app.stamp.personalengraving.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.assertj.core.util.Arrays;

import nts.uk.ctx.at.record.app.find.stamp.management.DisplayScreenStampingResultDto;
import nts.uk.ctx.at.record.app.find.stamp.management.DisplayScreenStampingResultFinder;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ConfirmStatusActualDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service.CompanyDailyItemService;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

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
	
	@Inject
	private WorkTypeRepository workTypeRepo;
	
	private Object getStampResultConfirm(StampResultConfirmRequest param) {
		// 1
		List<DisplayScreenStampingResultDto> screenDisplay = displayScreenStamping.getDisplay(param.toStampDatePeriod());
		// 2
//		List<ConfirmStatusActualResult> confirmStatusAcResults = confirmStatusActualDay.
		String cid = AppContexts.user().companyId();
		String authorityId = AppContexts.user().roles().forAttendance();
		List<AttItemName> getDailyItems = companyDailyItemService.getDailyItems(cid, Optional.ofNullable(authorityId) , param.getAttendanceItems(), Collections.emptyList());
		
//		workTypeRepo.getCodeNameWorkType(cid, getDailyItems.stream().filter(i -> i.getAttendanceItemId() == 28).findFirst().orElseGet(null).);
		
		return null;
	}
	
}
