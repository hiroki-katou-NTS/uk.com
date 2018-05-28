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
					
					// get upper-daily-singleItem list
					List<Integer> singleIdUpper = this.singleAttendanceRepo
							.getIdSingleAttendanceRecordByPosition(companyId, request.getLayout(), UPPER_POSITION);

					// return result upper-daily-singleItems
					/** To do**/

					//  get upper-daily-calculateItem list

					List<CalculateAttendanceRecord> calculateUpperDaily = this.calculateAttendanceRepo
							.getIdCalculateAttendanceRecordDailyByPosition(companyId, request.getLayout(),
									UPPER_POSITION);

					//  return result upper-daily-calculateItems
					/** To do **/

					/** get upper-daily result**/

					// get lower-daily-singleItem list
					List<Integer> singleIdLower = this.singleAttendanceRepo
							.getIdSingleAttendanceRecordByPosition(companyId, request.getLayout(), LOWER_POSITION);

					// return result lower-daily-singleItems
					/** To do**/

					// get lower-daily-CalculateItem list

					List<CalculateAttendanceRecord> calculateLowerDaily = this.calculateAttendanceRepo
							.getIdCalculateAttendanceRecordDailyByPosition(companyId, request.getLayout(),
									LOWER_POSITION);

					// return result lower-daily-calculateItems
					/** To do **/

					/** get lower-daily result**/

					// get upper-monthly-Item list
					List<CalculateAttendanceRecord> calculateUpperMonthly = this.calculateAttendanceRepo
							.getIdCalculateAttendanceRecordMonthlyByPosition(companyId, request.getLayout(),
									UPPER_POSITION);

					// return result upper-monthly-Items
					/** To do **/
					

					// get lower-monthly-Item list
					List<CalculateAttendanceRecord> calculateLowerMonthly = this.calculateAttendanceRepo
							.getIdCalculateAttendanceRecordMonthlyByPosition(companyId, request.getLayout(),
									LOWER_POSITION);

					// return result lower-monthly-Items
					/** To do**/

				}

			}

		});
	}

}
