package nts.uk.shr.pereg.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemLog {
	private String itemId;
	private String itemCode;
	private String itemName;
	private Integer type;	
	private String valueBefore;
	private String valueAfter;
}
