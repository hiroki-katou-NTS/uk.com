package nts.uk.ctx.at.function.pubimp.attendanceitemname;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemNameDomainService;
import nts.uk.ctx.at.function.pub.attendanceitemname.AttendanceItemExport;
import nts.uk.ctx.at.function.pub.attendanceitemname.AttendanceItemPub;

@Stateless
public class AttendanceItemPubImpl implements AttendanceItemPub{
	
	@Inject
	private AttendanceItemNameDomainService attendanceItemNameDomainService;

	@Override
	public List<AttendanceItemExport> getAttendanceItemName(List<Integer> dailyAttendanceItemIds,
			int typeOfAttendanceItem) {
		return this.attendanceItemNameDomainService.getNameOfAttendanceItem(dailyAttendanceItemIds, typeOfAttendanceItem).stream().map(item -> {
			return new AttendanceItemExport(item.getAttendanceItemId(), item.getAttendanceItemName(), item.getAttendanceItemDisplayNumber());
		}).collect(Collectors.toList());
	}

}
