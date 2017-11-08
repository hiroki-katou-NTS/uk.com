package nts.uk.ctx.at.function.ac.dailyattendanceitem;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.DailyAttendanceItemAdapter;
import nts.uk.ctx.at.function.dom.adapter.DailyAttendanceItemAdapterDto;
import nts.uk.ctx.at.shared.pub.scherec.dailyattendanceitem.DailyAttendanceItemRecPub;

@Stateless
public class DailyAttendanceItemAcFinder implements DailyAttendanceItemAdapter {

	@Inject
	private DailyAttendanceItemRecPub dailyAttendanceItemPub;

	@Override
	public List<DailyAttendanceItemAdapterDto> getDailyAttendanceItem(String companyId,
			List<Integer> dailyAttendanceItemIds) {
		return this.dailyAttendanceItemPub.getDailyAttendanceItem(companyId, dailyAttendanceItemIds).stream().map(f -> {
			return new DailyAttendanceItemAdapterDto(f.getCompanyId(), f.getAttendanceItemId(), f.getAttendanceName(),
					f.getDisplayNumber(), f.getUserCanUpdateAtr(), f.getDailyAttendanceAtr(),
					f.getNameLineFeedPosition());
		}).collect(Collectors.toList());
	}

	@Override
	public List<DailyAttendanceItemAdapterDto> getDailyAttendanceItemList(String companyId) {
		return this.dailyAttendanceItemPub.getDailyAttendanceItemList(companyId).stream().map(f -> {
			return new DailyAttendanceItemAdapterDto(f.getCompanyId(), f.getAttendanceItemId(), f.getAttendanceName(),
					f.getDisplayNumber(), f.getUserCanUpdateAtr(), f.getDailyAttendanceAtr(),
					f.getNameLineFeedPosition());
		}).collect(Collectors.toList());
	}
	
}
