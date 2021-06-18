package nts.uk.ctx.at.function.app.find.indexreconstruction;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.indexreconstruction.ProcExecIndex;
import nts.uk.ctx.at.function.dom.indexreconstruction.repository.ProcExecIndexRepository;

/**
 * The Class ProcExecIndexFinder.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ProcExecIndexFinder {

    /** The repo. */
    @Inject
    private ProcExecIndexRepository repo;

    /**
     * Find by exection id.
     *	ドメインモデル「インデックス再構成結果履歴」を取得する
     * @param executionId the execution id
     * @return the optional
     */
    public ProExecIndexDtoAndNumberTargetTableDto findByExectionId(String executionId) {
        Optional<ProcExecIndex> result = this.repo.findByExecId(executionId);
        return result.map(data -> ProExecIndexDtoAndNumberTargetTableDto
        		.fromProExecIndexDto(ProcExecIndexDto.createFromDomain(data))).orElse(null);
    }

}
