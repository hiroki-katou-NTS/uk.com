package nts.uk.ctx.at.shared.app.find.attendance;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.attendance.AttendanceItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AttendanceItemFinder {
	@Inject
	private AttendanceItemRepository attendanceRepo;

	public List<AttendanceItemDto> getPossibleAttendanceItem(List<Integer> listPossible) {
		List<AttendanceItemDto> lst = this.attendanceRepo
				.getPossibleAttendanceItems(AppContexts.user().companyId(), listPossible).stream()
				.map(c -> AttendanceItemDto.fromDomain(c)).collect(Collectors.toList());
		return lst;
	}

	
	public List<AttItemDto> getAttendanceItems() {
		return this.attendanceRepo.getAttendanceItems(AppContexts.user().companyId()).stream()
				.map(c -> AttItemDto.fromDomain(c)).collect(Collectors.toList());
	}

}
