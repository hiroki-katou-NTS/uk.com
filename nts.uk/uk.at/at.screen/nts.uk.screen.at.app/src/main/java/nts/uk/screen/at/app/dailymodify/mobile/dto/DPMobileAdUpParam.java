package nts.uk.screen.at.app.dailymodify.mobile.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRecordWorkDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ApprovalConfirmCache;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPAttendanceItem;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemCheckBox;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemValue;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.screen.at.app.dailyperformance.correction.dto.month.DPMonthValue;
import nts.uk.screen.at.app.dailyperformance.correction.month.asynctask.ParamCommonAsync;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DPMobileAdUpParam {

	private int mode;

	private String employeeId;

	private List<DPItemValue> itemValues;

	private List<DPItemCheckBox> dataCheckSign = new ArrayList<>();

	private List<DPItemCheckBox> dataCheckApproval = new ArrayList<>();;

	private DateRange dateRange;

	private DPMonthValue monthValue;

	private List<DailyRecordDto> dailyOlds;

	private List<DailyRecordDto> dailyEdits;

	private List<DailyRecordDto> dailyOldForLog;

	private Map<Integer, DPAttendanceItem> lstAttendanceItem;

	private List<DPItemValue> lstNotFoundWorkType = new ArrayList<>();

	private Boolean showDialogError;

	private boolean showFlex;

	private boolean checkDailyChange;

	private ApprovalConfirmCache approvalConfirmCache;

	private Optional<MonthlyRecordWorkDto> domainMonthOpt = Optional.empty();

	private ParamCommonAsync paramCommonAsync;
}
