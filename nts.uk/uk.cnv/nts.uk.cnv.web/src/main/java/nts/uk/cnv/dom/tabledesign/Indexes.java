package nts.uk.cnv.dom.tabledesign;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Indexes {
	private String name;
	private List<String> colmns;
}
