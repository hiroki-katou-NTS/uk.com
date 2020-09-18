package nts.uk.ctx.at.request.dom.application.common.adapter.closure;

import lombok.Builder;
import lombok.Value;
@Value
@Builder

//	現在の締め期間
public class CurrentClosingPeriodExport {
	// 処理年月
	private Integer processingYm;

	// 締め開始日
	private String closureStartDate;

	// 締め終了日
	private String closureEndDate;
	
	}