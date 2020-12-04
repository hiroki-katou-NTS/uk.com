package nts.uk.ctx.at.request.infra.repository.application.lateleaveearly;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Worksheet;

import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfApp;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateCancelation;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.TimeReport;

/**
 * @author anhnm
 *
 */
@Stateless
public class AsposeLateLeaveEarly {
	private final String HALF_SIZE_SPACE = " ";
	private final String FULL_SIZE_SPACE = "　";
	private final String EMPTY = "";

	/**
	 * @param worksheet
	 * @param printContentOfApp
	 */
	public void printLateEarlyContent(Worksheet worksheet, PrintContentOfApp printContentOfApp) {
		Cells cells = worksheet.getCells();
		Cell cellB8 = cells.get("B8");
		cellB8.setValue(I18NText.getText("KAF004_14"));
		Cell cellB9 = cells.get("B9");
		cellB9.setValue(I18NText.getText("KAF004_15"));
		Cell cellB10 = cells.get("B10");
		cellB10.setValue(I18NText.getText("KAF004_32"));
		Cell cellB11 = cells.get("B11");
		cellB11.setValue(I18NText.getText("KAF004_33"));

		Cell cellD8 = cells.get("D8");
		Cell cellD9 = cells.get("D9");
		Cell cellD10 = cells.get("D10");
		Cell cellD11 = cells.get("D11");

		List<TimeReport> timeReportsTemp;
		List<LateCancelation> cancelTemp;

		boolean multipleWorkTimeManagement = printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get()
				.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().isManagementMultipleWorkCycles();

		// A1_2
		String scheAttendTime1 = EMPTY;
		if (printContentOfApp.getOpArrivedLateLeaveEarlyInfo().isPresent()
				&& printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get().getAppDispInfoStartupOutput()
						.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().isPresent()
				&& printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get().getAppDispInfoStartupOutput()
						.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get().get(0)
						.getOpAchievementDetail().isPresent()
				&& printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get().getAppDispInfoStartupOutput()
						.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get().get(0)
						.getOpAchievementDetail().get().getAchievementEarly().getScheAttendanceTime1() != null) {
			scheAttendTime1 = printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get().getAppDispInfoStartupOutput()
					.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get().get(0).getOpAchievementDetail()
					.get().getAchievementEarly().getScheAttendanceTime1().map(x -> x.getInDayTimeWithFormat()).orElse(EMPTY);
		}
		// A2_2
		String scheDepartureTime1 = EMPTY;
		if (printContentOfApp.getOpArrivedLateLeaveEarlyInfo().isPresent()
				&& printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get().getAppDispInfoStartupOutput()
						.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().isPresent()
				&& printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get().getAppDispInfoStartupOutput()
						.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get().get(0)
						.getOpAchievementDetail().isPresent()
				&& printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get().getAppDispInfoStartupOutput()
						.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get().get(0)
						.getOpAchievementDetail().get().getAchievementEarly().getScheDepartureTime1() != null) {
			scheDepartureTime1 = printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get().getAppDispInfoStartupOutput()
					.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get().get(0).getOpAchievementDetail()
					.get().getAchievementEarly().getScheDepartureTime1().map(x -> x.getInDayTimeWithFormat()).orElse(EMPTY);
		}

		timeReportsTemp = printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get().getArrivedLateLeaveEarly().get()
				.getLateOrLeaveEarlies().stream().map(x -> {
					if (x.getWorkNo() == 1 && x.getLateOrEarlyClassification().value == 0) {
						return x;
					}
					return null;
				}).filter(m -> (m != null)).collect(Collectors.toList());
		cancelTemp = printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get().getArrivedLateLeaveEarly().get()
				.getLateCancelation().stream().map(x -> {
					if (x.getWorkNo() == 1 && x.getLateOrEarlyClassification().value == 0) {
						return x;
					}
					return null;
				}).filter(m -> (m != null)).collect(Collectors.toList());
		// A1_3
		String attendTime1 = timeReportsTemp.size() > 0
				? timeReportsTemp.get(0).getTimeWithDayAttr().getFullText()
				: null;
		// A1_4
		String cancel1 = cancelTemp.size() == 0 ? null : I18NText.getText("KAF004_63");

		if (cancel1 != null) {
			cellD8.setValue(cancel1);
		} else {
			if (attendTime1 != null) {
				String valueA1_2 = scheAttendTime1.isEmpty() ? EMPTY
						: new StringBuilder().append(I18NText.getText("KAF004_21")).append(HALF_SIZE_SPACE)
								.append(scheAttendTime1).append(FULL_SIZE_SPACE).toString();
				String valueD8 = new StringBuilder().append(valueA1_2).append(attendTime1).append(HALF_SIZE_SPACE)
						.append(I18NText.getText("KAF004_54")).toString();
				cellD8.setValue(valueD8);
			} else {
				cellB8.setValue(EMPTY);
			}
		}

		timeReportsTemp = printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get().getArrivedLateLeaveEarly().get()
				.getLateOrLeaveEarlies().stream().map(x -> {
					if (x.getWorkNo() == 1 && x.getLateOrEarlyClassification().value == 1) {
						return x;
					}
					return null;
				}).filter(m -> (m != null)).collect(Collectors.toList());
		cancelTemp = printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get().getArrivedLateLeaveEarly().get()
				.getLateCancelation().stream().map(x -> {
					if (x.getWorkNo() == 1 && x.getLateOrEarlyClassification().value == 1) {
						return x;
					}
					return null;
				}).filter(m -> (m != null)).collect(Collectors.toList());
		// A2_3
		String leaveTime1 = timeReportsTemp.size() > 0
				? timeReportsTemp.get(0).getTimeWithDayAttr().getFullText()
				: null;
		// A2_4
		String cancel2 = cancelTemp.size() == 0 ? null : I18NText.getText("KAF004_63");

		if (cancel2 != null) {
			cellD9.setValue(cancel2);
		} else {
			if (leaveTime1 != null) {
				String valueA2_2 = scheDepartureTime1.isEmpty() ? EMPTY
						: new StringBuilder().append(I18NText.getText("KAF004_21")).append(HALF_SIZE_SPACE)
								.append(scheDepartureTime1).append(FULL_SIZE_SPACE).toString();
				String valueD9 = new StringBuilder().append(valueA2_2).append(leaveTime1).append(HALF_SIZE_SPACE)
						.append(I18NText.getText("KAF004_55")).toString();
				cellD9.setValue(valueD9);
			} else {
				cellB9.setValue(EMPTY);
			}
		}

		String attendTime2 = null;
		String leaveTime2 = null;

		if (multipleWorkTimeManagement) {
			// A3_2
			String scheAttendTime2 = EMPTY;
			if (printContentOfApp.getOpArrivedLateLeaveEarlyInfo().isPresent()
					&& printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get().getAppDispInfoStartupOutput()
							.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().isPresent()
					&& printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get().getAppDispInfoStartupOutput()
							.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get().get(0)
							.getOpAchievementDetail().isPresent()
					&& printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get().getAppDispInfoStartupOutput()
							.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get().get(0)
							.getOpAchievementDetail().get().getAchievementEarly().getScheAttendanceTime2()
							.isPresent()) {
				scheAttendTime2 = printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get().getAppDispInfoStartupOutput()
						.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get().get(0)
						.getOpAchievementDetail().get().getAchievementEarly().getScheAttendanceTime2().get()
						.getInDayTimeWithFormat();
			}
			// A4_2
			String scheDepartureTime2 = EMPTY;
			if (printContentOfApp.getOpArrivedLateLeaveEarlyInfo().isPresent()
					&& printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get().getAppDispInfoStartupOutput()
							.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().isPresent()
					&& printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get().getAppDispInfoStartupOutput()
							.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get().get(0)
							.getOpAchievementDetail().isPresent()
					&& printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get().getAppDispInfoStartupOutput()
							.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get().get(0)
							.getOpAchievementDetail().get().getAchievementEarly().getScheDepartureTime2().isPresent()) {
				scheDepartureTime2 = printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get()
						.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst()
						.get().get(0).getOpAchievementDetail().get().getAchievementEarly().getScheDepartureTime2()
						.get().getInDayTimeWithFormat();
			}

			timeReportsTemp = printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get().getArrivedLateLeaveEarly().get()
					.getLateOrLeaveEarlies().stream().map(x -> {
						if (x.getWorkNo() == 2 && x.getLateOrEarlyClassification().value == 0) {
							return x;
						}
						return null;
					}).filter(m -> (m != null)).collect(Collectors.toList());
			cancelTemp = printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get().getArrivedLateLeaveEarly().get()
					.getLateCancelation().stream().map(x -> {
						if (x.getWorkNo() == 2 && x.getLateOrEarlyClassification().value == 0) {
							return x;
						}
						return null;
					}).filter(m -> (m != null)).collect(Collectors.toList());
			// A3_3
			attendTime2 = timeReportsTemp.size() > 0
					? timeReportsTemp.get(0).getTimeWithDayAttr().getFullText()
					: null;
			// A1_4
			String cancel3 = cancelTemp.size() == 0 ? null : I18NText.getText("KAF004_63");

			if (cancel3 != null) {
				cellD10.setValue(cancel3);
			} else {
				if (attendTime2 != null) {
					String valueA3_2 = scheAttendTime2.isEmpty() ? EMPTY
							: new StringBuilder().append(I18NText.getText("KAF004_21")).append(HALF_SIZE_SPACE)
									.append(scheAttendTime2).append(FULL_SIZE_SPACE).toString();
					String valueD10 = new StringBuilder().append(valueA3_2).append(attendTime2).append(HALF_SIZE_SPACE)
							.append(I18NText.getText("KAF004_54")).toString();
					cellD10.setValue(valueD10);
				} else {
					cellB10.setValue(EMPTY);
				}
			}

			timeReportsTemp = printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get().getArrivedLateLeaveEarly().get()
					.getLateOrLeaveEarlies().stream().map(x -> {
						if (x.getWorkNo() == 2 && x.getLateOrEarlyClassification().value == 1) {
							return x;
						}
						return null;
					}).filter(m -> (m != null)).collect(Collectors.toList());
			cancelTemp = printContentOfApp.getOpArrivedLateLeaveEarlyInfo().get().getArrivedLateLeaveEarly().get()
					.getLateCancelation().stream().map(x -> {
						if (x.getWorkNo() == 2 && x.getLateOrEarlyClassification().value == 1) {
							return x;
						}
						return null;
					}).filter(m -> (m != null)).collect(Collectors.toList());
			// A4_3
			leaveTime2 = timeReportsTemp.size() > 0
					? timeReportsTemp.get(0).getTimeWithDayAttr().getFullText()
					: null;
			// A1_4
			String cancel4 = cancelTemp.size() == 0 ? null : I18NText.getText("KAF004_63");

			if (cancel4 != null) {
				cellD11.setValue(cancel4);
			} else {
				if (leaveTime2 != null) {
					String valueA4_2 = scheDepartureTime2.isEmpty() ? EMPTY
							: new StringBuilder().append(I18NText.getText("KAF004_21")).append(HALF_SIZE_SPACE)
									.append(scheDepartureTime2).append(FULL_SIZE_SPACE).toString();
					String valueD11 = new StringBuilder().append(valueA4_2).append(leaveTime2)
							.append(HALF_SIZE_SPACE).append(I18NText.getText("KAF004_55")).toString();
					cellD11.setValue(valueD11);
				} else {
					cellB11.setValue(EMPTY);
				}
			}
		} else {
			cellB10.setValue(EMPTY);
			cellB11.setValue(EMPTY);
		}

	}

	public void deleteEmptyRow(Worksheet worksheet) {
		Cells cells = worksheet.getCells();

		Cell cellB8 = cells.get("B8");
		Cell cellB9 = cells.get("B9");
		Cell cellB10 = cells.get("B10");
		Cell cellB11 = cells.get("B11");

		if (cellB11.getValue().toString().isEmpty()) {
			worksheet.getCells().deleteRow(10);
		}
		if (cellB10.getValue().toString().isEmpty()) {
			worksheet.getCells().deleteRow(9);
		}
		if (cellB9.getValue().toString().isEmpty()) {
			worksheet.getCells().deleteRow(8);
		}
		if (cellB8.getValue().toString().isEmpty()) {
			worksheet.getCells().deleteRow(7);
		}
	}
}
