package nts.uk.ctx.at.schedule.infra.repository.displaysetting.functioncontrol;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.*;
import nts.uk.ctx.at.schedule.infra.entity.displaysetting.functioncontrol.KscmtFuncCtrBywkp;
import nts.uk.ctx.at.schedule.infra.entity.displaysetting.functioncontrol.KscmtFuncCtrBywkpAlchkcd;
import nts.uk.shr.com.enumcommon.NotUseAtr;
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
public class JpaScheFunctionCtrlByWorkplaceRepository extends JpaRepository implements ScheFunctionCtrlByWorkplaceRepository {
    @Override
    public Optional<ScheFunctionCtrlByWorkplace> get(String companyId) {
        // get child list
        String subSql = "SELECT * FROM KSCMT_FUNC_CTR_BYWKP_ALCHKCD WHERE CID = @companyId ORDER BY ALCHK_CD";
        List<String> lstAlarmCheckCode = new NtsStatement(subSql, this.jdbcProxy())
                .paramString("companyId", companyId)
                .getList(x -> KscmtFuncCtrBywkpAlchkcd.MAPPER.toEntity(x).getAlchkCd());

        String sql = "SELECT * FROM KSCMT_FUNC_CTR_BYWKP WHERE CID = @companyId";
        return new NtsStatement(sql, this.jdbcProxy())
                .paramString("companyId", companyId)
                .getSingle(x -> KscmtFuncCtrBywkp.MAPPER.toEntity(x).toDomain(lstAlarmCheckCode));
    }

    @Override
    public void insert(String companyId, ScheFunctionCtrlByWorkplace domain) {
        //Insert KSCMT_FUNC_CTR
        this.commandProxy().insert(KscmtFuncCtrBywkp.of(companyId, domain));

        // Insert KSCMT_FUNC_CTR_USE_WKTP
        this.commandProxy().insertAll(KscmtFuncCtrBywkpAlchkcd.toEntities(companyId, domain));
    }

    @Override
    public void update(String companyId, ScheFunctionCtrlByWorkplace domain) {
        this.queryProxy().find(companyId, KscmtFuncCtrBywkp.class).ifPresent(entity -> {
            entity.display28days = BooleanUtils.toInteger(domain.isUseDisplayPeriod(FuncCtrlDisplayPeriod.TwentyEightDayCycle));
            entity.display1month = BooleanUtils.toInteger(domain.isUseDisplayPeriod(FuncCtrlDisplayPeriod.LastDayUtil));
            entity.modeAbbr = BooleanUtils.toInteger(domain.isUseDisplayFormat(FuncCtrlDisplayFormat.AbbreviatedName));
            entity.modeFull = BooleanUtils.toInteger(domain.isUseDisplayFormat(FuncCtrlDisplayFormat.WorkInfo));
            entity.modeShift = BooleanUtils.toInteger(domain.isUseDisplayFormat(FuncCtrlDisplayFormat.Shift));
            entity.openDispBydate = BooleanUtils.toInteger(domain.isStartControl(FuncCtrlStartControl.ByDate));
            entity.openDispByperson = BooleanUtils.toInteger(domain.isStartControl(FuncCtrlStartControl.ByPerson));
            entity.useCompletion = domain.getUseCompletionAtr().value;

            domain.getCompletionMethodControl().ifPresent(completionControl -> {
                entity.completionMethod = completionControl.getCompletionExecutionMethod().value;
                entity.completionAndDecision = BooleanUtils.toInteger(completionControl.isCompletionMethodControl(FuncCtrlCompletionMethod.Confirm));
                entity.completionAndAlchk = BooleanUtils.toInteger(completionControl.isCompletionMethodControl(FuncCtrlCompletionMethod.AlarmCheck));
            });

            this.commandProxy().update(entity);
        });

        // Update KSCMT_FUNC_CTR_USE_WKTP
        if (domain.getUseCompletionAtr() == NotUseAtr.USE && domain.getCompletionMethodControl().isPresent()) {
            String query = "select c from KscmtFuncCtrBywkpAlchkcd c where c.pk.cid = :cid";
            List<KscmtFuncCtrBywkpAlchkcd> oldWktps = this.queryProxy().query(query, KscmtFuncCtrBywkpAlchkcd.class)
                    .setParameter("cid", companyId)
                    .getList();
            this.commandProxy().removeAll(oldWktps);
            this.getEntityManager().flush();
            this.commandProxy().insertAll(KscmtFuncCtrBywkpAlchkcd.toEntities(companyId, domain));
        }
    }
}
