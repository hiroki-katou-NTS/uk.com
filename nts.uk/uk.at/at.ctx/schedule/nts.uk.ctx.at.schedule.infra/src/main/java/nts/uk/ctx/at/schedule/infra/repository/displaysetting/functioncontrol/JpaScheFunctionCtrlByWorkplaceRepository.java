package nts.uk.ctx.at.schedule.infra.repository.displaysetting.functioncontrol;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.*;
import nts.uk.ctx.at.schedule.infra.entity.displaysetting.functioncontrol.KscmtFuncCtrBywkp;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import org.apache.commons.lang3.BooleanUtils;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.Optional;

/**
 * @author viet.tx
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaScheFunctionCtrlByWorkplaceRepository extends JpaRepository implements ScheFunctionCtrlByWorkplaceRepository {
    @Override
    public Optional<ScheFunctionCtrlByWorkplace> get(String companyId) {
        String sql = "SELECT * FROM KSCMT_FUNC_CTR_BYWKP WHERE CID = @companyId";
        return new NtsStatement(sql, this.jdbcProxy())
                .paramString("companyId", companyId)
                .getSingle(x -> KscmtFuncCtrBywkp.MAPPER.toEntity(x).toDomain());
    }

    @Override
    public void insert(String companyId, ScheFunctionCtrlByWorkplace domain) {
        this.commandProxy().insert(KscmtFuncCtrBywkp.of(companyId, domain));
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
    }
}
