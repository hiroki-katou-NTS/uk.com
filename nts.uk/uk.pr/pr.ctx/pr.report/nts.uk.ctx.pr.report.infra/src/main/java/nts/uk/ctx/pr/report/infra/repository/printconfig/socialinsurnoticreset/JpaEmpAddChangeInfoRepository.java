package nts.uk.ctx.pr.report.infra.repository.printconfig.socialinsurnoticreset;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpAddChangeInfo;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpAddChangeInfoRepository;
import nts.uk.ctx.pr.report.infra.entity.printconfig.socialinsurnoticreset.QrsmtEmpAddCgeSetting;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;

public class JpaEmpAddChangeInfoRepository extends JpaRepository implements EmpAddChangeInfoRepository {

    private static String EMP_ADD_CHANGE = "SELECT f FROM QrsmtEmpAddCgeSetting f WHERE f.empAddCgeSettingPk.cid = :cid AND f.empAddCgeSettingPk.sid IN :empIds";

    @Override
    public List<EmpAddChangeInfo> getListEmpAddChange(List<String> empIds) {
        return this.queryProxy().query(EMP_ADD_CHANGE, QrsmtEmpAddCgeSetting.class)
                .setParameter("empIds", empIds)
                .setParameter("cid", AppContexts.user().companyId())
                .getList(x -> x.toDomain());
    }
}
