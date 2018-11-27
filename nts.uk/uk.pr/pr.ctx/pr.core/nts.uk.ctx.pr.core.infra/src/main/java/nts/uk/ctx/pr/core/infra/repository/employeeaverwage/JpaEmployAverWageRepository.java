package nts.uk.ctx.pr.core.infra.repository.employeeaverwage;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.core.dom.employaverwage.EmployAverWage;
import nts.uk.ctx.pr.core.dom.employaverwage.EmployAverWageRepository;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.infra.entity.fromsetting.employaverwage.QpbmtEmployAverWage;
import nts.uk.ctx.pr.core.infra.entity.fromsetting.employaverwage.QpbmtEmployAverWagePk;

@Stateless
public class JpaEmployAverWageRepository extends JpaRepository implements EmployAverWageRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtEmployAverWage f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.employAverWagePk.employeeId =:employeeId AND  f.employAverWagePk.targetDate =:targetDate ";
    private static final String SELECT_BY_IN_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.employAverWagePk.employeeId IN :employeeIds AND  f.employAverWagePk.targetDate =:targetDate ";


    @Override
    public List<EmployAverWage> getAllEmployAverWage(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtEmployAverWage.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public List<EmployAverWage> getEmployByIds(List<String> employeeIds, int targetDate){
        return this.queryProxy().query(SELECT_BY_IN_ID, QpbmtEmployAverWage.class)
                .setParameter("employeeIds",employeeIds)
                .setParameter("targetDate",targetDate)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<EmployAverWage> getEmployAverWageById(String employeeId, int targetDate){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtEmployAverWage.class)
                .setParameter("employeeId", employeeId)
                .setParameter("targetDate", targetDate)
                .getSingle(c->c.toDomain());
    }

    @Override
    public void add(EmployAverWage domain){
        this.commandProxy().insert(QpbmtEmployAverWage.toEntity(domain));
    }

    @Override
    public void update(EmployAverWage domain){
        this.commandProxy().update(QpbmtEmployAverWage.toEntity(domain));
    }

    @Override
    public void remove(String employeeId, int targetDate){
        this.commandProxy().remove(QpbmtEmployAverWage.class, new QpbmtEmployAverWagePk(employeeId, targetDate));
    }

    @Override
    public void addAll(List<EmployAverWage> domains) {
        this.commandProxy().insertAll(domains.stream().map(x -> QpbmtEmployAverWage.toEntity(x)).collect(Collectors.toList()));
    }

    @Override
    public void updateAll(List<EmployAverWage> domains) {
        this.commandProxy().updateAll(domains.stream().map(x -> QpbmtEmployAverWage.toEntity(x)).collect(Collectors.toList()));
    }

}
