package nts.uk.ctx.at.function.pubimp.attendanceitemname;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums.TypeOfItem;
import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemNameDomainService;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceType;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceTypeRepository;
import nts.uk.ctx.at.function.pub.attendanceitemname.AttendanceItemExport;
import nts.uk.ctx.at.function.pub.attendanceitemname.AttendanceItemPub;

@Stateless
public class AttendanceItemPubImpl implements AttendanceItemPub{
	
	@Inject
	private AttendanceItemNameDomainService attendanceItemNameDomainService;

	@Inject
	private AttendanceTypeRepository attendanceTypeRepository;

	@Override
	public List<AttendanceItemExport> getAttendanceItemName(List<Integer> dailyAttendanceItemIds,
			int typeOfAttendanceItem) {
		return this.attendanceItemNameDomainService.getNameOfAttendanceItem(dailyAttendanceItemIds, typeOfAttendanceItem).stream().map(item -> {
			return new AttendanceItemExport(item.getAttendanceItemId(), item.getAttendanceItemName(), item.getAttendanceItemDisplayNumber());
		}).collect(Collectors.toList());
	}

	@Override
	public List<AttendanceItemExport> getAttendanceItemName(int typeOfAttendanceItem) {
		return this.attendanceItemNameDomainService.getNameOfAttendanceItem(EnumAdaptor.valueOf(typeOfAttendanceItem, TypeOfItem.class)).stream().map(item -> {
			return new AttendanceItemExport(item.getAttendanceItemId(), item.getAttendanceItemName(), item.getAttendanceItemDisplayNumber());
		}).collect(Collectors.toList());
	}

	@Override
	public List<AttendanceItemExport> getByAttendenceIds(String cid,int typeOfAttendanceItem) {
		List<AttendanceType> attendanceTypes = attendanceTypeRepository.getItemByAtrandType(cid,typeOfAttendanceItem);
		List<Integer> attendanceItemIds = attendanceTypes.stream().map(AttendanceType::getAttendanceItemId).collect(Collectors.toList());

		return this.attendanceItemNameDomainService.getNameOfAttendanceItem(attendanceItemIds, typeOfAttendanceItem).stream().map(item -> {
			return new AttendanceItemExport(item.getAttendanceItemId(), item.getAttendanceItemName(), item.getAttendanceItemDisplayNumber());
		}).collect(Collectors.toList());
	}

}
