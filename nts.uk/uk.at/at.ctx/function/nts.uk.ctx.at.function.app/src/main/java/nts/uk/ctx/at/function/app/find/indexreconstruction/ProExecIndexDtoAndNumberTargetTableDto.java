package nts.uk.ctx.at.function.app.find.indexreconstruction;

import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class ProExecIndexDtoAndNumberTargetTableDto {

    private ProcExecIndexDto procExecIndexDto;

    private int numberOfTargetTable;

    public static ProExecIndexDtoAndNumberTargetTableDto fromProExecIndexDto(ProcExecIndexDto dto){
        List<String> tablePhysicalNames = dto.getIndexReconstructionResult()
                .stream()
                .map(ProcExecIndexResultDto::getTablePhysicalName)
                .distinct()
                .collect(Collectors.toList());
        return new ProExecIndexDtoAndNumberTargetTableDto(dto,tablePhysicalNames.size());
    }
}
