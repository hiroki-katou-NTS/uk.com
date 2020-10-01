package nts.uk.ctx.at.function.app.find.indexreconstruction;

import lombok.Value;
import nts.uk.ctx.at.function.dom.indexreconstruction.ProcExecIndexResult;

import java.math.BigDecimal;

@Value
public class ProcExecIndexResultDto {

    private String indexName;

    private String tablePhysicalName;

    private BigDecimal fragmentationRate;

    private BigDecimal fragmentationRateAfterProcessing;

    public static ProcExecIndexResultDto fromDomain(ProcExecIndexResult domain){
        return new ProcExecIndexResultDto(
                domain.getIndexName().v(),
                domain.getTablePhysicalName().v(),
                domain.getFragmentationRate().v(),
                domain.getFragmentationRateAfterProcessing().v());
    }
}
