package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.service;

import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutSetRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class StatementLayoutSetService {

    @Inject
    private StatementLayoutSetRepository statementLayoutSetRepo;

    public void addStatementLayoutSet(StatementLayoutSet statementLayoutSet) {
        statementLayoutSetRepo.add(statementLayoutSet);
    }

    public void updateStatementLayoutSet(StatementLayoutSet statementLayoutSet) {
        statementLayoutSetRepo.remove(statementLayoutSet.getHistId());
        addStatementLayoutSet(statementLayoutSet);
    }
}
