package nts.uk.ctx.at.function.app.find.indexreconstruction;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.indexreconstruction.ProcExecIndex;
import nts.uk.ctx.at.function.dom.indexreconstruction.repository.ProcExecIndexRepository;

/**
 * The Class ProcExecIndexFinder.
 */
@Stateless
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
    public Optional<ProExecIndexDtoAndNumberTargetTableDto> findByExectionId(String executionId) {
        Optional<ProcExecIndex> result = this.repo.findByExecId(executionId);
        if (result.isPresent()) {
            ProcExecIndexDto procExecIndexDto = ProcExecIndexDto.fromDomain(result.get());
            // 「インデックス再構成結果履歴」と「対象テーブル数」を返す
           return Optional.of(ProExecIndexDtoAndNumberTargetTableDto.fromProExecIndexDto(procExecIndexDto));
        }
        return Optional.empty();
    }

}
