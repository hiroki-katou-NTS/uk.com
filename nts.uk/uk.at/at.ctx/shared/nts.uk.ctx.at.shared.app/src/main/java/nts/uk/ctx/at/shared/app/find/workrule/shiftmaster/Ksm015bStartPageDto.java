package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import java.util.List;

import lombok.Data;

/**
 * @author anhdt
 * 【Output】
	・List<シフトマスタ>
	・List<勤務種類>
	・List<就業時間帯の設定>
 */
@Data
public class Ksm015bStartPageDto {
	List<ShiftMasterDto> shiftMasters;
	List<WorkTypeDto> workTypes;
	List<WorkTimeSettingDto> workTimeSettings;
}
