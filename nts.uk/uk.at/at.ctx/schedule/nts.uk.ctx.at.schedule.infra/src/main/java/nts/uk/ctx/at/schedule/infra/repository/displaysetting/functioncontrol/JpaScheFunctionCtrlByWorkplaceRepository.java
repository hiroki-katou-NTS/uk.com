package nts.uk.ctx.at.schedule.infra.repository.displaysetting.functioncontrol;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.*;
import nts.uk.ctx.at.schedule.infra.entity.displaysetting.functioncontrol.KscmtFuncCtrBywkp;
import nts.uk.ctx.at.schedule.infra.entity.displaysetting.functioncontrol.KscmtFuncCtrBywkpAlchkcd;
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
        String subSql = "SELECT * FROM KSCMT_FUNC_CTR_BYWKP_ALCHKCD WHERE CID = @companyId";
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
        KscmtFuncCtrBywkp up = this.queryProxy()
                .find(companyId, KscmtFuncCtrBywkp.class)
                .get();

        up.display28days = BooleanUtils.toInteger(domain.isUseDisplayPeriod(FuncCtrlDisplayPeriod.TwentyEightDayCycle));
        up.display1month = BooleanUtils.toInteger(domain.isUseDisplayPeriod(FuncCtrlDisplayPeriod.LastDayUtil));
        up.modeAbbr = BooleanUtils.toInteger(domain.isUseDisplayFormat(FuncCtrlDisplayFormat.AbbreviatedName));
        up.modeFull = BooleanUtils.toInteger(domain.isUseDisplayFormat(FuncCtrlDisplayFormat.WorkInfo));
        up.modeShift = BooleanUtils.toInteger(domain.isUseDisplayFormat(FuncCtrlDisplayFormat.Shift));
        up.openDispBydate = BooleanUtils.toInteger(domain.isStartControl(FuncCtrlStartControl.ByDate));
        up.openDispByperson = BooleanUtils.toInteger(domain.isStartControl(FuncCtrlStartControl.ByPerson));
        up.useCompletion = domain.getUseCompletionAtr().value;

        Optional<CompletionMethodControl> completionMethodCtrl = domain.getCompletionMethodControl();
        if (completionMethodCtrl.isPresent() && (completionMethodCtrl.get().getCompletionExecutionMethod() != null))
            up.completionMethod = completionMethodCtrl.get().getCompletionExecutionMethod().value;
        if (completionMethodCtrl.isPresent() && !completionMethodCtrl.get().getCompletionMethodControl().isEmpty())
            up.completionAndDecision = BooleanUtils.toInteger(completionMethodCtrl.get().isCompletionMethodControl(FuncCtrlCompletionMethod.Confirm));
        if (completionMethodCtrl.isPresent() && !completionMethodCtrl.get().getCompletionMethodControl().isEmpty())
            up.completionAndAlchk = BooleanUtils.toInteger(completionMethodCtrl.get().isCompletionMethodControl(FuncCtrlCompletionMethod.AlarmCheck));

        this.commandProxy().update(up);

        // Update KSCMT_FUNC_CTR_USE_WKTP
        this.commandProxy().updateAll(KscmtFuncCtrBywkpAlchkcd.toEntities(companyId, domain));
    }
}
