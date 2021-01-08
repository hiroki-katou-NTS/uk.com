package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;

public class ApplicationTimeCommand {
	// 申請時間
	public List<OvertimeApplicationSettingCommand> applicationTime;
	// フレックス超過時間
	public Integer flexOverTime;
	// 就業時間外深夜時間
	public OverTimeShiftNightCommand overTimeShiftNight;
	// 任意項目
	public List<AnyItemValueCommand> anyItem;
	// 乖離理由
	public List<ReasonDivergenceCommand> reasonDissociation;
	
	public ApplicationTime toDomain() {
		
		return new ApplicationTime(
				CollectionUtil.isEmpty(applicationTime) ? 
						Collections.emptyList() :
							applicationTime.stream()
							.map(x -> x.toDomain())
							.collect(Collectors.toList()),
				flexOverTime != null ? Optional.of(new AttendanceTimeOfExistMinus(flexOverTime)) : Optional.empty(),
				overTimeShiftNight!= null ? Optional.of(overTimeShiftNight.toDomain()) : Optional.empty(),
				CollectionUtil.isEmpty(anyItem) ? 
						Optional.empty() :
							Optional.ofNullable(
									anyItem.stream()
										.map(x -> x.toDomain())
										.collect(Collectors.toList())),
				CollectionUtil.isEmpty(reasonDissociation) ? 
						Optional.empty() :
							Optional.ofNullable(reasonDissociation.stream()
								.map(x -> x.toDomain())
								.collect(Collectors.toList())));
	}
}
