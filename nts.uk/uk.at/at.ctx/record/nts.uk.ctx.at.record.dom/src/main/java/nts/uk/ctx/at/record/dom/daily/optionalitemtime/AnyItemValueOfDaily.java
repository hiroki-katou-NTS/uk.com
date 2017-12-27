package nts.uk.ctx.at.record.dom.daily.optionalitemtime;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 日別実績の任意項目*/
@Getter
@AllArgsConstructor
public class AnyItemValueOfDaily {

	/** 任意項目値: 任意項目値 */
	private List<AnyItemValue> items;
}
