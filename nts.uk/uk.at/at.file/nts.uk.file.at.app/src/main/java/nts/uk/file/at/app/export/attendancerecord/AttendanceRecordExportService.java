package nts.uk.file.at.app.export.attendancerecord;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordRepositoty;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordRepository;
import nts.uk.ctx.at.shared.app.service.workrule.closure.ClosureEmploymentService;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AttendanceRecordExportService extends ExportService<AttendanceRecordRequest> {

	final static long UPPER_POSITION = 1;
	final static long LOWER_POSITION = 2;

	@Inject
	private ClosureEmploymentService closureEmploymentService;

	@Inject
	private SingleAttendanceRecordRepository singleAttendanceRepo;

	@Inject
	private CalculateAttendanceRecordRepositoty calculateAttendanceRepo;

	@Override
	protected void handle(ExportServiceContext<AttendanceRecordRequest> context) {

		// get Dto
		AttendanceRecordRequest request = context.getQuery();
		String companyId = AppContexts.user().companyId();
		request.getEmployeeCodeList().forEach(employeeId -> {

			// get Closure
			Optional<Closure> optionalClosure = closureEmploymentService.findClosureByEmployee(employeeId,
					request.getEndDate());
			ClosureDate closureDate;
			if (optionalClosure.isPresent()) {
				Closure closure = optionalClosure.get();

				// get closure history
				List<ClosureHistory> closureHistory = closure.getClosureHistories();

				// get init yearMonthMax
				YearMonth yearMonthMax = closureHistory.stream()
						.min(Comparator.comparing(ClosureHistory::getStartYearMonth))
						.orElseThrow(NoSuchElementException::new).getStartYearMonth();

				// find yearMonthMax
				for (ClosureHistory history : closureHistory) {
					YearMonth histYearMonth = history.getStartYearMonth();
					if (histYearMonth.greaterThan(yearMonthMax)
							&& histYearMonth.greaterThanOrEqualTo(request.getStartDate().yearMonth())
							&& histYearMonth.lessThanOrEqualTo(request.getEndDate().yearMonth())) {
						yearMonthMax = histYearMonth;
						closureDate = history.getClosureDate();

					}
				}

				// check if yearMonthMax is init value

				if (yearMonthMax.greaterThanOrEqualTo(request.getStartDate().yearMonth())
						&& yearMonthMax.lessThanOrEqualTo(request.getEndDate().yearMonth())) {

					// lấy danh sách hàng ngày hàng trên single
					List<Integer> singleIdUpper = this.singleAttendanceRepo
							.getIdSingleAttendanceRecordByPosition(companyId, request.getLayout(), UPPER_POSITION);

					// trả về danh sách giá trị hàng ngày hàng trên single
					/** Chưa làm **/

					// Lấy về danh sách hàng ngày hàng trên calculate

					List<CalculateAttendanceRecord> calculateUpperDaily = this.calculateAttendanceRepo
							.getIdCalculateAttendanceRecordDailyByPosition(companyId, request.getLayout(),
									UPPER_POSITION);

					// Trả về danh sách giá trị hàng ngày hàng trên calculate
					/** chưa làm **/

					/** Tổng hợp hàng trên hàng dưới **/

					// lấy danh sách hàng ngày hàng dưới single
					List<Integer> singleIdLower = this.singleAttendanceRepo
							.getIdSingleAttendanceRecordByPosition(companyId, request.getLayout(), LOWER_POSITION);

					// trả về danh sách gia trị hàng ngày hàng dưới single
					/** Chưa làm **/

					// Lấy về danh sách hàng ngày hàng dưới calculate

					List<CalculateAttendanceRecord> calculateLowerDaily = this.calculateAttendanceRepo
							.getIdCalculateAttendanceRecordDailyByPosition(companyId, request.getLayout(),
									LOWER_POSITION);

					// trả về danh sách gia trị hàng ngày hàng dưới calculate
					/** Chưa làm **/

					/*** Tổng hợp gia trị hàng dưới **/

					// Lấy về danh sách hàng tháng hàng trên
					List<CalculateAttendanceRecord> calculateUpperMonthly = this.calculateAttendanceRepo
							.getIdCalculateAttendanceRecordMonthlyByPosition(companyId, request.getLayout(),
									UPPER_POSITION);

					// Trả về giá trị hàng tháng hàng trên
					/** chưa làm **/
					

					// Lấy về danh sách hàng tháng hàng dưới
					List<CalculateAttendanceRecord> calculateLowerMonthly = this.calculateAttendanceRepo
							.getIdCalculateAttendanceRecordMonthlyByPosition(companyId, request.getLayout(),
									LOWER_POSITION);

					// Trả về giá trị hàng tháng hàng dưới
					/** chưa làm **/

				}

			}

		});
	}

}
