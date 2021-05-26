package nts.uk.ctx.at.record.pub.remainnumber.specialleave.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 特別休暇残数
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class SpecialLeaveRemainingNumberExport {

	/** 合計残日数 */
	public Double dayNumberOfRemain;

	/** 合計残時間 */
	public Optional<Integer> timeOfRemain;

	/** 明細 */
	private List<SpecialLeaveRemainingDetailExport> details;

	public SpecialLeaveRemainingNumberExport() {
		this.dayNumberOfRemain=0.0;
		this.timeOfRemain=Optional.empty();
		this.details=new ArrayList<>();
	}

}
