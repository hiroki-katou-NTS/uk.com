package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSetCommand;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.InfoNoBaseDate;
import nts.uk.shr.com.context.AppContexts;

public class InfoNoBaseDateCommand {
	// 残業休日出勤申請の反映
	public AppReflectOtHdWorkCommand overTimeReflect;
	
	// 残業申請設定
	public OvertimeAppSetCommand overTimeAppSet;
	
	// 申請用時間外労働時間パラメータ
	public AgreeOverTimeCommand agreeOverTimeOutput;
	
	// 利用する乖離理由(DivergenceReasonInputMethod at record , so create new class #112406)
	public List<DivergenceReasonInputMethodCommand> divergenceReasonInputMethod;
	
	// 乖離時間枠
	public List<DivergenceTimeRootCommand> divergenceTimeRoot;
	
	public InfoNoBaseDate toDomain() {
		
		
		return new InfoNoBaseDate(
				overTimeReflect.toDomain(),
				overTimeAppSet.toDomain(AppContexts.user().companyId()),
				agreeOverTimeOutput.toDomain(),
				CollectionUtil.isEmpty(divergenceReasonInputMethod) ? 
						Collections.emptyList() :
						divergenceReasonInputMethod.stream()
												   .map(x -> x.toDomain())
												   .collect(Collectors.toList()),
			    CollectionUtil.isEmpty(divergenceTimeRoot) ? 
						Collections.emptyList() :
							divergenceTimeRoot.stream()
												   .map(x -> x.toDomain())
												   .collect(Collectors.toList()));
	}
		
}
