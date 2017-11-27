package nts.uk.shr.pereg.app.find;

import lombok.Value;

/**
 * dto contains class specific and queryResult(itemDef and valueDto)
 * @author xuan vinh
 *
 */

@Value
public class PeregResult {
	
	/**
	 * class of DTO
	 */
	private Class<?> dtoClass;
	
	
	/**
	 * PeregDto or List<PeregDto>
	 * fix and optional data
	 */
	private Object dto;
	
}
