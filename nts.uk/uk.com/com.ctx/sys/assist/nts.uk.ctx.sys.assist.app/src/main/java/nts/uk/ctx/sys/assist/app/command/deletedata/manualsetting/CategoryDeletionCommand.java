/**
 * 
 */
package nts.uk.ctx.sys.assist.app.command.deletedata.manualsetting;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * @author nam.lh
 *
 */
@Data
@Getter
@Setter
@NoArgsConstructor
public class CategoryDeletionCommand {
	    /**
	    * カテゴリID
	    */
	    private String categoryId;
	    
	    /**
	    * 自動設定対象期間
	    */
	    private GeneralDate periodDeletion;
	    
	    /**
	     * システム種類
	     */
	    private int systemType;
}
