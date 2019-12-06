package nts.uk.ctx.at.shared.pub.workrule.closure.workday;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClosureDateOfEmployment {
	// 雇用ｺｰﾄﾞ
	private String employmentCd;
	// 締め日
	private Optional<ClosureDate> closureDate;
}
