package nts.uk.ctx.at.schedule.infra.repository.displaysetting.authcontrol;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyAuthCtrlByPerson;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyAuthCtrlByPersonRepository;
import nts.uk.ctx.at.schedule.infra.entity.displaysetting.authcontrol.KscmtAuthByperson;
import nts.uk.ctx.at.schedule.infra.entity.displaysetting.authcontrol.KscmtAuthBypersonPk;
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
public class JpaScheModifyAuthCtrlByPersonRepository extends JpaRepository implements ScheModifyAuthCtrlByPersonRepository {
    @Override
    public void insert(ScheModifyAuthCtrlByPerson domain) {
        this.commandProxy().insert(KscmtAuthByperson.of(domain));
    }

    @Override
    public void update(ScheModifyAuthCtrlByPerson domain) {
        val pk = new KscmtAuthBypersonPk(domain.getCompanyId(), domain.getRoleId(), domain.getFunctionNo());

        KscmtAuthByperson updateData = this.queryProxy()
                .find(pk, KscmtAuthByperson.class)
                .get();

        updateData.availableAtr = BooleanUtils.toInteger(domain.isAvailable());
        this.commandProxy().update(updateData);
    }

    @Override
    public Optional<ScheModifyAuthCtrlByPerson> get(String companyId, String roleId, int functionNo) {
        String sql = "SELECT * FROM KSCMT_AUTH_BYPERSON"
                + " WHERE CID = @companyId"
                + " AND ROLE_ID = @roleId"
                + "AND FUNCTION_NO = @functionNo";

        return new NtsStatement(sql, this.jdbcProxy())
                .paramString("companyId", companyId)
                .paramString("roleId", roleId)
                .paramInt("functionNo", functionNo)
                .getSingle(x -> KscmtAuthByperson.MAPPER.toEntity(x).toDomain());
    }

    @Override
    public List<ScheModifyAuthCtrlByPerson> getAllByRoleId(String companyId, String roleId) {
        String sql = "SELECT * FROM KSCMT_AUTH_BYPERSON"
                + " WHERE CID = @companyId"
                + " AND ROLE_ID = @roleId";

        return new NtsStatement(sql, this.jdbcProxy())
                .paramString("companyId", companyId)
                .paramString("roleId", roleId)
                .getList(x -> KscmtAuthByperson.MAPPER.toEntity(x).toDomain());
    }
}
