package nts.uk.cnv.dom.td.alteration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class Feature {
	String name;

	public String v() {
		return name;
	}
}
