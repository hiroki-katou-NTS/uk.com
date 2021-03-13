package nts.uk.cnv.dom.td.alteration.content;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.schema.prospect.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.tabledesign.Indexes;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;

@EqualsAndHashCode(callSuper= false)
public class ChangeUK extends AlterationContent {
	private final String name;
	private final List<String> columnNames;
	private final boolean clustred;

	public ChangeUK(String name, List<String> columnNames, boolean clustred) {
		super(AlterationType.UNIQUE_KEY_CHANGE);
		this.name = name;
		this.columnNames = columnNames;
		this.clustred = clustred;
	}

	public static List<AlterationContent> create(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		Indexes uk = altered.get().getIndexes().stream()
			.filter(idx -> idx.isUK()).findFirst().get();
		return Arrays.asList(new ChangeUK(uk.getName(), uk.getColumns(), uk.isClustered()));
	}

	public static boolean applicable(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		if(!base.isPresent() || !altered.isPresent()) {
			return false;
		}

		List<Indexes> baseUKeys = base.get().getIndexes().stream()
				.filter(idx -> idx.isUK())
				.collect(Collectors.toList());
		List<Indexes> alteredUKeys = altered.get().getIndexes().stream()
				.filter(idx -> idx.isUK())
				.collect(Collectors.toList());

		if(baseUKeys.size() != alteredUKeys.size()) {
			return true;
		}

		for(int i=0; i<alteredUKeys.size(); i++) {
			Indexes baseUk = baseUKeys.get(i);
			Indexes alterdUk = alteredUKeys.get(i);
			if(!baseUk.equals(alterdUk)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public TableProspectBuilder apply(String alterationId, TableProspectBuilder builder) {
		return builder.uk(alterationId, this.name, this.columnNames, this.clustred);
	}
}
