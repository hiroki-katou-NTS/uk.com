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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContent;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KscdtScheExeContent.
 */
//Entity: スケジュール作成内容

@Getter
@Setter
@Entity
@Table(name = "KSCDT_BATCH_CONTENT")
@AllArgsConstructor
public class KscdtScheExeContent extends UkJpaEntity implements Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    private static final int exclusVerConst =0;
    /**
     * The exe id.
     */
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXE_ID")
    private String exeId;
    /**
     * 契約コード
     */
    @NotNull
    @Column(name = "CONTRACT_CD")
    private String contractCd;

    /**
     * 会社ID
     */
    @NotNull
    @Column(name = "CID")
    private String companyId;

    /**
     * 確定済みにする
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "BE_CONFIRMED")
    private Boolean beConfirmed;

    /**
     * 作成種類
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_TYPE")
    private Integer creationType;

    /**
     * 作成方法
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_METHOD")
    private Integer creationMethod;

    /**
     * The copy start ymd.
     */
    @Column(name = "COPY_START_YMD")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate copyStartYmd;

    /**
     * 作成方法参照先
     */
    @Column(name = "REFERENCE_MASTER")
    private Integer referenceMaster;

    /**
     * 月間パターンコード
     */
    @Column(name = "MONTHLY_PATTERN_CD")
    private String monthlyPatternId;

    /**
     * 再作成者を限定するか
     */
    @Column(name = "RE_TARGET_ATR")
    private Boolean reTargetAtr;

    /**
     * 異動者のみ再作成するか
     */
    @Column(name = "RE_TARGET_TRANSFER")
    private Boolean reTargetTransfer;

    /**
     * 休職休業者のみ再作成するか
     */
    @Column(name = "RE_TARGET_LEAVE")
    private Boolean reTargetLeave;

    /**
     * 短時間勤務者のみ再作成するか
     */
    @Column(name = "RE_TARGET_SHORT_WORK")
    private Boolean reTargetShortWork;

    /**
     * 労働条件変更者のみ再作成するか
     */
    @Column(name = "RE_TARGET_LABOR_CHANGE")
    private Boolean reTargetLaborChange;

    /**
     * 確定済みも再作成するか
     */
    @Column(name = "RE_OVERWRITE_CONFIRMED")
    private Boolean reOverwriteConfirmed;

    /**
     * 手修正・申請反映した日も再作成するか
     */
    @Column(name = "RE_OVERWRITE_REVISED")
    private Boolean reOverwriteRevised;

    /**
     * Instantiates a new kscmt sch create content.
     */
    public KscdtScheExeContent() {
    }

    public  KscdtScheExeContent toEntityNew(ScheduleCreateContent domain, String companyId, String contractCode) {
        Boolean isTransfer = null;
        Boolean isLeaveOfAbsence = null;
        Boolean isShortWorkingHours = null;
        Boolean isChangedWorkingConditions = null;
        val getRecreateConditionOpt =  domain.getRecreateCondition();
        if(getRecreateConditionOpt.isPresent()){
            val getRecreateCondition = getRecreateConditionOpt.get();
            val getNarrowingEmployeesOpt = getRecreateCondition.getNarrowingEmployees();
            if(getNarrowingEmployeesOpt.isPresent()){
                isTransfer = getNarrowingEmployeesOpt.get().isTransfer();
                isLeaveOfAbsence = getNarrowingEmployeesOpt.get().isLeaveOfAbsence();
                isShortWorkingHours = getNarrowingEmployeesOpt.get().isShortWorkingHours();
                isChangedWorkingConditions = getNarrowingEmployeesOpt.get().isChangedWorkingConditions();
            }
        }
        return new KscdtScheExeContent(
                domain.getExecutionId(),
                contractCode,
                companyId,
                domain.getConfirm(),
                domain.getImplementAtr().value,
                domain.getSpecifyCreation().getCreationMethod().value,
                domain.getSpecifyCreation().getCopyStartDate().isPresent()? domain.getSpecifyCreation().getCopyStartDate().get():null,
                domain.getSpecifyCreation().getReferenceMaster().isPresent()?domain.getSpecifyCreation().getReferenceMaster().get().value:null,
                domain.getSpecifyCreation().getMonthlyPatternCode().isPresent()? domain.getSpecifyCreation().getMonthlyPatternCode().get().toString():null,
                domain.getRecreateCondition().isPresent()?domain.getRecreateCondition().get().getReTargetAtr():null,
                isTransfer,
                isLeaveOfAbsence,
                isShortWorkingHours,
                isChangedWorkingConditions,
                domain.getRecreateCondition().isPresent()?domain.getRecreateCondition().get().getReOverwriteConfirmed():null,
                domain.getRecreateCondition().isPresent()?domain.getRecreateCondition().get().getReOverwriteRevised():null);
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

