package nts.uk.ctx.at.function.app.find.indexreconstruction;

import lombok.Value;
import nts.uk.ctx.at.function.dom.indexreconstruction.ProcExecIndex;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class ProcExecIndexDto {

    private String executionId;

    List<ProcExecIndexResultDto> indexReconstructionResult;

    public static ProcExecIndexDto fromDomain(ProcExecIndex domain){
        return new ProcExecIndexDto(domain.getExecutionId().v(),
                domain.getIndexReconstructionResult().stream().map(r -> ProcExecIndexResultDto.fromDomain(r)).collect(Collectors.toList()));
    }
}
