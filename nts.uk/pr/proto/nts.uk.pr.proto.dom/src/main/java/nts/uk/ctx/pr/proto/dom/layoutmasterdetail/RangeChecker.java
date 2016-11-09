package nts.uk.ctx.pr.proto.dom.layoutmasterdetail;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.gul.util.Range;
public class RangeChecker extends DomainObject {
	//範囲上限
	@Getter
	private boolean isUseHigh;
	//範囲下限
	@Getter
	private boolean isUseLow;
	@Getter
	private Range<?> range;
	public RangeChecker(boolean isUseHigh, boolean isUseLow, Range<?> range) {
		super();
		this.isUseHigh = isUseHigh;
		this.isUseLow = isUseLow;
		this.range = range;
	}
	
}
