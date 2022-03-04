package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sakuratani
 *
 *         項目値
 */
@Getter
@AllArgsConstructor
public class ItemValue {

	//時間
	private int time;

	//金額
	private long amount;

	//色
	private Optional<String> color;

}