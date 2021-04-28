package nts.uk.ctx.at.record.pub.remainnumber.specialleave.export;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 特別休暇
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class SpecialLeaveExport {

	/**
	 * 使用数（特別休暇使用情報）
	 */
	private SpecialLeaveUsedInfoExport usedNumberInfo;

	/**
	 * 残数（特別休暇残数情報）
	 */
	private SpecialLeaveRemainingNumberInfoExport remainingNumberInfo;

	/**
	 * コンストラクタ
	 */
	public SpecialLeaveExport(){

		this.usedNumberInfo = new SpecialLeaveUsedInfoExport();
		this.remainingNumberInfo = new SpecialLeaveRemainingNumberInfoExport();
	}

}
