package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.estimate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainAggregate;
/**
 * 目安金額の扱い			
 * @author lan_lt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HandingOfEstimateAmount implements DomainAggregate{
	/** 枠別の扱いリスト */
	private List<HandleFrameNo> handleFrameNoList;

}
