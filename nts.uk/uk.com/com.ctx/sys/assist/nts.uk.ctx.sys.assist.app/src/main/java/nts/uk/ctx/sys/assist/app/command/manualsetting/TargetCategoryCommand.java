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
public class TargetCategoryCommand {
	 /**
	    * データ保存処理ID
	    */
	    private String storeProcessingId;
	    
	    /**
	    * カテゴリID
	    */
	    private String categoryId;
}
