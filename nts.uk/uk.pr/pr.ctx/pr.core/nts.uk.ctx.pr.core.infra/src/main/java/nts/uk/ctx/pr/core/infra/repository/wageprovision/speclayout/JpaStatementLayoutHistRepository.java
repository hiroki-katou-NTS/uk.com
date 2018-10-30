package nts.uk.ctx.pr.core.infra.repository.wageprovision.speclayout;

import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHist;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHistRepository;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaStatementLayoutHistRepository implements StatementLayoutHistRepository {
    @Override
    public List<StatementLayoutHist> getAllStatementLayoutHist() {
        return null;
    }

    @Override
    public Optional<StatementLayoutHist> getStatementLayoutHistById(String cid, int specCd, String histId) {
        return Optional.empty();
    }

    @Override
    public void add(StatementLayoutHist domain) {

    }

    @Override
    public void update(StatementLayoutHist domain) {

    }

    @Override
    public void remove(String cid, int specCd, String histId) {

    }
}
