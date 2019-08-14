package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empcomworkstlinfor;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomworkstlinfor.CorEmpWorkHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomworkstlinfor.CorEmpWorkHisRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomworkstlinfor.CorWorkFormInfo;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomworkstlinfor.CorWorkFormInfoRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empcomworkstlinfor.QqsmtCorEmpWorkHis;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaCorEmpWorkHisRepository extends JpaRepository implements CorEmpWorkHisRepository, CorWorkFormInfoRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtCorEmpWorkHis f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.corEmpWorkHisPk.empId =:empId";

    @Override
    public Optional<CorEmpWorkHis> getAllCorEmpWorkHisByEmpId(String empId){
        List<QqsmtCorEmpWorkHis> domain = this.queryProxy().query(SELECT_ALL_QUERY_STRING, QqsmtCorEmpWorkHis.class)
                .setParameter("empId", empId)
                .getList();
        return this.toDomain(domain);

    }


    private Optional<CorEmpWorkHis> toDomain(List<QqsmtCorEmpWorkHis> domain){
        if(domain.isEmpty()) return Optional.empty();
        return Optional.of(new CorEmpWorkHis(domain.get(0).corEmpWorkHisPk.empId,domain.stream().map(item -> new YearMonthHistoryItem(item.corEmpWorkHisPk.historyId, new YearMonthPeriod(new YearMonth(item.startYm), new YearMonth(item.endYm)))).collect(Collectors.toList())));
    }

    @Override
    public Optional<CorWorkFormInfo> getCorWorkFormInfoByHisId(String hisId) {
        return Optional.empty();
    }
}
