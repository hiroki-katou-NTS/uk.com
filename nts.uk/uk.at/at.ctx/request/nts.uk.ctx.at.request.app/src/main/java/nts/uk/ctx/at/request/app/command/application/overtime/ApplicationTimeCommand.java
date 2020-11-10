package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.Collections;
import java.util.List;

import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;

public class ApplicationTimeCommand {
	// 申請時間
	public List<OvertimeApplicationSettingCommand> applicationTime = Collections.emptyList();
	// フレックス超過時間
	public Integer flexOverTime;
	// 就業時間外深夜時間
	public OverTimeShiftNightCommand overTimeShiftNight;
	// 任意項目
	public List<AnyItemValueCommand> anyItem;
	// 乖離理由
	public List<ReasonDivergenceCommand> reasonDissociation;
	
	public ApplicationTime toDomain() {
		return null;
	}
}
