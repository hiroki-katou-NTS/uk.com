package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.DisplayReason;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.*;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting.KrqstApplicationSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
import org.apache.commons.lang3.BooleanUtils;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KRQST_APP_TYPE")
public class KrqstAppType extends UkJpaEntity {
    @EmbeddedId
    private KrqstAppTypePk pk;

    @Column(name = "PRE_POST_INIT_ATR")
    private int prePostInitAtr;

    @Column(name = "PRE_POST_CHANGE_ATR")
    private int prePostChangeAtr;

    @Column(name = "APV_SEND_MAIL_ATR")
    private int apvSendMailAtr;

    @Column(name = "APP_SEND_MAIL_ATR")
    private int appSendMailAtr;

    @Column(name = "POST_FUTURE_ALLOW_ATR")
    private int postFutureAllowAtr;

    @Column(name = "PRE_EARLY_DAYS")
    private int preEarlyDays;

    @Column(name = "USE_ATR")
    private int useAtr;

    @Column(name = "PRE_OT_CHECK_SET")
    private int preOTCheckSet;

    @Column(name = "PRE_OT_BEF_WORK_TIME")
    private Integer preOTBeforeWorkTime;

    @Column(name = "PRE_OT_AFT_WORK_TIME")
    private Integer preOTAfterWorkTime;

    @Column(name = "PRE_OT_BEF_AFT_WORK_TIME")
    private Integer preOTBeforeAfterWorkTime;

    @Column(name = "FIXED_REASON_DISP_ATR")
    private int fixedReasonDisplayAtr;

    @Column(name = "REASON_DISP_ATR")
    private int reasonDisplayAtr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CID", insertable = false, updatable = false)
    private KrqstApplicationSet applicationSetting;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public AppTypeSetting toApTypeSetting() {
        return AppTypeSetting.createNew(
                this.pk.applicationType,
                BooleanUtils.toBoolean(this.appSendMailAtr),
                BooleanUtils.toBoolean(apvSendMailAtr),
                this.prePostInitAtr,
                BooleanUtils.toBoolean(this.prePostChangeAtr)
        );
    }

    public ReceptionRestrictionSetting toReceptionRestrictSetting() {
        return new ReceptionRestrictionSetting(
                OTAppBeforeAccepRestric.createNew(
                        this.preOTCheckSet,
                        this.preEarlyDays,
                        BooleanUtils.toBoolean(this.useAtr),
                        this.preOTBeforeWorkTime,
                        this.preOTAfterWorkTime,
                        this.preOTBeforeAfterWorkTime
                ),
                new AfterhandRestriction(BooleanUtils.toBoolean(postFutureAllowAtr)),
                BeforehandRestriction.createNew(
                        preEarlyDays,
                        BooleanUtils.toBoolean(useAtr)
                ),
                EnumAdaptor.valueOf(this.pk.applicationType, ApplicationType.class)
        );
    }

    public static KrqstAppType fromDomain(String companyId, AppTypeSetting appTypeSetting, ReceptionRestrictionSetting receptionRestrictSetting, DisplayReason displayReason) {
        return KrqstAppType.builder()
                .pk(new KrqstAppTypePk(companyId, appTypeSetting.getAppType().value))
                .appSendMailAtr(BooleanUtils.toInteger(appTypeSetting.isSendMailWhenRegister()))
                .apvSendMailAtr(BooleanUtils.toInteger(appTypeSetting.isSendMailWhenApproval()))
                .prePostInitAtr(appTypeSetting.getDisplayInitialSegment().value)
                .prePostChangeAtr(BooleanUtils.toInteger(appTypeSetting.isCanClassificationChange()))
                .preOTCheckSet(receptionRestrictSetting.getOtAppBeforeAccepRestric().getMethodCheck().value)
                .preEarlyDays(receptionRestrictSetting.getOtAppBeforeAccepRestric().getDateBeforehandRestrictions().value)
                .useAtr(BooleanUtils.toInteger(receptionRestrictSetting.getOtAppBeforeAccepRestric().isToUse()))
                .preOTBeforeWorkTime(receptionRestrictSetting.getOtAppBeforeAccepRestric().getOpEarlyOvertime().map(PrimitiveValueBase::v).orElse(null))
                .preOTAfterWorkTime(receptionRestrictSetting.getOtAppBeforeAccepRestric().getOpNormalOvertime().map(PrimitiveValueBase::v).orElse(null))
                .preOTBeforeAfterWorkTime(receptionRestrictSetting.getOtAppBeforeAccepRestric().getOpEarlyNormalOvertime().map(PrimitiveValueBase::v).orElse(null))
                .postFutureAllowAtr(BooleanUtils.toInteger(receptionRestrictSetting.getAfterhandRestriction().isAllowFutureDay()))
                .reasonDisplayAtr(displayReason.getDisplayAppReason().value)
                .fixedReasonDisplayAtr(displayReason.getDisplayFixedReason().value)
                .build();
    }
}
