package nts.uk.ctx.at.function.app.find.indexreconstruction;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;

@Value
public class ProExecIndexDtoAndNumberTargetTableDto {

    /** The number of target table. */
    private int numberOfTargetTable;
    
    /** The execution id. */
    private String executionId;
    
    /** The index reconstruction result. */
    List<ProcExecIndexResultDto> indexReconstructionResult;

    /**
     * From pro exec index dto.
     *
     * @param dto the dto
     * @return the pro exec index dto and number target table dto
     */
    public static ProExecIndexDtoAndNumberTargetTableDto fromProExecIndexDto(ProcExecIndexDto dto){
    	// 「対象テーブル数」をカウントする
        List<String> tablePhysicalNames = dto.getIndexReconstructionResult()
                .stream()
                .map(ProcExecIndexResultDto::getTablePhysicalName)
                .distinct()
                .collect(Collectors.toList());
        return new ProExecIndexDtoAndNumberTargetTableDto(tablePhysicalNames.size(),dto.getExecutionId(),dto.getIndexReconstructionResult());
    }
}
