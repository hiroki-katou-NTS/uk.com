package nts.uk.ctx.sys.assist.app.command.mastercopy;

import lombok.Data;

/**
 * The Class MasterCopyCategoryDto.
 */
@Data
public class MasterCopyCategoryDto {
	
	/** The master copy id. */
	private Integer masterCopyId;
	
	/** The category name. */
	private String categoryName;
	
	/** The order. */
	private Integer order;
	
	/** The system type. */
	private String systemType;
	
	/** The copy method. */
	private Integer copyMethod;
	
	/**
	 * Instantiates a new master copy category find dto.
	 */
	public MasterCopyCategoryDto(){
		super();
	}
}
