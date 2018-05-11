/**
 * 
 */
package nts.uk.ctx.sys.assist.app.command.deletedata.manualsetting;

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
public class CategoryDeletionCommand {
	    /**
	    * カテゴリID
	    */
	    private String categoryId;
}
