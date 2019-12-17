package nts.uk.ctx.pereg.dom.workrule.closure;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Builder
@Data
public class ClosureDateOfEmploymentImport {
	// 雇用ｺｰﾄﾞ
	private String employmentCd;
	// 締め日
	private Optional<ClosureDate> closureDate;
}
