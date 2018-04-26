package nts.uk.ctx.sys.assist.infra.enity.category;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.assist.dom.category.Category;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* カテゴリ
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SSPMT_CATEGORY")
public class SspmtCategory extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public SspmtCategoryPk categoryPk;
    
    /**
    * オフィスヘルパーの使用可否
    */
    @Basic(optional = true)
    @Column(name = "SCHELPER_SYSTEM")
    public int schelperSystem;
    
    /**
    * カテゴリID
    */
    @Basic(optional = true)
    @Column(name = "CATEGORY_ID")
    public String categoryId;
    
    /**
    * カテゴリ名
    */
    @Basic(optional = true)
    @Column(name = "CATEGORY_NAME")
    public String categoryName;
    
    /**
    * システム使用可否
    */
    @Basic(optional = true)
    @Column(name = "POSSIBILITY_SYSTEM")
    public int possibilitySystem;
    
    /**
    * 保存方法指定可能
    */
    @Basic(optional = true)
    @Column(name = "STORED_PROCEDURE_SPECIFIED")
    public int storedProcedureSpecified;
    
    /**
    * 保存期間区分
    */
    @Basic(optional = true)
    @Column(name = "TIME_STORE")
    public int timeStore;
    
    /**
    * 別会社区分
    */
    @Basic(optional = true)
    @Column(name = "RE_FR_COMPANY_OTHER")
    public int reFrCompanyOther;
    
    /**
    * 勤怠システムの使用可否
    */
    @Basic(optional = true)
    @Column(name = "ATTENDANCE_SYSTEM")
    public int attendanceSystem;
    
    /**
    * 復旧保存範囲
    */
    @Basic(optional = true)
    @Column(name = "RECOVERY_STORAGE_RANGE")
    public int recoveryStorageRange;
    
    /**
    * 給与システムの使用可否
    */
    @Basic(optional = true)
    @Column(name = "PAYMENT_AVAILABILITY")
    public int paymentAvailability;
    
    /**
    * 保存時保存範囲
    */
    @Basic(optional = true)
    @Column(name = "STORAGE_RANGE_SAVED")
    public int storageRangeSaved;
    
    @Override
    protected Object getKey()
    {
        return categoryPk;
    }

    public Category toDomain() {
        return new Category(this.schelperSystem, this.categoryId, this.categoryName, this.possibilitySystem, this.storedProcedureSpecified, this.timeStore, this.reFrCompanyOther, this.attendanceSystem, this.recoveryStorageRange, this.paymentAvailability, this.storageRangeSaved);
    }
    public static SspmtCategory toEntity(Category domain) {
        return new SspmtCategory(new SspmtCategoryPk(), domain.getSchelperSystem(), domain.getCategoryId(), domain.getCategoryName(), domain.getPossibilitySystem(), domain.getStoredProcedureSpecified(), domain.getTimeStore(), domain.getReFrCompanyOther(), domain.getAttendanceSystem(), domain.getRecoveryStorageRange(), domain.getPaymentAvailability(), domain.getStorageRangeSaved());
    }

}
