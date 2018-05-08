/**
 * 
 */
package nts.uk.ctx.sys.assist.app.command.manualsetting;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author nam.lh
 *
 */
@Data
@Getter
@Setter
@NoArgsConstructor
public class TargetEmployeesCommand {
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
	    private String businessname;
	    
	    /**
	     * 社員コード
	     */
	    private String scd;
}
