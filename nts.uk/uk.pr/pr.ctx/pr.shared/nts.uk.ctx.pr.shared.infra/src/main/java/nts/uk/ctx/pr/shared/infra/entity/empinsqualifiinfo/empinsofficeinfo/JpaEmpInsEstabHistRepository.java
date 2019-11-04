package nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.empinsofficeinfo;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsHistRepository;

import javax.ejb.Stateless;

@Stateless
public class JpaEmpInsEstabHistRepository extends JpaRepository implements EmpInsHistRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM  f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f..cid =:cid AND  f..sid =:sid AND  f..histId =:histId ";


}
