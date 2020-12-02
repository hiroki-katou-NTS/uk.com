package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.estimate;
import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.shr.com.color.ColorCode;
/**
 * 枠別の扱い
 * @author lan_lt
 *
 */
@Value
public class HandleFrameNo implements DomainValue{
	/** 目安金額枠NO */
	private final EstimateAmountNo estimateAmountNo;
	
	/** 背景色 */
	private ColorCode backgroundColor;

}
