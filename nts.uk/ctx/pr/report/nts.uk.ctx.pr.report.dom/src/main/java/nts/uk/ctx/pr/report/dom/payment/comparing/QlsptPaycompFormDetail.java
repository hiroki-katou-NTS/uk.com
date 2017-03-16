package nts.uk.ctx.pr.report.dom.payment.comparing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QlsptPaycompFormDetail extends AggregateRoot {

	private DispOrder dispOrder;
	
}
