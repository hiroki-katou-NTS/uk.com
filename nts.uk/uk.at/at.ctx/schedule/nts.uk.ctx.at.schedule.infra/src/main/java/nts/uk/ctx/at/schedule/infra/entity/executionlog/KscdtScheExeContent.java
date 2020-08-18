/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KscdtScheExeContent.
 */
//Entity: スケジュール作成内容

@Getter
@Setter
@Entity
@Table(name = "KSCDT_BATCH_CONTENT")
public class KscdtScheExeContent extends UkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The exe id. */
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXE_ID")
    private String exeId;

    /** 排他バージョン */
    @NotNull
    @Column(name = "EXCLUS_VER")
    private Integer exclusVer;

    /** 契約コード */
    @NotNull
    @Column(name = "CONTRACT_CD")
    private String contractCD;

    /** 会社ID */
    @NotNull
    @Column(name = "CID")
    private String companyId;

    /** 確定済みにする */
    @Basic(optional = false)
    @NotNull
    @Column(name = "BE_CONFIRMED")
    private Boolean beConfirmed;

    /** 作成種類 */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_TYPE")
    private Integer creationType;
    
    /** 作成方法 */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_METHOD")
    private Integer creationMethod;
    
    /** The copy start ymd. */
    @Column(name = "COPY_START_YMD")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate copyStartYmd;
    
    /** 作成方法参照先 */
    @Column(name = "REFERENCE_MASTER")
    private Integer referenceMaster;
    
    /** 月間パターンコード */
    @Column(name = "MONTHLY_PATTERN_CD")
    private String monthlyPatternId;

    /** 再作成者を限定するか */
    @Column(name = "RE_TARGET_ATR")
    private Boolean reTargetAtr;
    
    /** 異動者のみ再作成するか */
    @Column(name = "RE_TARGET_TRANSFER")
    private Boolean reTargetTransfer;
    
    /** 休職休業者のみ再作成するか	 */
    @Column(name = "RE_TARGET_LEAVE")
    private Boolean reTargetLeave;
    
    /** 短時間勤務者のみ再作成するか */
    @Column(name = "RE_TARGET_SHORT_WORK")
    private Boolean reTargetShortWork;
    
    /** 労働条件変更者のみ再作成するか*/
    @Column(name = "RE_TARGET_LABOR_CHANGE")
    private Boolean reTargetLaborChange;
    
    /** 確定済みも再作成するか */
    @Column(name = "RE_OVERWRITE_CONFIRMED")
    private Boolean reOverwriteConfirmed;
    
    /** 手修正・申請反映した日も再作成するか*/
    @Column(name = "RE_OVERWRITE_REVISED")
    private Boolean reOverwriteRevised;

    /**
     * Instantiates a new kscmt sch create content.
     */
    public KscdtScheExeContent() {
    }

    /**
     * Instantiates a new kscmt sch create content.
     *
     * @param exeId the exe id
     */
    public KscdtScheExeContent(String exeId) {
        this.exeId = exeId;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exeId != null ? exeId.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KscdtScheExeContent)) {
            return false;
        }
        KscdtScheExeContent other = (KscdtScheExeContent) object;
        if ((this.exeId == null && other.exeId != null) || (this.exeId != null && !this.exeId.equals(other.exeId))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KscmtSchCreateContent[ exeId=" + exeId + " ]";
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.exeId;
	}
    
    
}

