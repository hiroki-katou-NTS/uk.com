package nts.uk.ctx.at.function.dom.holidaysremaining;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author thanh.tq 出力する子の看護休暇の項目
 */
@Getter
@Setter
@NoArgsConstructor
public class ChildNursingLeave {
	/**
	 * childNursingLeave
	 */
	private boolean ChildNursingLeave;

	public ChildNursingLeave(boolean childNursingLeave) {
		super();
		ChildNursingLeave = childNursingLeave;
	}
	

}
