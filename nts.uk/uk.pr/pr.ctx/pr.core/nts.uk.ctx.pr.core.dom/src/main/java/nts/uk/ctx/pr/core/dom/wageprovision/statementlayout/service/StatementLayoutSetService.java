package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.service;

import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutSetRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class StatementLayoutSetService {

    @Inject
    private StatementLayoutSetRepository statementLayoutSetRepo;

    public void addStatementLayoutSet(String statementCode, StatementLayoutSet statementLayoutSet) {
        statementLayoutSetRepo.add(statementCode, statementLayoutSet);
    }

    public void updateStatementLayoutSet(String statementCode, StatementLayoutSet statementLayoutSet) {
        statementLayoutSetRepo.remove(statementLayoutSet.getHistId());
        addStatementLayoutSet(statementCode, statementLayoutSet);
    }
}
