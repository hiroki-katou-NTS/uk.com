package nts.uk.ctx.sys.assist.app.command.mastercopy;

import java.util.List;

import lombok.Data;

/**
 * The Class MasterCopyDataCommand.
 */
@Data
public class MasterCopyDataCommand {
	
	/** The company id. */
	private String companyId;
	
	/** The master data list. */
	private List<MasterCopyCategoryDto> masterDataList;
	
	/**
	 * Instantiates a new master copy data command.
	 */
	public MasterCopyDataCommand() {
		super();
	}
}
