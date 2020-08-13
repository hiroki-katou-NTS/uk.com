package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.appovertime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.ApplicationDetailSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSet;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.KrqstAppType;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業申請設定.残業申請設定
 */
@Entity
@Table(name = "KRQST_APP_OVERTIME")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqstAppOvertime extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CID")
    private String companyId;

    @Column(name = "PRE_EXCESS_ATR")
    private int preExcessAtr;

    @Column(name = "EXTRATIME_EXCESS_ATR")
    private int extraTimeExcessAtr;

    @Column(name = "EXTRATIME_DISPLAY_ATR")
    private int extraTimeDisplayAtr;

    @Column(name = "ATD_EXCESS_ATR")
    private int atdExcessAtr;

    @Column(name = "ATD_EXCESS_OVERRIDE_ATR")
    private int atdExcessOverrideAtr;

    @Column(name = "INSTRUCT_EXCESS_ATR")
    private int instructExcessAtr;

    @Column(name = "DVGC_EXCESS_ATR")
    private int dvgcExcessAtr;

    @Column(name = "INSTRUCT_REQUIRED_ATR")
    private int instructRequiredAtr;

    @Column(name = "PRE_REQUIRED_ATR")
    private int preRequiredAtr;

    @Column(name = "TIME_INPUT_USE_ATR")
    private int timeInputUseAtr;

    @Column(name = "TIME_CAL_USE_ATR")
    private int timeCalUseAtr;

    @Column(name = "WORK_TIME_INI_ATR")
    private int workTimeIniAtr;

    @Column(name = "END_WORK_TIME_INI_ATR")
    private int endWorkTimeIniAtr;

    @Column(name = "PRE_WORK_REFLECT_ATR")
    private int preWorkReflectAtr;

    @Column(name = "PRE_INPUT_TIME_REFLECT_ATR")
    private int preInputTimeReflectAtr;

    @Column(name = "PRE_BREAK_TIME_REFLECT_ATR")
    private int preBreakTimeReflectAtr;

    @Column(name = "POST_WORK_TIME_REFLECT_ATR")
    private int postWorkTimeReflectAtr;

    @Column(name = "POST_BP_TIME_REFLECT_ATR")
    private int postBpTimeReflectAtr;

    @Column(name = "POST_ANYV_TIME_REFLECT_ATR")
    private int postAnyvTimeReflectAtr;

    @Column(name = "POST_DVGC_REFLECT_ATR")
    private int postDvgcReflectAtr;

    @Column(name = "POST_BREAK_TIME_REFLECT_ATR")
    private int postBreakTimeReflectAtr;

    @OneToMany(mappedBy = "appOvertimeSetting", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<KrqstAppOvertimeFrame> overtimeFrames = new ArrayList<>();

    @Override
    protected Object getKey() {
        return this.companyId;
    }

    public OvertimeAppSet toDomain() {
        return new OvertimeAppSet(
                this.companyId,
                OvertimeLeaveAppCommonSet.create(
                        this.preExcessAtr,
                        this.extraTimeExcessAtr,
                        this.extraTimeDisplayAtr,
                        this.atdExcessAtr,
                        this.instructExcessAtr,
                        this.dvgcExcessAtr,
                        this.atdExcessOverrideAtr),
                KrqstAppOvertimeFrame.toDomains(this.overtimeFrames),
                ApplicationDetailSetting.create(this.instructRequiredAtr, this.preRequiredAtr, this.timeInputUseAtr, this.timeCalUseAtr, this.workTimeIniAtr, this.endWorkTimeIniAtr)
        );
    }

}

