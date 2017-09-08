package nts.uk.ctx.at.function.pubimp.attendancetype;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.uk.ctx.at.function.dom.attendancetype.AttendanceTypeRepository;
import nts.uk.ctx.at.function.pub.attendancetype.AttendanceTypePub;
import nts.uk.ctx.at.function.pub.attendancetype.AttendanceTypePubDto;
@Stateless
public class AttendanceTypePubImp implements AttendanceTypePub {
	
	private AttendanceTypeRepository repository;
	@Override
	public List<AttendanceTypePubDto> getItemByScreenUseAtr(String companyId, int screenUseAtr) {
		List<AttendanceTypePubDto> data = repository.getItemByScreenUseAtr(companyId, screenUseAtr)
				.stream()
				.map(x -> new AttendanceTypePubDto(companyId, x.getAttendanceItemId(), x.getAttendanceItemType().value))
				.collect(Collectors.toList());
		return data;
	}

}
