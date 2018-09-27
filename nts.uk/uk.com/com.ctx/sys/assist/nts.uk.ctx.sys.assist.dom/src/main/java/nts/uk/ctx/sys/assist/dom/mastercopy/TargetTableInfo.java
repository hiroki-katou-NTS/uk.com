package nts.uk.ctx.sys.assist.dom.mastercopy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TargetTableInfo {
	
	/** The key. */
	private KeyInformation key;
	
	/** The copy attribute. */
	private CopyAttribute copyAttribute;
	
	/** The table no. */
	private CopyTargetTableNo tableNo;
	
	/** The table name. */
	private CopyTargetTableName tableName;
	
	

}
