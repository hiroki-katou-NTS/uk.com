package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.InfoBaseDateOutput;
import nts.uk.ctx.at.shared.app.command.worktype.WorkTypeCommandBase;
import nts.uk.shr.com.context.AppContexts;

public class InfoBaseDateOutputCommand {
	// 勤務種類リスト
	public List<WorkTypeCommandBase> worktypes;
	// 残業申請で利用する残業枠
	public QuotaOuputCommand quotaOutput;
	
	public InfoBaseDateOutput toDomain() {
		
		return new InfoBaseDateOutput(
				CollectionUtil.isEmpty(worktypes) 
				? Collections.emptyList() : 
					worktypes.stream()
							 .map(x-> x.toDomain(AppContexts.user().companyId()))
							 .collect(Collectors.toList()),
				quotaOutput.toDomain());
	}
}
