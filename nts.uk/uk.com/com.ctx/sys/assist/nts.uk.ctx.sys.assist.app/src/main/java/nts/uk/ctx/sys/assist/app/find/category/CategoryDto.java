package nts.uk.ctx.sys.assist.app.find.category;

import lombok.Data;
import nts.uk.ctx.sys.assist.dom.category.Category;
/**
* カテゴリ
*/

@Data
public class CategoryDto
{
	
    
    public CategoryDto() {
		super();
	}

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
    private int otherCompanyCls;
    
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
    
    
    public static CategoryDto fromDomain(Category domain)
    {
        return new CategoryDto(domain.getSchelperSystem().value, domain.getCategoryId().toString(), domain.getCategoryName().toString(), domain.getPossibilitySystem().value, domain.getStoredProcedureSpecified().value, domain.getTimeStore().value, domain.getOtherCompanyCls().value, domain.getAttendanceSystem().value, domain.getRecoveryStorageRange().value, domain.getPaymentAvailability().value, domain.getStorageRangeSaved().value);
    }

    public CategoryDto(int schelperSystem, String categoryId, String categoryName, int possibilitySystem,
			int storedProcedureSpecified, int timeStore, int otherCompanyCls, int attendanceSystem,
			int recoveryStorageRange, int paymentAvailability, int storageRangeSaved) {
		super();
		this.schelperSystem = schelperSystem;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.possibilitySystem = possibilitySystem;
		this.storedProcedureSpecified = storedProcedureSpecified;
		this.timeStore = timeStore;
		this.otherCompanyCls = otherCompanyCls;
		this.attendanceSystem = attendanceSystem;
		this.recoveryStorageRange = recoveryStorageRange;
		this.paymentAvailability = paymentAvailability;
		this.storageRangeSaved = storageRangeSaved;
	}
	
    
}
