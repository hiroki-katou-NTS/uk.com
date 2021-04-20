package nts.uk.ctx.at.record.pub.remainnumber.specialleave.export;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 特別休暇残数情報
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class SpecialLeaveRemainingExport {

	/** 特休（マイナスなし） */
	private SpecialLeaveExport specialLeaveNoMinus;
	/** 特休（マイナスあり） */
	private SpecialLeaveExport specialLeaveWithMinus;
	/** 特休未消化数 */
	@Setter
	private Optional<SpecialLeaveUndigestNumberExport> specialLeaveUndigestNumber;

	/**
	 * コンストラクタ
	 */
	public SpecialLeaveRemainingExport(){
		this.specialLeaveNoMinus = new SpecialLeaveExport();
		this.specialLeaveWithMinus = new SpecialLeaveExport();
		this.specialLeaveUndigestNumber = Optional.empty();
	}

}
