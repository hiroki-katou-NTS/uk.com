package nts.uk.ctx.pr.shared.infra.repository.employeeaverwage;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.shared.dom.employaverwage.EmployAverWage;
import nts.uk.ctx.pr.shared.dom.employaverwage.EmployAverWageRepository;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.infra.entity.employaverwage.QqsmtEmployAverWage;
import nts.uk.ctx.pr.shared.infra.entity.employaverwage.QqsmtEmployAverWagePk;

@Stateless
public class JpaEmployAverWageRepository extends JpaRepository implements EmployAverWageRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmployAverWage f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.employAverWagePk.employeeId =:employeeId AND  f.employAverWagePk.targetDate =:targetDate ";
    private static final String SELECT_BY_IN_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.employAverWagePk.employeeId IN :employeeIds AND  f.employAverWagePk.targetDate =:targetDate ";


    @Override
    public List<EmployAverWage> getAllEmployAverWage(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QqsmtEmployAverWage.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public List<EmployAverWage> getEmployByIds(List<String> employeeIds, int targetDate){
        return this.queryProxy().query(SELECT_BY_IN_ID, QqsmtEmployAverWage.class)
                .setParameter("employeeIds",employeeIds)
                .setParameter("targetDate",targetDate)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<EmployAverWage> getEmployAverWageById(String employeeId, int targetDate){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmployAverWage.class)
                .setParameter("employeeId", employeeId)
                .setParameter("targetDate", targetDate)
                .getSingle(c->c.toDomain());
    }

    @Override
    public void add(EmployAverWage domain){
        this.commandProxy().insert(QqsmtEmployAverWage.toEntity(domain));
    }

    @Override
    public void update(EmployAverWage domain){
        this.commandProxy().update(QqsmtEmployAverWage.toEntity(domain));
    }

    @Override
    public void remove(String employeeId, int targetDate){
        this.commandProxy().remove(QqsmtEmployAverWage.class, new QqsmtEmployAverWagePk(employeeId, targetDate));
    }

    @Override
    public void addAll(List<EmployAverWage> domains) {
        this.commandProxy().insertAll(domains.stream().map(x -> QqsmtEmployAverWage.toEntity(x)).collect(Collectors.toList()));
    }

    @Override
    public void updateAll(List<EmployAverWage> domains) {
        this.commandProxy().updateAll(domains.stream().map(x -> QqsmtEmployAverWage.toEntity(x)).collect(Collectors.toList()));
    }

}
