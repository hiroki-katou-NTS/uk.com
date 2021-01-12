package nts.uk.ctx.at.function.app.find.indexreconstruction;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.function.dom.indexreconstruction.ProcExecIndex;
import nts.uk.ctx.at.function.dom.indexreconstruction.ProcExecIndexResult;

@Data
public class ProcExecIndexDto implements ProcExecIndex.MementoSetter {

    private String executionId;

    List<ProcExecIndexResultDto> indexReconstructionResult;

	@Override
	public void setIndexReconstructionResult(List<ProcExecIndexResult> indexReconstructionResult) {
		this.indexReconstructionResult = indexReconstructionResult.stream()
				.map(domain -> {
					ProcExecIndexResultDto dto = new ProcExecIndexResultDto();
					domain.setMemento(dto);
					return dto;
				}).collect(Collectors.toList());
	}
	
	public static ProcExecIndexDto createFromDomain(ProcExecIndex domain) {
		ProcExecIndexDto dto = new ProcExecIndexDto();
		domain.setMemento(dto);
		return dto;
	}
}
