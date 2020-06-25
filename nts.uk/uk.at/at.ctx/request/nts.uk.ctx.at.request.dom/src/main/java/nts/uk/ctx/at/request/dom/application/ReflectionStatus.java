package nts.uk.ctx.at.request.dom.application;

import java.util.List;

import lombok.Getter;

/**
 * refactor 4
 * 反映状態
 * @author Doan Duy Hung
 *
 */
@Getter
public class ReflectionStatus {
	
	/**
	 * 対象日の反映状態
	 */
	private List<ReflectionStatusOfDay> listReflectionStatusOfDay;
	
}
