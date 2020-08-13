package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.RecordDate;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdispset.AppDisplaySetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.AppLimitSetting;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset.KrqstAppMclose;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.KrqstAppType;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOvertimeHolidayWork;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
import org.apache.commons.lang3.BooleanUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請設定
 */

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KRQST_APPLICATION")
@Getter
public class KrqstApplicationSet extends UkJpaEntity {

    @Id
    @Column(name = "CID")
    private String companyId;

    // 承認ルートの基準日
    @Column(name = "BASE_DATE_SET")
    private int baseDateSet;

    // 申請制限設定.月別実績が確認済なら申請できない
    @Column(name = "MON_ATD_CONFIRM_ATR")
    private int monAtdConfirmAtr;

    // 申請制限設定.実績修正がロック状態なら申請できない
    @Column(name = "ATD_LOCK_ATR")
    private int atdLockAtr;

    // 申請制限設定.就業確定済の場合申請できない
    @Column(name = "ATD_CONFIRM_ATR")
    private int atdConfirmAtr;

    // 申請制限設定.日別実績が確認済なら申請できない
    @Column(name = "DAY_ATD_CONFIRM_ATR")
    private int dayAtdConfirmAtr;

    // 申請制限設定.申請理由が必須
    @Column(name = "REASON_REQUIRE_ATR")
    private int reasonRequireAtr;

    // 申請制限設定.定型理由が必須
    @Column(name = "FIXED_REASON_REQUIRE_ATR")
    private int fixedReasonRequireAtr;

    // 申請表示設定.事前事後区分表示
    @Column(name = "PRE_POST_DISPLAY_ATR")
    private int prePostDisplayAtr;

    // 申請表示設定.登録時の手動メール送信の初期値
    @Column(name = "SEND_MAIL_INI_ATR")
    private int sendEmailIniAtr;

    // 残業休日出勤申請の反映.時間外深夜時間を反映する
    @Column(name = "TIME_NIGHT_REFLECT_ATR")
    private int timeNightReflectAtr;

    @OneToMany(mappedBy = "applicationSetting", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<KrqstAppMclose> appDeadlineSetings = new ArrayList<>();

    @OneToMany(mappedBy = "applicationSetting", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<KrqstRepresentApp> appProxySettings = new ArrayList<>();

    @OneToMany(mappedBy = "applicationSetting", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<KrqstAppType> appTypeSettings = new ArrayList<>();

    @Override
    protected Object getKey() {
        return this.companyId;
    }

    public ApplicationSetting toDomainApplicationSetting() {
        return new ApplicationSetting(
                this.companyId,
                new AppLimitSetting(
                        BooleanUtils.toBoolean(monAtdConfirmAtr),
                        BooleanUtils.toBoolean(atdLockAtr),
                        BooleanUtils.toBoolean(atdConfirmAtr),
                        BooleanUtils.toBoolean(reasonRequireAtr),
                        BooleanUtils.toBoolean(fixedReasonRequireAtr),
                        BooleanUtils.toBoolean(dayAtdConfirmAtr)),
                this.appTypeSettings.stream().map(KrqstAppType::toApTypeSetting).collect(Collectors.toList()),
                this.appProxySettings.stream().map(KrqstRepresentApp::toDomain).collect(Collectors.toList()),
                this.appDeadlineSetings.stream().map(KrqstAppMclose::toDomain).collect(Collectors.toList()),
                AppDisplaySetting.createNew(this.prePostDisplayAtr, this.sendEmailIniAtr),
                this.appTypeSettings.stream().map(KrqstAppType::toReceptionRestrictSetting).collect(Collectors.toList()),
                EnumAdaptor.valueOf(this.baseDateSet, RecordDate.class)
        );
    }

    public AppReflectOvertimeHolidayWork toDomainAppReflectOvertimeHolidayWork() {
        return new AppReflectOvertimeHolidayWork(companyId, null, null, NotUseAtr.valueOf(timeNightReflectAtr));
    }

    public KrqstApplicationSet(ApplicationSetting domain, int nightOvertimeReflectAtr) {
        this.companyId = domain.getCompanyID();
        this.baseDateSet = domain.getRecordDate().value;
        this.monAtdConfirmAtr = domain.getAppLimitSetting().isCanAppAchievementMonthConfirm() ? 1 : 0;
        this.atdLockAtr = domain.getAppLimitSetting().isCanAppAchievementLock() ? 1 : 0;
        this.atdConfirmAtr = BooleanUtils.toInteger(domain.getAppLimitSetting().isCanAppFinishWork());
        this.dayAtdConfirmAtr = BooleanUtils.toInteger(domain.getAppLimitSetting().isCanAppAchievementConfirm());
        this.reasonRequireAtr = BooleanUtils.toInteger(domain.getAppLimitSetting().isRequiredAppReason());
        this.fixedReasonRequireAtr = BooleanUtils.toInteger(domain.getAppLimitSetting().isStandardReasonRequired());
        this.prePostDisplayAtr = domain.getAppDisplaySetting().getPrePostDisplayAtr().value;
        this.sendEmailIniAtr = domain.getAppDisplaySetting().getManualSendMailAtr().value;
        this.timeNightReflectAtr = nightOvertimeReflectAtr;
    }
}
