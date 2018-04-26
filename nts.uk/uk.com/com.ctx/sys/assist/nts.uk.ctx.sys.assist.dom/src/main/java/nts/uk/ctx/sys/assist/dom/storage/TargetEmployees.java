package nts.uk.ctx.sys.assist.dom.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
* データ保存の対象社員
*/
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
    private String employeeId;
    
    /**
    * ビジネスネーム
    */
    private BusinessName businessname;
    
    
}
