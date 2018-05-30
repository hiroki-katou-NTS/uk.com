package nts.uk.file.at.app.export.attendancerecord;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordRepositoty;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordRepository;
import nts.uk.ctx.at.record.app.service.attendanceitem.value.AttendanceItemValueService;
import nts.uk.ctx.at.record.app.service.attendanceitem.value.AttendanceItemValueService.MonthlyAttendanceItemValueResult;
import nts.uk.ctx.at.shared.app.service.workrule.closure.ClosureEmploymentService;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
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

	@Inject
	private AttendanceItemValueService attendanceService;

	@Override
	protected void handle(ExportServiceContext<AttendanceRecordRequest> context) {

		// get Dto
		AttendanceRecordRequest request = context.getQuery();
		String companyId = AppContexts.user().companyId();
		request.getEmployeeCodeList().forEach(employeeId -> {

			// get Closure
			Optional<Closure> optionalClosure = closureEmploymentService.findClosureByEmployee(employeeId,
					request.getEndDate());
			ClosureDate closureDate = new ClosureDate(0, false);
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

					// get upper-daily-calculateItem list

					List<CalculateAttendanceRecord> calculateUpperDaily = this.calculateAttendanceRepo
							.getIdCalculateAttendanceRecordDailyByPosition(companyId, request.getLayout(),
									UPPER_POSITION);

					// get lower-daily-singleItem list
					List<Integer> singleIdLower = this.singleAttendanceRepo
							.getIdSingleAttendanceRecordByPosition(companyId, request.getLayout(), LOWER_POSITION);

					// get lower-daily-CalculateItem list

					List<CalculateAttendanceRecord> calculateLowerDaily = this.calculateAttendanceRepo
							.getIdCalculateAttendanceRecordDailyByPosition(companyId, request.getLayout(),
									LOWER_POSITION);

					// get upper-monthly-Item list
					List<CalculateAttendanceRecord> calculateUpperMonthly = this.calculateAttendanceRepo
							.getIdCalculateAttendanceRecordMonthlyByPosition(companyId, request.getLayout(),
									UPPER_POSITION);

					// get lower-monthly-Item list
					List<CalculateAttendanceRecord> calculateLowerMonthly = this.calculateAttendanceRepo
							.getIdCalculateAttendanceRecordMonthlyByPosition(companyId, request.getLayout(),
									LOWER_POSITION);

					YearMonth startYearMonth = request.getStartDate().yearMonth();
					YearMonth endYearMonth = request.getEndDate().yearMonth();
					YearMonth yearMonth = startYearMonth;
					while (yearMonth.lessThanOrEqualTo(endYearMonth)) {

						GeneralDate startDateByClosure = GeneralDate.ymd(yearMonth.year(), yearMonth.month(),
								closureDate.getClosureDay().v());
						GeneralDate endDateByClosure = GeneralDate.ymd(yearMonth.year(), yearMonth.month(),
								closureDate.getClosureDay().v());

						// Report by Month
						while (startDateByClosure.beforeOrEquals(endDateByClosure)) {
							List<AttendanceItemValueService.AttendanceItemValueResult> dailyresult = new ArrayList<>();

							// return result upper-daily-singleItems
							AttendanceItemValueService.AttendanceItemValueResult valueSingleUpper = attendanceService
									.getValueOf(employeeId, startDateByClosure, singleIdUpper);
							dailyresult.add(valueSingleUpper);

							// return result upper-daily-calculateItems
							calculateUpperDaily.forEach(item -> {
								// get add item
								AttendanceItemValueService.AttendanceItemValueResult addValueCalUpper = attendanceService
										.getValueOf(employeeId, startDateByClosure, item.getAddedItem());

								// get sub item
								AttendanceItemValueService.AttendanceItemValueResult subValueCalUpper = attendanceService
										.getValueOf(employeeId, startDateByClosure, item.getSubtractedItem());

								Object sum;
								switch (addValueCalUpper.getAttendanceItems().get(0).getValueType().value) {
								case (0):
									sum = new Integer(0);
									// calculate add
									for (ItemValue i : addValueCalUpper.getAttendanceItems()) {
										sum = Integer.parseInt(sum.toString()) + (int) i.value();
									}
									// calculate sub
									for (ItemValue i : subValueCalUpper.getAttendanceItems()) {
										sum = Integer.parseInt(sum.toString()) - (int) i.value();
									}
									break;
								case (1):
									sum = new String("");
									Integer tmp = 0;
									String period;
									for (ItemValue i : addValueCalUpper.getAttendanceItems()) {
										if (i.value() != null) {
											tmp = tmp + (int) this.getNumberFromString(i.value().toString());
										}

									}
									// calculate sub
									for (ItemValue i : subValueCalUpper.getAttendanceItems()) {
										tmp = Integer.parseInt(sum.toString())
												- (int) this.getNumberFromString(i.value().toString());
									}

									period = this.getPeriodFromString(subValueCalUpper.getAttendanceItems());

									sum = tmp + period;
									break;
								case (2):
									sum = new Double(0);
									// calculate add
									for (ItemValue i : addValueCalUpper.getAttendanceItems()) {
										sum = Double.parseDouble(sum.toString()) + (Double) i.value();
									}
									// calculate sub
									for (ItemValue i : subValueCalUpper.getAttendanceItems()) {
										sum = Double.parseDouble(sum.toString()) - (Double) i.value();
									}
									break;
								case (3):
									sum = new BigDecimal(0);
									BigDecimal sum1 = new BigDecimal(0);
									// calculate add
									for (ItemValue i : addValueCalUpper.getAttendanceItems()) {
										sum1 = sum1.add(new BigDecimal(i.value().toString()));
									}
									// calculate sub
									for (ItemValue i : subValueCalUpper.getAttendanceItems()) {
										sum1 = sum1.subtract(new BigDecimal(i.value().toString()));
									}
									sum = sum1;
									break;
								}

							});
							// AttendanceItemValueService.AttendanceItemValueResult
							// valueCalculateUpper =
						}
					}

					List<AttendanceItemValueService.MonthlyAttendanceItemValueResult> monthlyResult = new ArrayList<>();

					/** To do **/

					/** get upper-daily result **/

					// return result lower-daily-singleItems
					/** To do **/

					// return result lower-daily-calculateItems
					/** To do **/

					/** get lower-daily result **/

					// return result upper-monthly-Items
					/** To do **/

					// return result lower-monthly-Items
					/** To do **/

				}

			}

		});
	}

	Integer getNumberFromString(String string) {

		String result = string.replaceAll("\\D+", "");
		return Integer.parseInt(result);
	}

	String getPeriodFromString(List<ItemValue> list) {
		for (ItemValue i : list) {
			if (i.value() != null) {
				String tmp = this.getNumberFromString(i.value().toString()).toString();
				int index = i.value().toString().indexOf(tmp);
				return i.value().toString().substring(index);
			}
		}
		return null;

	}

}
