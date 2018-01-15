package nts.uk.ctx.at.record.dom.divergencetimeofdaily;

import java.util.List;

import lombok.Getter;

/**
 * 
 * @author nampt
 * 日別実績の乖離時間
 *
 */
@Getter
public class DivergenceTimeOfDaily {
	
	/** 乖離時間 */
	private List<DivergenceTime> divergenceTime;

}
