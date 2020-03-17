package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.dto.ShiftMasterDto;

/**
 * @author anhdt
 * 【Output】
	・List<シフトマスタ>
	・List<勤務種類>
	・List<就業時間帯の設定>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ksm015StartPageDto {
	List<ShiftMasterDto> shiftMasters;
	String forAttendent;
	List<String> alreadyConfigWorkplaces;
}
