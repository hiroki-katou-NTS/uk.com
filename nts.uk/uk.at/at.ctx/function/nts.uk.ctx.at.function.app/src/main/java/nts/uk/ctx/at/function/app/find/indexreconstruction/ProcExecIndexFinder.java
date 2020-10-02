package nts.uk.ctx.at.function.app.find.indexreconstruction;

import nts.uk.ctx.at.function.dom.indexreconstruction.ProcExecIndex;
import nts.uk.ctx.at.function.dom.indexreconstruction.repository.ProcExecIndexRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class ProcExecIndexFinder {

    @Inject
    private ProcExecIndexRepository repo;

    public Optional<ProExecIndexDtoAndNumberTargetTableDto> findByExectionId(String executionId){
        Optional<ProcExecIndex> result = this.repo.findByExecId(executionId);
        if (result.isPresent()) {
            ProcExecIndexDto procExecIndexDto = ProcExecIndexDto.fromDomain(result.get());
           return Optional.of(ProExecIndexDtoAndNumberTargetTableDto.fromProExecIndexDto(procExecIndexDto));
        }
        return Optional.empty();
    }

}
