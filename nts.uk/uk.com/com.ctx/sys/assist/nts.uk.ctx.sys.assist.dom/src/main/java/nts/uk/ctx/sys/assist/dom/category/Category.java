package nts.uk.ctx.sys.assist.dom.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* カテゴリ
*/
@AllArgsConstructor
@Getter
public class Category extends AggregateRoot
{
    
    /**
    * オフィスヘルパーの使用可否
    */
    private SystemUsability schelperSystem;
    
    /**
    * カテゴリID
    */
    private CategoryId categoryId;
    
    /**
    * カテゴリ名
    */
    private CategoryName categoryName;
    
    /**
    * システム使用可否
    */
    private SystemUsability possibilitySystem;
    
    /**
    * 保存方法指定可能
    */
    private StoredProcedureSpecified storedProcedureSpecified;
    
    /**
    * 保存期間区分
    */
    private TimeStore timeStore;
    
    /**
    * 別会社区分
    */
    private RecoverFormCompanyOther otherCompanyCls;
    
    /**
    * 勤怠システムの使用可否
    */
    private SystemUsability attendanceSystem;
    
    /**
    * 復旧保存範囲
    */
    private RecoveryStorageRange recoveryStorageRange;
    
    /**
    * 給与システムの使用可否
    */
    private SystemUsability paymentAvailability;
    
    /**
    * 保存時保存範囲
    */
    private StorageRangeSaved storageRangeSaved;


    
    
}
