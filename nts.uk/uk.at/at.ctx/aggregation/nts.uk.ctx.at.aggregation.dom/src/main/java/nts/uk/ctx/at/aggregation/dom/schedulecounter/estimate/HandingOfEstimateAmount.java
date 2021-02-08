package nts.uk.ctx.at.aggregation.dom.schedulecounter.estimate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
/**
 * 目安金額の扱い			
 * @author lan_lt
 *
 */
@AllArgsConstructor
@Getter
public class HandingOfEstimateAmount implements DomainAggregate{
	/** 枠別の扱いリスト */
	private List<HandleFrameNo> handleFrameNoList;

}
