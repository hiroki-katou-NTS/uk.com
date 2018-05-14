package nts.uk.ctx.at.function.app.find.attendancerecord.export;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExport;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExportRepository;

/**
 * The Class AttendanceRecordExportFinder.
 */
@Stateless
public class AttendanceRecordExportFinder {

	/** The attendance record exp repo. */
	@Inject
	AttendanceRecordExportRepository attendanceRecordExpRepo;

	/**
	 * Gets the all attendance record export.
	 *
	 * @param companyId
	 *            the company id
	 * @param code
	 *            the code
	 * @return the all attendance record export
	 */
	public List<AttendanceRecordExportDto> getAllAttendanceRecordExport(String companyId, String code) {

		// Get list of domain
		List<AttendanceRecordExport> domainList = attendanceRecordExpRepo.getAllAttendanceRecordExport(companyId, code);

		// Convert domain to Dto
		List<AttendanceRecordExportDto> dtoList = domainList.stream().map(item -> {
			AttendanceRecordExportDto dto = new AttendanceRecordExportDto();
			dto.setColumnIndex(item.getColumnIndex());
			dto.setExportAtr(item.getExportAtr().value);
			dto.setUserAtr(item.getUseAtr());
			return dto;
		}).collect(Collectors.toList());
		return dtoList;
	}

}
