package nts.uk.ctx.at.record.ac.attendancetype;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.pub.attendancetype.AttendanceTypePub;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendancetype.AttendanceTypeDivergenceServiceDto;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendancetype.AttendanceTypeDivergenceSevice;
@Stateless
public class AttendanceTypeDivergenceSeviceImpl implements AttendanceTypeDivergenceSevice{
	@Inject
	private AttendanceTypePub atPub;
	@Override
	public List<AttendanceTypeDivergenceServiceDto> getItemByScreenUseAtr(String companyId, int screenUseAtr) {
		List<AttendanceTypeDivergenceServiceDto> data = atPub.getItemByScreenUseAtr(companyId, screenUseAtr).stream()
				.map(x -> new AttendanceTypeDivergenceServiceDto(x.getAttendanceItemId()))
				.collect(Collectors.toList());
		return data;
	}

}
