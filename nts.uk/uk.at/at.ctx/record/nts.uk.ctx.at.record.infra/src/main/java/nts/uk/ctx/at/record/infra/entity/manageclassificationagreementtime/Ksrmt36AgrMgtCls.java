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

    /** The exclus ver. */
    @Column(name = "EXCLUS_VER")
    private int exclusVer;

    @Column(name = "CONTRACT_CD")
    public String contractCD;

    @Column(name = "BASIC_M_AL_TIME")
    public double basicMAllTime;

    @Column(name = "BASIC_M_ER_TIME")
    public double basicMArlTime;

    @Column(name = "BASIC_M_LIMIT_TIME")
    public double basicMLimitTime;

    @Column(name = "SP_M_AL_TIME")
    public double spMAlTime;

    @Column(name = "SP_M_ER_TIME")
    public double spMErTime;

    @Column(name = "SP_M_LIMIT_TIME")
    public double spMLimitTime;

    @Column(name = "BASIC_Y_AL_TIME")
    public double basisYAlTime;

    @Column(name = "BASIC_Y_ER_TIME")
    public double basisYErTime;

    @Column(name = "BASIC_Y_LIMIT_TIME")
    public double basisYLimitTime;

    @Column(name = "SP_Y_AL_TIME")
    public double spYAlTime;

    @Column(name = "SP_Y_ER_TIME")
    public double spYErlTime;

    @Column(name = "SP_Y_LIMIT_TIME")
    public double spYLimitTime;

    @Column(name = "MULTI_M_AVG_AL_TIME")
    public double multiMAvgAlTime;

    @Column(name = "MULTI_M_AVG_ER_TIME")
    public double multiMAvgErTime;

    @Column(name = "UPPER_LIMIT_CNT")
    public double upperLimitCnt;

    @Override
    protected Object getKey() {
        return null;
    }
}
