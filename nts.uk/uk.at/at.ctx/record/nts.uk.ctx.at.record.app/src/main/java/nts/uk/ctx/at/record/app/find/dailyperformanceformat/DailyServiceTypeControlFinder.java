package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyattendanceitem.DailyServiceTypeControl;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.repository.DailyServiceTypeControlRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DailyServiceTypeControlFinder {
	@Inject
	private DailyServiceTypeControlRepository dailyServiceTypeControlRepository;

	public List<DailyServiceTypeControlDto> getListDailyServiceTypeControl(String businessTypeCode) {
		String companyId = AppContexts.user().companyId();
		List<DailyServiceTypeControl> lstDailyServiceTypeControl = this.dailyServiceTypeControlRepository
				.getListDailyServiceTypeControl(new BusinessTypeCode(businessTypeCode), companyId);
		return lstDailyServiceTypeControl.stream().map(c -> toDailyServiceTypeControlDto(c))
				.collect(Collectors.toList());
	}

	private DailyServiceTypeControlDto toDailyServiceTypeControlDto(DailyServiceTypeControl dailyServiceTypeControl) {
		return new DailyServiceTypeControlDto(dailyServiceTypeControl.getAttendanceItemId(),
				dailyServiceTypeControl.getAttendanceItemName(), dailyServiceTypeControl.getBusinessTypeCode().v(),
				dailyServiceTypeControl.isUse(), dailyServiceTypeControl.isYouCanChangeIt(),
				dailyServiceTypeControl.isCanBeChangedByOthers(),
				dailyServiceTypeControl.getUserCanSet() == 1 ? true : false);
	}
}
