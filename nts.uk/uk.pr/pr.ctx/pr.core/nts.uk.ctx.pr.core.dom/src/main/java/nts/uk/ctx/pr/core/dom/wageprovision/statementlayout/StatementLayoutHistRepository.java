package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

import java.util.Optional;
import java.util.List;

/**
* 明細書レイアウト履歴
*/
<<<<<<< HEAD:nts.uk/uk.pr/pr.ctx/pr.core/nts.uk.ctx.pr.core.dom/src/main/java/nts/uk/ctx/pr/core/dom/wageprovision/statementlayout/StatementLayoutHistRepository.java
public interface StatementLayoutHistRepository
{
=======
public interface SpecificationLayoutHistRepository {
>>>>>>> QMM020:nts.uk/uk.pr/pr.ctx/pr.core/nts.uk.ctx.pr.core.dom/src/main/java/nts/uk/ctx/pr/core/dom/wageprovision/speclayout/SpecificationLayoutHistRepository.java

    List<StatementLayoutHist> getAllStatementLayoutHist();

    Optional<StatementLayoutHist> getStatementLayoutHistById(String cid, int specCd, String histId);

<<<<<<< HEAD:nts.uk/uk.pr/pr.ctx/pr.core/nts.uk.ctx.pr.core.dom/src/main/java/nts/uk/ctx/pr/core/dom/wageprovision/statementlayout/StatementLayoutHistRepository.java
    void add(StatementLayoutHist domain);
=======
    List<String> getSpecCode(String cid, String salaryCd, int startYearMonth);

    void add(SpecificationLayoutHist domain);
>>>>>>> QMM020:nts.uk/uk.pr/pr.ctx/pr.core/nts.uk.ctx.pr.core.dom/src/main/java/nts/uk/ctx/pr/core/dom/wageprovision/speclayout/SpecificationLayoutHistRepository.java

    void update(StatementLayoutHist domain);

    void remove(String cid, int specCd, String histId);

}
