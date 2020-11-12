package nts.uk.ctx.at.shared.dom.application.common;

import java.util.List;

import lombok.Getter;

/**
 * 
 * 反映状態
 * @author thanh_nx
 *
 */
@Getter
public class ReflectionStatusShare {
	
	/**
	 * 対象日の反映状態
	 */
	private List<ReflectionStatusOfDayShare> listReflectionStatusOfDay;
	
	public ReflectionStatusShare(List<ReflectionStatusOfDayShare> listReflectionStatusOfDay) {
		this.listReflectionStatusOfDay = listReflectionStatusOfDay;
	}
	
}
