package nts.uk.ctx.at.record.dom.daily.breaktimegoout;

import java.util.List;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.AcquisitionConditionsAtr;

/**
 * 日別実績の外出時間帯
 * @author keisuke_hoshina
 *
 */
@Value
@EqualsAndHashCode(callSuper=false)
public class GoOutTimeSheetOfDailyWork extends AggregateRoot{

	private List<GoOutTimeSheet> timeSheet;

	/**
	 * 取得条件区分を基に外出時間の不要項目の削除
	 * (区分 = 控除なら　外出理由 = 私用or組合のみのリストへ)
	 * @param 取得条件区分 
	 * @return 不要な項目を削除した時間帯
	 */
	public List<GoOutTimeSheet> RemoveUnuseItemBaseOnAtr(AcquisitionConditionsAtr acqAtr ) {
		
		if(acqAtr.isForDeduction()) {
			return timeSheet.stream()
					 .filter(tg -> tg.getGoOutReason().isPrivateOrUnion())
					 .collect(Collectors.toList());
		}
		return timeSheet;
	}
}
