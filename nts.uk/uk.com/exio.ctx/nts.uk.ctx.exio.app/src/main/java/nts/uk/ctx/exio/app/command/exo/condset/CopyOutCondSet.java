package nts.uk.ctx.exio.app.command.exo.condset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CopyOutCondSet {

	private boolean result;
	private String destinationName;
	private String destinationCode;
	private boolean overWrite;
	private Integer standType;
	private String conditionSetCode;
	private int categoryId;
	private int delimiter;
	private int itemOutputName;
	private int autoExecution;
	private String conditionSetName;
	private int conditionOutputName;
	private int stringFormat;
	
	public CopyOutCondSet(boolean overWrite, String destinationName, String destinationCode, boolean result) {
		this.overWrite = overWrite;
		this.destinationName = destinationName;
		this.destinationCode = destinationCode;
		this.result = result;
	}

}
