package nts.uk.screen.at.app.dailyperformance.correction.month.asynctask;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRecordWorkDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.screen.at.app.dailyperformance.correction.dto.IdentityProcessUseSetDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.cache.DPCorrectionStateParam;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParamCommonAsync implements Serializable {

	private static final long serialVersionUID = 1L;

	String employeeTarget;

	DateRange dateRange;

	String employmentCode;

	Set<String> autBussCode;

	int displayFormat;

	IdentityProcessUseSetDto identityUseSetDto;

	private DPCorrectionStateParam stateParam;

	private Optional<MonthlyRecordWorkDto> domainMonthOpt = Optional.empty();
	
	private boolean loadAfterCalc = false;
}
