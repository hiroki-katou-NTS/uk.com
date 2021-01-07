package nts.uk.ctx.at.request.dom.application.overtime.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetailOutput {
	// 残業申請の表示情報
	private DisplayInfoOverTime displayInfoOverTime;
	// 残業申請
	private AppOverTime appOverTime;

}
