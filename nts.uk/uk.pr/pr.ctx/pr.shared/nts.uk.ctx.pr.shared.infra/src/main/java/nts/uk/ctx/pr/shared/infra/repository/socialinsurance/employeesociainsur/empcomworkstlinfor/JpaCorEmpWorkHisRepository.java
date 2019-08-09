package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empcomworkstlinfor;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomworkstlinfor.CorEmpWorkHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomworkstlinfor.CorEmpWorkHisRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomworkstlinfor.CorWorkFormInfoRepository;

import javax.ejb.Stateless;

@Stateless
public class JpaCorEmpWorkHisRepository extends JpaRepository implements CorEmpWorkHisRepository, CorWorkFormInfoRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtCorEmpWorkHis f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.corEmpWorkHisPk.empId =:empId";

    @Override
    public CorEmpWorkHis getAllCorEmpWorkHis(){
        return null;
    }
}
