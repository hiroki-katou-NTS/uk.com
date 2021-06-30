package nts.uk.ctx.at.schedule.infra.repository.displaysetting.authcontrol;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyAuthCtrlCommon;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyAuthCtrlCommonRepository;
import nts.uk.ctx.at.schedule.infra.entity.displaysetting.authcontrol.KscmtAuthCommon;
import nts.uk.ctx.at.schedule.infra.entity.displaysetting.authcontrol.KscmtAuthCommonPk;
import org.apache.commons.lang3.BooleanUtils;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;
import java.util.Optional;

/**
 * @author viet.tx
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaScheModifyAuthCtrlCommonRepository extends JpaRepository implements ScheModifyAuthCtrlCommonRepository {

    @Override
    public void insert(ScheModifyAuthCtrlCommon domain) {
        this.commandProxy().insert(KscmtAuthCommon.of(domain));
    }

    @Override
    public void update(ScheModifyAuthCtrlCommon domain) {
        val pk = new KscmtAuthCommonPk(domain.getCompanyId(), domain.getRoleId(), domain.getFunctionNo());

        KscmtAuthCommon updateData = this.queryProxy()
                .find(pk, KscmtAuthCommon.class)
                .get();

        updateData.availableAtr = BooleanUtils.toInteger(domain.isAvailable());
        this.commandProxy().update(updateData);
    }

    @Override
    public Optional<ScheModifyAuthCtrlCommon> get(String companyId, String roleId, int functionNo) {
        String sql = "SELECT * FROM KSCMT_AUTH_COMMON"
                + " WHERE CID = @companyId"
                + " AND ROLE_ID = @roleId"
                + " AND FUNCTION_NO = @functionNo";

        return new NtsStatement(sql, this.jdbcProxy())
                .paramString("companyId", companyId)
                .paramString("roleId", roleId)
                .paramInt("functionNo", functionNo)
                .getSingle(x -> KscmtAuthCommon.MAPPER.toEntity(x).toDomain());
    }

    @Override
    public List<ScheModifyAuthCtrlCommon> getAllByRoleId(String companyId, String roleId) {
        String sql = "SELECT * FROM KSCMT_AUTH_COMMON"
                + " WHERE CID = @companyId"
                + " AND ROLE_ID = @roleId";

        return new NtsStatement(sql, this.jdbcProxy())
                .paramString("companyId", companyId)
                .paramString("roleId", roleId)
                .getList(x -> KscmtAuthCommon.MAPPER.toEntity(x).toDomain());
    }
}
