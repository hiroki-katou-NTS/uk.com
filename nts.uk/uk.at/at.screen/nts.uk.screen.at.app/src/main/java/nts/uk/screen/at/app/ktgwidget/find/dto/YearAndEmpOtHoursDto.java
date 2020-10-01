package nts.uk.screen.at.app.ktgwidget.find.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AgreementExcessInfo;

@Data
@Builder
public class YearAndEmpOtHoursDto {
	/** 年月ごとの時間外時間*/
	List<YearMonthOvertime> overtimeHours;
	
	/** 36協定超過情報 */
	int agreeInfo;
	
}
