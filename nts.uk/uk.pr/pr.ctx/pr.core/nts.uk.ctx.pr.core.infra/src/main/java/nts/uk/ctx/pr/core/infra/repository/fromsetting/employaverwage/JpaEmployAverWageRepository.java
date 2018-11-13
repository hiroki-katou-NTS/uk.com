package nts.uk.ctx.pr.core.infra.repository.fromsetting.employaverwage;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;


import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.employaverwage.EmployAverWage;
import nts.uk.ctx.pr.core.dom.employaverwage.EmployAverWageRepository;
import nts.uk.ctx.pr.core.infra.entity.fromsetting.employaverwage.QpbmtEmployAverWage;
import nts.uk.ctx.pr.core.infra.entity.fromsetting.employaverwage.QpbmtEmployAverWagePk;

@Stateless
public class JpaEmployAverWageRepository extends JpaRepository implements EmployAverWageRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtEmployAverWage f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.employAverWagePk.employeeId =:employeeId ";

    @Override
    public List<EmployAverWage> getAllEmployAverWage(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtEmployAverWage.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<EmployAverWage> getEmployAverWageById(String employeeId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtEmployAverWage.class)
        .setParameter("employeeId", employeeId)
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
    public void remove(String employeeId){
        this.commandProxy().remove(QpbmtEmployAverWage.class, new QpbmtEmployAverWagePk(employeeId));
    }
}
