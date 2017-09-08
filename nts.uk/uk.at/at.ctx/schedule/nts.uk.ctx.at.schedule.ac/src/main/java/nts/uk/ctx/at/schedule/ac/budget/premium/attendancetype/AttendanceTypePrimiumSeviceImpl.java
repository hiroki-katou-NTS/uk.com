package nts.uk.ctx.at.schedule.ac.budget.premium.attendancetype;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.uk.ctx.at.function.pub.attendancetype.AttendanceTypePub;
import nts.uk.ctx.at.schedule.dom.budget.premium.service.AttendanceTypePrimiumSevice;
import nts.uk.ctx.at.schedule.dom.budget.premium.service.AttendanceTypePriServiceDto;
@Stateless
public class AttendanceTypePrimiumSeviceImpl implements AttendanceTypePrimiumSevice {
	private AttendanceTypePub atPub;
	@Override
	public List<AttendanceTypePriServiceDto> getItemByScreenUseAtr(String companyId, int screenUseAtr) {
		List<AttendanceTypePriServiceDto> data = atPub.getItemByScreenUseAtr(companyId, screenUseAtr).stream()
				.map(x -> new AttendanceTypePriServiceDto(x.getAttendanceItemId()))
				.collect(Collectors.toList());
		return data;
	}

}
