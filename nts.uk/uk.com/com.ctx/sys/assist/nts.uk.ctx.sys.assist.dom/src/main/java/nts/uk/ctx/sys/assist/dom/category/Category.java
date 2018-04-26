package nts.uk.ctx.sys.assist.dom.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

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
    private int schelperSystem;
    
    /**
    * カテゴリID
    */
    private String categoryId;
    
    /**
    * カテゴリ名
    */
    private String categoryName;
    
    /**
    * システム使用可否
    */
    private int possibilitySystem;
    
    /**
    * 保存方法指定可能
    */
    private int storedProcedureSpecified;
    
    /**
    * 保存期間区分
    */
    private int timeStore;
    
    /**
    * 別会社区分
    */
    private int reFrCompanyOther;
    
    /**
    * 勤怠システムの使用可否
    */
    private int attendanceSystem;
    
    /**
    * 復旧保存範囲
    */
    private int recoveryStorageRange;
    
    /**
    * 給与システムの使用可否
    */
    private int paymentAvailability;
    
    /**
    * 保存時保存範囲
    */
    private int storageRangeSaved;
    
    
}
