package nts.uk.ctx.at.record.infra.entity.manageworkplaceagreedhours;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.infra.entity.manageemploymenthours.Ksrmt36AgrMgtEmpPk;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.limitrule.AgreementMultiMonthAvg;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOneMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOneYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOverMaxTimes;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * entity: 雇用３６協定時間
 */
@Entity
@Table(name = "KSRMT_36AGR_MGT_WKP")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Ksrmt36AgrMgtWkp extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    public Ksrmt36AgrMgtWkpPk ksrmt36AgrMgtWkpPk;

    /**
     * 排他バージョン
     */
    @Column(name = "EXCLUS_VER")
    private int exclusVer;
    /**
     * 契約コード
     */
    @Column(name = "CONTRACT_CD")
    public String contractCD;
    /**
     * 会社ID
     */
    @Column(name = "CID")
    public String companyID;
    /**
     * 基本設定の1ヶ月アラーム時間
     * 職場３６協定時間.３６協定基本設定.1ヶ月.基本設定.エラーアラーム時間
     */
    @Column(name = "BASIC_M_AL_TIME")
    public double basicMAllTime;
    /**
     * 基本設定の1ヶ月エラー時間
     * 職場３６協定時間.３６協定基本設定.1ヶ月.基本設定.エラーアラーム時間
     */
    @Column(name = "BASIC_M_ER_TIME")
    public double basicMArlTime;
    /**
     * 基本設定の1ヶ月上限時間
     * 職場３６協定時間.３６協定基本設定.1ヶ月.基本設定
     */
    @Column(name = "BASIC_M_LIMIT_TIME")
    public double basicMLimitTime;
    /**
     * 特例設定の1ヶ月アラーム時間
     * 職場３６協定時間.３６協定基本設定.1ヶ月.特例条項による上限.エラーアラーム時間
     */
    @Column(name = "SP_M_AL_TIME")
    public double spMAlTime;
    /**
     * 特例設定の1ヶ月エラー時間
     * 職場３６協定時間.３６協定基本設定.1ヶ月.特例条項による上限.エラーアラーム時間
     */
    @Column(name = "SP_M_ER_TIME")
    public double spMErTime;
    /**
     * 特例設定の1ヶ月上限時間
     * 職場３６協定時間.３６協定基本設定.1ヶ月.特例条項による上限
     */
    @Column(name = "SP_M_LIMIT_TIME")
    public double spMLimitTime;
    /**
     * 基本設定の1年間アラーム時間
     * 職場３６協定時間.３６協定基本設定.1年間.基本設定.エラーアラーム時間
     */
    @Column(name = "BASIC_Y_AL_TIME")
    public double basisYAlTime;
    /**
     * 基本設定の1年間エラー時間
     * 職場３６協定時間.３６協定基本設定.1年間.基本設定.エラーアラーム時間
     */
    @Column(name = "BASIC_Y_ER_TIME")
    public double basisYErTime;
    /**
     * 基本設定の1年間上限時間
     * 職場３６協定時間.３６協定基本設定.1年間.基本設定
     */
    @Column(name = "BASIC_Y_LIMIT_TIME")
    public double basisYLimitTime;
    /**
     * 特例設定の1年間アラーム時間
     * 職場３６協定時間.３６協定基本設定.1年間.特例条項による上限.エラーアラーム時間
     */
    @Column(name = "SP_Y_AL_TIME")
    public double spYAlTime;
    /**
     * 特例設定の1年間エラー時間
     * 職場３６協定時間.３６協定基本設定.1年間.特例条項による上限.エラーアラーム時間
     */
    @Column(name = "SP_Y_ER_TIME")
    public double spYErlTime;
    /**
     * 特例設定の1年間上限時間
     * 職場３６協定時間.３６協定基本設定.1年間.特例条項による上限
     */
    @Column(name = "SP_Y_LIMIT_TIME")
    public double spYLimitTime;
    /**
     * 複数月平均のアラーム時間
     * 職場３６協定時間.３６協定基本設定.複数月平均.複数月平均
     */
    @Column(name = "MULTI_M_AVG_AL_TIME")
    public double multiMAvgAlTime;
    /**
     * 複数月平均のエラー時間
     * 職場３６協定時間.３６協定基本設定.複数月平均.複数月平均
     */
    @Column(name = "MULTI_M_AVG_ER_TIME")
    public double multiMAvgErTime;
    /**
     * 超過上限回数
     * 職場３６協定時間.３６協定基本設定
     */
    @Column(name = "UPPER_LIMIT_CNT")
    public int upperLimitCnt;

    @Override
    protected Object getKey() {
        return this.ksrmt36AgrMgtWkpPk;
    }

    public static Ksrmt36AgrMgtWkp toEntity(AgreementTimeOfWorkPlace domain) {
        val contractCD = AppContexts.user().contractCode();
        val cid = AppContexts.user().companyId();
        return new Ksrmt36AgrMgtWkp(
                new Ksrmt36AgrMgtWkpPk(domain.getWorkplaceId(), domain.getLaborSystemAtr().value),
                1,
                contractCD,
                cid,
                // basicMAllTime ->分類３６協定時間.３６協定基本設定.1ヶ月.基本設定.エラーアラーム時間
                domain.getSetting().getOneMonth().getBasic().getErAlTime().getError().v(),

                // basicMArlTime->分類３６協定時間.３６協定基本設定.1ヶ月.基本設定.エラーアラーム時間
                domain.getSetting().getOneMonth().getBasic().getErAlTime().getAlarm().v(),
                // BASIC_M_LIMIT_TIME->分類３６協定時間.３６協定基本設定.1ヶ月.基本設定
                domain.getSetting().getOneMonth().getBasic().getUpperLimit().v(),

                // SP_M_AL_TIME-> 分類３６協定時間.３６協定基本設定.1ヶ月.特例条項による上限.エラーアラーム時間
                domain.getSetting().getOneMonth().getSpecConditionLimit().getErAlTime().getError().v(),

                // SP_M_ER_TIME->分類３６協定時間.３６協定基本設定.1ヶ月.特例条項による上限.エラーアラーム時間
                domain.getSetting().getOneMonth().getSpecConditionLimit().getErAlTime().getAlarm().v(),

                // SP_M_LIMIT_TIME->分類３６協定時間.３６協定基本設定.1ヶ月.特例条項による上限
                domain.getSetting().getOneMonth().getSpecConditionLimit().getUpperLimit().v(),

                // BASIC_Y_AL_TIME->分類３６協定時間.３６協定基本設定.1年間.基本設定.エラーアラーム時間
                domain.getSetting().getOneYear().getBasic().getError().v(),

                // BASIC_Y_ER_TIME->分類３６協定時間.３６協定基本設定.1年間.基本設定.エラーアラーム時間
                domain.getSetting().getOneYear().getBasic().getAlarm().v(),

                // BASIC_Y_LIMIT_TIME->分類３６協定時間.３６協定基本設定.1年間.基本設定
                domain.getSetting().getOneYear().getSpecConditionLimit().getUpperLimit().v(),

                // SP_Y_AL_TIME->分類３６協定時間.３６協定基本設定.1年間.特例条項による上限.エラーアラーム時間
                domain.getSetting().getOneYear().getSpecConditionLimit().getErAlTime().getError().v(),

                // SP_Y_ER_TIME->分類３６協定時間.３６協定基本設定.1年間.特例条項による上限.エラーアラーム時間
                domain.getSetting().getOneYear().getSpecConditionLimit().getErAlTime().getAlarm().v(),

                // SP_Y_LIMIT_TIME->分類３６協定時間.３６協定基本設定.1年間.特例条項による上限
                domain.getSetting().getOneYear().getSpecConditionLimit().getUpperLimit().v(),

                // MULTI_M_AVG_AL_TIME ->分類３６協定時間.３６協定基本設定.複数月平均.複数月平均
                domain.getSetting().getMultiMonth().getMultiMonthAvg().getError().v(),

                // MULTI_M_AVG_ER_TIME->分類３６協定時間.３６協定基本設定.複数月平均.複数月平均
                domain.getSetting().getMultiMonth().getMultiMonthAvg().getAlarm().v(),

                // UPPER_LIMIT_CNT->分類３６協定時間.３６協定基本設定
                domain.getSetting().getOverMaxTimes().value);

    }

    public static AgreementTimeOfWorkPlace toDomain(Ksrmt36AgrMgtWkp entity) {
        val companyId = entity.getCompanyID();
        val laborSystemAtr = EnumAdaptor.valueOf(entity.getKsrmt36AgrMgtWkpPk().getLaborSystemAtr(), LaborSystemtAtr.class);

        val erAlTime = new OneMonthErrorAlarmTime(new AgreementOneMonthTime((int) entity.getBasicMAllTime()), new AgreementOneMonthTime((int) entity.getBasicMArlTime()));
        val upperLimit = new AgreementOneMonthTime((int) entity.getBasicMLimitTime());

        val basic = new OneMonthTime(erAlTime, upperLimit);

        val erAlTimeSp = new OneMonthErrorAlarmTime(new AgreementOneMonthTime((int) entity.getSpMAlTime()), new AgreementOneMonthTime((int) entity.getSpMErTime()));
        val upperLimitSp = new AgreementOneMonthTime((int) entity.getSpMLimitTime());
        val specConditionLimit = new OneMonthTime(erAlTimeSp, upperLimitSp);

        val oneMonth = new AgreementOneMonth(basic, specConditionLimit);


        val basicY = new OneYearErrorAlarmTime(new AgreementOneYearTime((int) entity.getBasisYAlTime()), new AgreementOneYearTime((int) entity.getBasisYErTime()));
        val erAlTimeSpY = new OneYearErrorAlarmTime(new AgreementOneYearTime((int) entity.getSpYAlTime()), new AgreementOneYearTime((int) entity.getSpYErlTime()));

        val upperLimitSpY = new AgreementOneYearTime((int) entity.getBasisYLimitTime());

        val specConditionLimitY = new OneYearTime(erAlTimeSpY, upperLimitSpY);
        val oneYear = new AgreementOneYear(basicY, specConditionLimitY);


        val multiMonthAvg = new OneMonthErrorAlarmTime(new AgreementOneMonthTime((int) entity.getMultiMAvgAlTime())
                , new AgreementOneMonthTime((int) entity.getMultiMAvgErTime()));

        val multiMonth = new AgreementMultiMonthAvg(multiMonthAvg);
        val overMaxTimes = EnumAdaptor.valueOf(entity.getUpperLimitCnt(), AgreementOverMaxTimes.class);

        val basicAgreementSetting = new BasicAgreementSetting(oneMonth, oneYear, multiMonth, overMaxTimes);
        return new AgreementTimeOfWorkPlace(companyId, laborSystemAtr, basicAgreementSetting);


    }
}
