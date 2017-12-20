/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.app.systemresource.dto;

import lombok.Data;

@Data
public class SystemResourceDto {
	
	/** The resource id. */
	private String resourceId;
	
	/** The resource content. */
	private String resourceContent;
	
	/**
	 * Instantiates a new system resource dto.
	 *
	 * @param id the id
	 * @param content the content
	 */
	public SystemResourceDto(String id, String content){
		this.resourceId = id;
		this.resourceContent = content;
	}
	
	/**
	 * Instantiates a new system resource dto.
	 */
	public SystemResourceDto(){
		super();
	}
}
