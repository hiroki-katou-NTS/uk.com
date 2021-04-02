package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.command.application.common.AppDispInfoStartupCmd;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.CalculatedFlag;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.application.overtime.service.DisplayInfoOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkInfo;

public class DisplayInfoOverTimeCommand {
	// 基準日に関する情報
	public InfoBaseDateOutputCommand infoBaseDateOutput;
	// 基準日に関係しない情報
	public InfoNoBaseDateCommand infoNoBaseDate;
	// 休出枠
	public List<WorkdayoffFrameCommand> workdayoffFrames;
	// 残業申請区分
	public Integer overtimeAppAtr;
	// 申請表示情報
	public AppDispInfoStartupCmd appDispInfoStartup;
	// 計算結果
	public CalculationResultCommand calculationResultOp;
	// 申請日に関係する情報
	public InfoWithDateApplicationCommand infoWithDateApplicationOp;
	// 計算済フラグ
	public Integer calculatedFlag;
	
	public WorkInfo workInfo;
	
	public DisplayInfoOverTime toDomain() {
		
		return new DisplayInfoOverTime(
				infoBaseDateOutput.toDomain(),
				infoNoBaseDate.toDomain(),
				CollectionUtil.isEmpty(workdayoffFrames) ? 
						Collections.emptyList() : 
							workdayoffFrames.stream()
											.map(x -> x.toDomain())
											.collect(Collectors.toList()),
				EnumAdaptor.valueOf(overtimeAppAtr, OvertimeAppAtr.class),
				appDispInfoStartup.toDomain(),
				calculationResultOp == null ? Optional.empty() : Optional.of(calculationResultOp.toDomain()),
				infoWithDateApplicationOp == null ? Optional.empty() : Optional.of(infoWithDateApplicationOp.toDomain()),
				EnumAdaptor.valueOf(calculatedFlag, CalculatedFlag.class),
				workInfo
				);
	}
}
