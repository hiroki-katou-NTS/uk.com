package nts.uk.shr.pereg.app.find;

import lombok.Value;

/**
 * dto contains class specific and queryResult(itemDef and valueDto)
 * @author xuan vinh
 *
 */

@Value
public class LayoutingResult {
	private Class<?> dtoFinderClass;
	private Object queryResult;
}
