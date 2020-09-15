package nts.uk.screen.at.app.ktgwidget.find.dto;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AgreementExcessInfo;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementExcessInfoImport;

@Data
@Builder
public class YearAndEmpOtHoursDto {
	/** 年月ごとの時間外時間*/
	AgreementExcessInfoImport agreeInfoImp;
	
	/** 36協定超過情報 */
	AgreementExcessInfo agreeInfo;
	
}
