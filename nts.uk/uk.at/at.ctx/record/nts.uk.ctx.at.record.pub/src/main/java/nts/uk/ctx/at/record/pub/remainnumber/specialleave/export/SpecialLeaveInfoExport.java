package nts.uk.ctx.at.record.pub.remainnumber.specialleave.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * 特別休暇情報
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class SpecialLeaveInfoExport {

	/** 年月日 */
	private GeneralDate ymd;
	/** 残数 */
	private SpecialLeaveRemainingExport remainingNumber;
	/** 付与残数データ */
	private List<SpecialLeaveGrantRemainingDataExport> grantRemainingDataList;

	/**
	 * コンストラクタ
	 */
	public SpecialLeaveInfoExport(){
		this.ymd = GeneralDate.min();
		this.remainingNumber = new SpecialLeaveRemainingExport();
		this.grantRemainingDataList = new ArrayList<>();
	}
}
