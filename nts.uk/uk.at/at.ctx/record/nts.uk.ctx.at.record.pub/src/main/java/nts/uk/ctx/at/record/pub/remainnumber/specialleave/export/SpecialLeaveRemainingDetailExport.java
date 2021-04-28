package nts.uk.ctx.at.record.pub.remainnumber.specialleave.export;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * 特別休暇残明細
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class SpecialLeaveRemainingDetailExport {

	/** 付与日 */
	private GeneralDate grantDate;
	/** 日数 */
	private Double days;
	/** 時間 */
	private Optional<Integer> time;

	/**
	 * コンストラクタ
	 * @param grantDate 付与日
	 */
	public SpecialLeaveRemainingDetailExport(GeneralDate grantDate){
		this.grantDate = grantDate;
		this.days = 0.0;
		this.time = Optional.empty();
	}
}
