package nts.uk.ctx.at.schedule.infra.repository.displaysetting.authcontrol;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyAuthCtrlByWorkPlaceRepository;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyAuthCtrlByWorkplace;
import nts.uk.ctx.at.schedule.infra.entity.displaysetting.authcontrol.KscmtAuthBywkp;
import nts.uk.ctx.at.schedule.infra.entity.displaysetting.authcontrol.KscmtAuthBywkpPk;
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
public class JpaScheModifyAuthCtrlByWorkPlaceRepository extends JpaRepository implements ScheModifyAuthCtrlByWorkPlaceRepository {
    @Override
    public void insert(ScheModifyAuthCtrlByWorkplace domain) {
        this.commandProxy().insert(KscmtAuthBywkp.of(domain));
    }

    @Override
    public void update(ScheModifyAuthCtrlByWorkplace domain) {
        val pk = new KscmtAuthBywkpPk(domain.getCompanyId(), domain.getRoleId(), domain.getFunctionNo());

        KscmtAuthBywkp updateData = this.queryProxy()
                .find(pk, KscmtAuthBywkp.class)
                .get();

        updateData.availableAtr = BooleanUtils.toInteger(domain.isAvailable());
        this.commandProxy().update(updateData);
    }

    @Override
    public Optional<ScheModifyAuthCtrlByWorkplace> get(String companyId, String roleId, int functionNo) {
        String sql = "SELECT * FROM KSCMT_AUTH_BYWKP"
                + " WHERE CID = @companyId"
                + " AND ROLE_ID = @roleId"
                + "AND FUNCTION_NO = @functionNo";

        return new NtsStatement(sql, this.jdbcProxy())
                .paramString("companyId", companyId)
                .paramString("roleId", roleId)
                .paramInt("functionNo", functionNo)
                .getSingle(x -> KscmtAuthBywkp.MAPPER.toEntity(x).toDomain());
    }

    @Override
    public List<ScheModifyAuthCtrlByWorkplace> getAllByRoleId(String companyId, String roleId) {
        String sql = "SELECT * FROM KSCMT_AUTH_BYWKP"
                + " WHERE CID = @companyId"
                + " AND ROLE_ID = @roleId";

        return new NtsStatement(sql, this.jdbcProxy())
                .paramString("companyId", companyId)
                .paramString("roleId", roleId)
                .getList(x -> KscmtAuthBywkp.MAPPER.toEntity(x).toDomain());
    }
}
