package nts.uk.ctx.at.function.dom.holidaysremaining;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author thanh.tq 
 * 出力する特別休暇
 */
@Getter
@Setter
@NoArgsConstructor
public class SpecialHoliday {
	/**
	 * コード
	 */
	private String code;
	
	/**
	 * 出力する
	 */
	private boolean ouput;
}
