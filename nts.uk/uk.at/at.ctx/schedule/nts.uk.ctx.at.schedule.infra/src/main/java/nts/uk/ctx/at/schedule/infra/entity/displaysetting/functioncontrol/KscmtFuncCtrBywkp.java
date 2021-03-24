package nts.uk.ctx.at.schedule.infra.entity.displaysetting.functioncontrol;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.*;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import org.apache.commons.lang3.BooleanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.*;

/**
 * KSCMT_FUNC_CTR_BYWKP
 * スケジュール修正職場別の機能制御
 *
 * @author viet.tx
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_FUNC_CTR_BYWKP")
public class KscmtFuncCtrBywkp extends ContractUkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final JpaEntityMapper<KscmtFuncCtrBywkp> MAPPER = new JpaEntityMapper<>(KscmtFuncCtrBywkp.class);

    @Override
    protected Object getKey() {
        return this.companyId;
    }

    /**
     * 会社ID
     */
    @Id
    @Column(name = "CID")
    public String companyId;

    /**
     * 28日周期表示に切り替え可能か : スケジュール修正職場別の機能制御.使用する表示期間
     */
    @Column(name = "DISPLAY_28DAYS")
    public int display28days;

    /**
     * 月末までの1ヶ月間表示に切り替え可能か : スケジュール修正職場別の機能制御.使用する表示期間
     */
    @Column(name = "DISPLAY_1MONTH")
    public int display1month;

    /**
     * 略名モードが使用できるか : スケジュール修正職場別の機能制御.使用する表示形式
     */
    @Column(name = "MODE_ABBR")
    public int modeAbbr;

    /**
     * 勤務モードが使用できるか : スケジュール修正職場別の機能制御.使用する表示形式
     */
    @Column(name = "MODE_FULL")
    public int modeFull;

    /**
     * シフトモードが使用できるか : スケジュール修正職場別の機能制御.使用する表示形式
     */
    @Column(name = "MODE_SHIFT")
    public int modeShift;

    /**
     * スケ修正日付別画面が起動できるか : スケジュール修正職場別の機能制御.起動できる画面
     */
    @Column(name = "OPEN_DISP_BYDATE")
    public int openDispBydate;

    /**
     * スケ修正個人別画面が起動できるか : スケジュール修正職場別の機能制御.起動できる画面
     */
    @Column(name = "OPEN_DISP_BYPERSON")
    public int openDispByperson;

    /**
     * 完了機能を利用するか : スケジュール修正職場別の機能制御.完了利用する区分
     */
    @Column(name = "USE_COMPLETION")
    public int useCompletion;

    /**
     * 完了機能の実行方法 : スケジュール修正職場別の機能制御.完了方法制御.完了実行方法
     */
    @Column(name = "COMPLETION_METHOD")
    public int completionMethod;

    /**
     * 実行と同時に確定するか : スケジュール修正職場別の機能制御.完了方法制御.完了方法制御
     */
    @Column(name = "COMPLETION_AND_DECISION")
    public int completionAndDecision;

    /**
     * 実行と同時にアラームチェックするか : スケジュール修正職場別の機能制御.完了方法制御.完了方法制御
     */
    @Column(name = "COMPLETION_AND_ALCHK")
    public int completionAndAlchk;

    /**
     * Convert to domain
     * @return
     */
    public ScheFunctionCtrlByWorkplace toDomain() {
        List<FuncCtrlDisplayPeriod> lstDisplayPeriod = new ArrayList<>();
        if (this.display28days == 1)
            lstDisplayPeriod.add(FuncCtrlDisplayPeriod.TwentyEightDayCycle);

        if (this.display1month == 1)
            lstDisplayPeriod.add(FuncCtrlDisplayPeriod.LastDayUtil);

        List<FuncCtrlDisplayFormat> lstDisplayFormat = new ArrayList<>();
        if (this.modeAbbr == 1)
            lstDisplayFormat.add(FuncCtrlDisplayFormat.AbbreviatedName);

        if (this.modeFull == 1)
            lstDisplayFormat.add(FuncCtrlDisplayFormat.WorkInfo);

        if (this.modeShift == 1)
            lstDisplayFormat.add(FuncCtrlDisplayFormat.Shift);

        List<FuncCtrlStartControl> lstStartControl = new ArrayList<>();
        if (this.openDispBydate == 1)
            lstStartControl.add(FuncCtrlStartControl.ByDate);

        if (this.openDispByperson == 1)
            lstStartControl.add(FuncCtrlStartControl.ByPerson);

        List<FuncCtrlCompletionMethod> lstCompletionMethod = new ArrayList<>();
        if (this.completionAndDecision == 1)
            lstCompletionMethod.add(FuncCtrlCompletionMethod.Confirm);

        if (this.completionAndAlchk == 1)
            lstCompletionMethod.add(FuncCtrlCompletionMethod.AlarmCheck);

        Optional<CompletionMethodControl> completionMethodCtrl =
                Optional.of(CompletionMethodControl.create(
                        this.completionMethod == 0
                                ? FuncCtrlCompletionExecutionMethod.SelectAtRuntime
                                : FuncCtrlCompletionExecutionMethod.SettingBefore,
                        lstCompletionMethod,
                        Collections.emptyList()
                ));

        return new ScheFunctionCtrlByWorkplace(
                lstDisplayPeriod,
                lstDisplayFormat,
                lstStartControl,
                NotUseAtr.valueOf(this.useCompletion),
                completionMethodCtrl
        );
    }

    /**
     * Convert to entity
     * @param companyId
     * @param domain
     * @return
     */
    public static KscmtFuncCtrBywkp of(String companyId, ScheFunctionCtrlByWorkplace domain) {
        Optional<CompletionMethodControl> completionMethodCtrl = domain.getCompletionMethodControl();

        return new KscmtFuncCtrBywkp(
                companyId
                , BooleanUtils.toInteger(domain.isUseDisplayPeriod(FuncCtrlDisplayPeriod.TwentyEightDayCycle))
                , BooleanUtils.toInteger(domain.isUseDisplayPeriod(FuncCtrlDisplayPeriod.LastDayUtil))
                , BooleanUtils.toInteger(domain.isUseDisplayFormat(FuncCtrlDisplayFormat.AbbreviatedName))
                , BooleanUtils.toInteger(domain.isUseDisplayFormat(FuncCtrlDisplayFormat.WorkInfo))
                , BooleanUtils.toInteger(domain.isUseDisplayFormat(FuncCtrlDisplayFormat.Shift))
                , BooleanUtils.toInteger(domain.isStartControl(FuncCtrlStartControl.ByDate))
                , BooleanUtils.toInteger(domain.isStartControl(FuncCtrlStartControl.ByPerson))
                , domain.getUseCompletionAtr().value
                , (completionMethodCtrl.isPresent() && completionMethodCtrl.get().getCompletionExecutionMethod() != null)
                ? completionMethodCtrl.get().getCompletionExecutionMethod().value
                : null
                , (completionMethodCtrl.isPresent() && !completionMethodCtrl.get().getCompletionMethodControl().isEmpty())
                ? BooleanUtils.toInteger(completionMethodCtrl.get().isCompletionMethodControl(FuncCtrlCompletionMethod.Confirm))
                : null
                , (completionMethodCtrl.isPresent() && !completionMethodCtrl.get().getCompletionMethodControl().isEmpty())
                ? BooleanUtils.toInteger(completionMethodCtrl.get().isCompletionMethodControl(FuncCtrlCompletionMethod.AlarmCheck))
                : null
                );
    }
}
