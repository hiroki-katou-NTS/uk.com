package nts.uk.ctx.at.record.pub.remainnumber.specialleave.export;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 特別休暇使用情報
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class SpecialLeaveUsedInfoExport {

	/** 合計 */
	private SpecialLeaveUseNumberExport usedNumber;

	/** 付与前 */
	private SpecialLeaveUseNumberExport usedNumberBeforeGrant;

	/** 特休使用回数 （1日2回使用した場合２回でカウント）*/
	private Integer specialLeaveUsedTimes;

	/** 特休使用日数 （1日2回使用した場合１回でカウント） */
	private Integer specialLeaveUsedDayTimes;

	/** 付与後 */
	private Optional<SpecialLeaveUseNumberExport> usedNumberAfterGrantOpt;

	/** コンストラクタ */
	public SpecialLeaveUsedInfoExport(){
		usedNumber = new SpecialLeaveUseNumberExport ();
		usedNumberBeforeGrant = new SpecialLeaveUseNumberExport ();
		specialLeaveUsedTimes = 0;
		specialLeaveUsedDayTimes = 0;
		usedNumberAfterGrantOpt = Optional.empty();
	}

}
