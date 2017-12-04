package nts.uk.ctx.pereg.dom.person.info.category;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class ReferenceStateData {
	private String constraint;
	public static Map<Integer, Map<Integer, String>> selectionLst =  new HashMap<Integer, Map<Integer, String>>();;

}
