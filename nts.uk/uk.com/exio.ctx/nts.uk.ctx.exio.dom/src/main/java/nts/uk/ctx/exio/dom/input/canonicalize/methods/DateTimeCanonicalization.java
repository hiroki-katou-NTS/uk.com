package nts.uk.ctx.exio.dom.input.canonicalize.methods;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.input.canonicalize.result.CanonicalItem;
import nts.uk.ctx.exio.dom.input.canonicalize.result.IntermediateResult;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

import java.util.Optional;

/**
 * 年月日と時刻を日時に正準化する
 * CSVの1項目で日時を表現しようとすると仕様が複雑化するので、日時項目は年月日と時刻に分けて受け入れる想定
 */
@Value
@AllArgsConstructor
public class DateTimeCanonicalization {
	/** 年月日の項目No*/
	private final int itemNoDate;
	/** 時分の項目No*/
	private final int itemNoTime;
	/** 秒の項目No*/
	private final int itemNoSecond;
	/** 日時の項目No*/
	private final int itemNoDateTime;

	/**
	 * 渡された編集済みデータを正準化する
	 * @param require
	 * @param interm
	 * @return
	 */
	public IntermediateResult canonicalize(CanonicalizationMethodRequire require, IntermediateResult interm) {

		val date = interm.getItemByNo(itemNoDate).get().getDate();
		int time = (int) (long) interm.getItemByNo(itemNoTime).get().getInt();

		// 秒の既定値は0
		int second = interm.getItemByNo(itemNoSecond)
				.filter(item -> item.getValue() != null)
				.map(item -> item.getJavaInt())
				.orElse(0);

		int hour = time / 60;
		int minute = time % 60;

		val datetime = GeneralDateTime.ymdhms(date.year(), date.month(), date.day(), hour, minute, second);
		val item = CanonicalItem.of(itemNoDateTime, datetime);

		return interm.addCanonicalized(item);
	}
}
