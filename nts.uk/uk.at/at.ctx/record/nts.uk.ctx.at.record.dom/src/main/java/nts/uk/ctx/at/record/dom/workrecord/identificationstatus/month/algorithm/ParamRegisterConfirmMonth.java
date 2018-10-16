package nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.algorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Data
@AllArgsConstructor
public class ParamRegisterConfirmMonth {
	private YearMonth yearMonth;
	private List<SelfConfirm> selfConfirm;
	private int closureId;
	private ClosureDate closureDate;
	private GeneralDate indentifyYmd;
}
