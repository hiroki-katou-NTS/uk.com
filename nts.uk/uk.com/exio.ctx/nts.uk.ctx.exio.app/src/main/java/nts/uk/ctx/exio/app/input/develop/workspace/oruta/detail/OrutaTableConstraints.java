package nts.uk.ctx.exio.app.input.develop.workspace.oruta.detail;

import java.util.List;

import lombok.Value;

@Value
public class OrutaTableConstraints {

	OrutaPrimaryKey primaryKey;

	List<OrutaIndex> indexes;
	
	List<OrutaIndex> uniqueConstraints;
}
