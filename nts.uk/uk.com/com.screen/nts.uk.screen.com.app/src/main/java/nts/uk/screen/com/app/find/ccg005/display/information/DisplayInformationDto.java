package nts.uk.screen.com.app.find.ccg005.display.information;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeBasicImport;
import nts.uk.screen.com.app.find.ccg005.attendance.information.AttendanceInformationDto;

@Builder
@Data
public class DisplayInformationDto {
	// 在席情報DTO（List）
	private List<AttendanceInformationDto> attendanceInformationDtos;
	// 個人基本情報（List）
	private List<EmployeeBasicImport> listPersonalInfo;
}
