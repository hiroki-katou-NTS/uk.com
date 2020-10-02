/**
 * 
 */
package nts.uk.ctx.sys.assist.app.command.manualsetting;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.sys.assist.dom.storage.SystemType;

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
	    
	    /**
	     * システム種類
	     */
	    private SystemType systemType;
}
