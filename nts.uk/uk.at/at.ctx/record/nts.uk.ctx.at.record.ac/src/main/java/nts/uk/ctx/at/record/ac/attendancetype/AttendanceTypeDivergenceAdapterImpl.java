package nts.uk.ctx.at.record.ac.attendancetype;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.pub.attendancetype.AttendanceTypePub;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendancetype.AttendanceTypeDivergenceAdapterDto;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendancetype.AttendanceTypeDivergenceAdapter;
@Stateless
public class AttendanceTypeDivergenceAdapterImpl implements AttendanceTypeDivergenceAdapter{
	@Inject
	private AttendanceTypePub atPub;
	@Override
	public List<AttendanceTypeDivergenceAdapterDto> getItemByScreenUseAtr(String companyId, int screenUseAtr) {
		List<AttendanceTypeDivergenceAdapterDto> data = atPub.getItemByScreenUseAtr(companyId, screenUseAtr).stream()
				.map(x -> new AttendanceTypeDivergenceAdapterDto(x.getAttendanceItemId()))
				.collect(Collectors.toList());
		return data;
	}

}
