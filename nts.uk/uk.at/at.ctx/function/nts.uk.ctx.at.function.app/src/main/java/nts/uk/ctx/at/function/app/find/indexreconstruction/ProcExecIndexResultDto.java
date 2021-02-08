package nts.uk.ctx.at.function.app.find.indexreconstruction;

import java.math.BigDecimal;

import lombok.Data;
import nts.uk.ctx.at.function.dom.indexreconstruction.ProcExecIndexResult;

@Data
public class ProcExecIndexResultDto implements ProcExecIndexResult.MementoSetter {

    private String indexName;

    private String tablePhysicalName;

    private BigDecimal fragmentationRate;

    private BigDecimal fragmentationRateAfterProcessing;
}
