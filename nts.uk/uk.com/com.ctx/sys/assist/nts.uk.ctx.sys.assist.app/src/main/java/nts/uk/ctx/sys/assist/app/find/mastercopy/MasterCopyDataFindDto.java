package nts.uk.ctx.sys.assist.app.find.mastercopy;

import lombok.Data;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyTarget;

/**
 * The Class MasterCopyDataFindDto.
 */
@Data
public class MasterCopyDataFindDto {

	/** The master copy id. */
	private String masterCopyId;
	
	/** The master copy target. */
	private MasterCopyTarget masterCopyTarget;
	
	/**
	 * Instantiates a new master copy data find dto.
	 */
	public MasterCopyDataFindDto(){
		super();
	}
	
	/**
	 * Instantiates a new master copy data find dto.
	 *
	 * @param masterCopyId the master copy id
	 * @param masterCopyTarget the master copy target
	 */
	public MasterCopyDataFindDto(String masterCopyId, MasterCopyTarget masterCopyTarget){
		this.masterCopyId = masterCopyId;
		this.masterCopyTarget = masterCopyTarget;
	}
}
