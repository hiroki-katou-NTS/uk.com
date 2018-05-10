package nts.uk.ctx.sys.assist.dom.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
* データ保存の対象社員
*/
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TargetEmployees
{

	/**
    * データ保存処理ID
    */
    private String storeProcessingId;
    
    /**
    * 社員ID
    */
    private String Sid;
    
    /**
    * ビジネスネーム
    */
    private BusinessName businessname;
    
    /**
     * 社員コード
     */
    private EmployeeCode scd;
    
}
