package nts.uk.screen.at.app.dailyperformance.correction.calctime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.dailyperform.checkdata.DailyModifyRCResult;
import nts.uk.ctx.at.record.app.command.dailyperform.correctevent.DailyCorrectEventServiceCenter;
import nts.uk.ctx.at.record.app.command.dailyperform.correctevent.EventCorrectResult;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.screen.at.app.dailyperformance.correction.datadialog.CodeName;
import nts.uk.screen.at.app.dailyperformance.correction.datadialog.DataDialogWithTypeProcessor;
import nts.uk.screen.at.app.dailyperformance.correction.datadialog.ParamDialog;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemValue;
import nts.uk.screen.at.app.dailyperformance.correction.dto.calctime.DCCalcTime;
import nts.uk.screen.at.app.dailyperformance.correction.dto.calctime.DCCellEdit;
import nts.uk.screen.at.app.dailyperformance.correction.dto.type.TypeLink;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DailyCorrectCalcTimeService {

	public static final int MINUTES_OF_DAY = 24 * 60;

	private static final String FORMAT_HH_MM = "%d:%02d";

	@Inject
	private DailyCorrectEventServiceCenter dailyCorrectEventServiceCenter;
	
	@Inject
	private DataDialogWithTypeProcessor dataDialogWithTypeProcessor;

	public DCCalcTime calcTime(List<DailyRecordDto> dailyEdits, DPItemValue itemEdit) {

		DCCalcTime calcTime = new DCCalcTime();

		getWplPosId(itemEdit);
		
		DailyRecordDto dtoEdit = dailyEdits.stream()
				.filter(x -> equalEmpAndDate(x.getEmployeeId(), x.getDate(), itemEdit)).findFirst().orElse(null);

		val itemValue = new ItemValue(itemEdit.getValue(), ValueType.valueOf(itemEdit.getValueType()),
				itemEdit.getLayoutCode(), itemEdit.getItemId());
		DailyModifyRCResult updated = DailyModifyRCResult.builder().employeeId(itemEdit.getEmployeeId())
				.workingDate(itemEdit.getDate()).items(Arrays.asList(itemValue)).completed();

		String companyId = AppContexts.user().companyId();
		
		AttendanceItemUtil.fromItemValues(dtoEdit, Arrays.asList(itemValue));

		EventCorrectResult result = dailyCorrectEventServiceCenter.correctRunTime(dtoEdit, updated, companyId);
		List<ItemValue> items = result.getCorrectedItemsWithStrict();

		DailyRecordDto resultBaseDto = result.getCorrected().workingDate(itemEdit.getDate()).employeeId(itemEdit.getEmployeeId());

		val dailyEditsResult = dailyEdits.stream().map(x -> {
			if (equalEmpAndDate(x.getEmployeeId(), x.getDate(), itemEdit)) {
				return resultBaseDto;
			} else {
				return x;
			}
		}).collect(Collectors.toList());
		calcTime.setCellEdits(items.stream().map(x -> new DCCellEdit(itemEdit.getRowId(), "A" + x.getItemId(),
				convertData(x.getValueType().value, x.getValue()))).collect(Collectors.toList()));
		calcTime.setDailyEdits(dailyEditsResult);

		return calcTime;
	}

	private Object convertData(int valueType, String value) {
		switch (valueType) {
		case 1:
		case 2:
		case 15:
			return value == null ? "" : converTime(valueType, value);
		default:
			return value == null ? "" : value;
		}
	}

	private String converTime(int valueType, String value) {
		int minute = 0;
		if (Integer.parseInt(value) >= 0) {
			minute = Integer.parseInt(value);
		} else {
			if (valueType == ValueType.TIME_WITH_DAY.value) {
				minute = 0 - ((Integer.parseInt(value)
						+ (1 + -Integer.parseInt(value) / MINUTES_OF_DAY) * MINUTES_OF_DAY));
			} else {
				minute = Integer.parseInt(value);
			}
		}
		int hours = minute / 60;
		int minutes = Math.abs(minute) % 60;
		String valueConvert = (minute < 0 && hours == 0) ? "-" + String.format(FORMAT_HH_MM, hours, minutes)
				: String.format(FORMAT_HH_MM, hours, minutes);
		return valueConvert;
	}

	private boolean equalEmpAndDate(String employee, GeneralDate date, DPItemValue itemEdit) {
		return employee.equals(itemEdit.getEmployeeId()) && date.equals(itemEdit.getDate());
	}
	
	private void getWplPosId(DPItemValue itemEdit) {
		// map id -> code possition and workplace
		if(itemEdit.getTypeGroup() == null) return;
		if (itemEdit.getTypeGroup() == TypeLink.POSSITION.value) {
			CodeName codeName = dataDialogWithTypeProcessor.getTypeDialog(itemEdit.getTypeGroup(),
					new ParamDialog(itemEdit.getDate(), itemEdit.getValue()));
			itemEdit.setValue(codeName == null ? null : codeName.getId());
		} else if (itemEdit.getTypeGroup() == TypeLink.WORKPLACE.value) {
			CodeName codeName = dataDialogWithTypeProcessor.getTypeDialog(itemEdit.getTypeGroup(),
					new ParamDialog(itemEdit.getDate(), itemEdit.getValue()));
			itemEdit.setValue(codeName == null ? null : codeName.getId());
		}
	}
}
