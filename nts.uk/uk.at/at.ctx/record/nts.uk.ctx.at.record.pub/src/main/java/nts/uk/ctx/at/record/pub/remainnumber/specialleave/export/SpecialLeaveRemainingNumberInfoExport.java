package nts.uk.ctx.at.record.pub.remainnumber.specialleave.export;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * 特別休暇残数情報
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class SpecialLeaveRemainingNumberInfoExport {

	/** 合計 */
	private SpecialLeaveRemainingNumberExport remainingNumber;

	/** 付与前 */
	private SpecialLeaveRemainingNumberExport remainingNumberBeforeGrant;

	/** 付与後 */
	private Optional<SpecialLeaveRemainingNumberExport> remainingNumberAfterGrantOpt;

	/** コンストラクタ  */
	public SpecialLeaveRemainingNumberInfoExport(){
		this.remainingNumber = new SpecialLeaveRemainingNumberExport();
		this.remainingNumberBeforeGrant = new SpecialLeaveRemainingNumberExport();
		this.remainingNumberAfterGrantOpt = Optional.empty();
	}

}
