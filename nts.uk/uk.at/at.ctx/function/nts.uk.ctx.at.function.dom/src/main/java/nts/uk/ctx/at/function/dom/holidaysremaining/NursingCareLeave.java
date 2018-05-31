package nts.uk.ctx.at.function.dom.holidaysremaining;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author thanh.tq 
 * 出力する介護休暇の項目
 */
@Getter
@Setter
@NoArgsConstructor
public class NursingCareLeave {
	/**
	 * 介護休暇の項目を出力する
	 */
	private boolean nursingLeave;

	public NursingCareLeave(boolean nursingLeave) {
		super();
		this.nursingLeave = nursingLeave;
	}
	
	
}
