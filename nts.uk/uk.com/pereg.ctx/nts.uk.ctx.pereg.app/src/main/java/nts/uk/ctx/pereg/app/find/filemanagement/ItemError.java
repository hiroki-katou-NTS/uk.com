package nts.uk.ctx.pereg.app.find.filemanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemError {
	private String sid;
	private String recordId;
	private int index;
	private String columnKey;
	private String message;

}
