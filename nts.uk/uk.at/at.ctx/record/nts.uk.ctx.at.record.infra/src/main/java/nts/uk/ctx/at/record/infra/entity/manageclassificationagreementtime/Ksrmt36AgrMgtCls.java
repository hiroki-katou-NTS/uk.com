package nts.uk.ctx.at.record.infra.entity.manageclassificationagreementtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.infra.entity.manageemploymenthours.Ksrmt36AgrMgtEmpPk;
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
@Table(name = "KSRMT_36AGR_MGT_CLS")
@AllArgsConstructor
@NoArgsConstructor
public class Ksrmt36AgrMgtCls extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    public Ksrmt36AgrMgtClsPk ksrmt36AgrMgtClsPk;

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
     * 基本設定の1ヶ月アラーム時間
     * 分類３６協定時間.３６協定基本設定.1ヶ月.基本設定.エラーアラーム時間
     */
    @Column(name = "BASIC_M_AL_TIME")
    public double basicMAllTime;
    /**
     * 基本設定の1ヶ月エラー時間
     * 分類３６協定時間.３６協定基本設定.1ヶ月.基本設定.エラーアラーム時間
     */
    @Column(name = "BASIC_M_ER_TIME")
    public double basicMArlTime;
    /**
     * 基本設定の1ヶ月上限時間
     * 分類３６協定時間.３６協定基本設定.1ヶ月.基本設定
     */
    @Column(name = "BASIC_M_LIMIT_TIME")
    public double basicMLimitTime;
    /**
     * 特例設定の1ヶ月アラーム時間
     * 分類３６協定時間.３６協定基本設定.1ヶ月.特例条項による上限.エラーアラーム時間
     */
    @Column(name = "SP_M_AL_TIME")
    public double spMAlTime;
    /**
     * 特例設定の1ヶ月エラー時間
     * 分類３６協定時間.３６協定基本設定.1ヶ月.特例条項による上限.エラーアラーム時間
     */
    @Column(name = "SP_M_ER_TIME")
    public double spMErTime;
    /**
     * 特例設定の1ヶ月上限時間
     * 分類３６協定時間.３６協定基本設定.1ヶ月.特例条項による上限
     */
    @Column(name = "SP_M_LIMIT_TIME")
    public double spMLimitTime;
    /**
     * 基本設定の1年間アラーム時間
     * 分類３６協定時間.３６協定基本設定.1年間.基本設定.エラーアラーム時間
     */
    @Column(name = "BASIC_Y_AL_TIME")
    public double basisYAlTime;
    /**
     * 基本設定の1年間エラー時間
     * 分類３６協定時間.３６協定基本設定.1年間.基本設定.エラーアラーム時間
     */
    @Column(name = "BASIC_Y_ER_TIME")
    public double basisYErTime;
    /**
     * 基本設定の1年間上限時間
     * 分類３６協定時間.３６協定基本設定.1年間.基本設定
     */
    @Column(name = "BASIC_Y_LIMIT_TIME")
    public double basisYLimitTime;
    /**
     * 特例設定の1年間アラーム時間
     * 分類３６協定時間.３６協定基本設定.1年間.特例条項による上限.エラーアラーム時間
     */
    @Column(name = "SP_Y_AL_TIME")
    public double spYAlTime;
    /**
     * 特例設定の1年間エラー時間
     * 分類３６協定時間.３６協定基本設定.1年間.特例条項による上限.エラーアラーム時間
     */
    @Column(name = "SP_Y_ER_TIME")
    public double spYErlTime;
    /**
     * 特例設定の1年間上限時間
     * 分類３６協定時間.３６協定基本設定.1年間.特例条項による上限
     */
    @Column(name = "SP_Y_LIMIT_TIME")
    public double spYLimitTime;
    /**
     * 複数月平均のアラーム時間
     * 分類３６協定時間.３６協定基本設定.複数月平均.複数月平均
     */
    @Column(name = "MULTI_M_AVG_AL_TIME")
    public double multiMAvgAlTime;
    /**
     * 複数月平均のエラー時間
     * 分類３６協定時間.３６協定基本設定.複数月平均.複数月平均
     */
    @Column(name = "MULTI_M_AVG_ER_TIME")
    public double multiMAvgErTime;
    /**
     * 超過上限回数
     * 分類３６協定時間.３６協定基本設定
     */
    @Column(name = "UPPER_LIMIT_CNT")
    public double upperLimitCnt;

    @Override
    protected Object getKey() {
        return this.ksrmt36AgrMgtClsPk;
    }
}
