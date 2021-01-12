package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.QuotaOuput;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;


public class QuotaOuputCommand {
	// フレックス時間表示区分
	public Boolean flexTimeClf;
	// 残業枠一覧
	public List<OvertimeWorkFrameCommand> overTimeQuotaList;
	
	
	public QuotaOuput toDomain() {	
		return new QuotaOuput(
				flexTimeClf,
				CollectionUtil.isEmpty(overTimeQuotaList) ? Collections.emptyList()
						: overTimeQuotaList.stream()
										   .map(x -> new OvertimeWorkFrame(x))
										   .collect(Collectors.toList()));
	}
}
