package nts.uk.ctx.exio.app.input.develop.workspace.oruta.detail;

import java.util.List;

import lombok.Value;

@Value
public class OrutaIndex {

	String suffix;
	List<String> columnIds;
	boolean clustered;
}
