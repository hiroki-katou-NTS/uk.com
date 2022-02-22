package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.*;

import nts.uk.ctx.at.record.app.command.dailyperform.month.UpdateMonthDailyParam;
import org.apache.commons.lang3.tuple.Pair;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRecordWorkDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.month.DPMonthValue;
import nts.uk.screen.at.app.dailyperformance.correction.month.asynctask.ParamCommonAsync;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DPItemParent {
	private int mode;
	
	private String employeeId;
	
	private List<DPItemValue> itemValues;
	
	private List<DPItemCheckBox> dataCheckSign;
	
	private List<DPItemCheckBox> dataCheckApproval;
	
	private DateRange dateRange;
	
	private SPRStampSourceInfo spr;
	
	private DPMonthValue monthValue;
	
	private List<DailyRecordDto> dailyOlds;
	
	private List<DailyRecordDto> dailyEdits;
	
	private List<DailyRecordDto> dailyOldForLog;
	
	private boolean flagCalculation;
	
	private List<CellEdit> cellEdits;
	
	private Map<Integer, DPAttendanceItem> lstAttendanceItem;
	
	private List<DPDataDto> lstData;
	
    private List<Pair<String, GeneralDate>> lstSidDateDomainError = new ArrayList<>();
	
	private boolean errorAllSidDate;
	
	private List<DPItemValue> lstNotFoundWorkType = new ArrayList<>();
	
	private Boolean showDialogError;
	
	private boolean showFlex;
	
	private boolean checkDailyChange;
	
	private ApprovalConfirmCache approvalConfirmCache;
	
	private Optional<MonthlyRecordWorkDto> domainMonthOpt = Optional.empty();
	
	private ParamCommonAsync paramCommonAsync;
	
	private Boolean checkUnLock;

	public UpdateMonthDailyParam createUpdateMonthDailyParam(){
		return monthValue.createUpdateMonthDailyParam(this.domainMonthOpt, this.dateRange);
	}

	public Set<Pair<String, GeneralDate>> pairSidDateCheck() {
		Set<Pair<String, GeneralDate>> pairSidDateCheck = new HashSet<>();
		dataCheckSign.stream().forEach(x -> {
			pairSidDateCheck.add(Pair.of(x.getEmployeeId(), x.getDate()));
		});

		dataCheckApproval.stream().forEach(x -> {
			pairSidDateCheck.add(Pair.of(x.getEmployeeId(), x.getDate()));
		});
		return pairSidDateCheck;
	}
}
