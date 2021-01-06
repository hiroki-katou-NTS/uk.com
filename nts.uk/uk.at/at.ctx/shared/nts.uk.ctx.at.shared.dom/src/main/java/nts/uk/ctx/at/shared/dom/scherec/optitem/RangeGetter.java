package nts.uk.ctx.at.shared.dom.scherec.optitem;

import java.math.BigDecimal;
import java.util.Optional;

public interface RangeGetter {

	public Optional<BigDecimal> getUpper(PerformanceAtr performanceAtr);
	
	public Optional<BigDecimal> getLower(PerformanceAtr performanceAtr);
}
