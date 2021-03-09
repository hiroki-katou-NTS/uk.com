package nts.uk.cnv.dom.td.alteration.content;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.tabledesign.Indexes;
import nts.uk.cnv.dom.td.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledesign.TableDesignBuilder;

@EqualsAndHashCode(callSuper= false)
public class ChangePK extends AlterationContent {
	private final List<String> columnNames;
	private final boolean clustred;

	public ChangePK(List<String> columnNames, boolean clustred) {
		super(AlterationType.PRIMARY_KEY_CHANGE);
		this.columnNames = columnNames;
		this.clustred = clustred;
	}

	public static List<AlterationContent> create(Optional<TableDesign> base, Optional<TableDesign> altered) {
		Indexes pk = altered.get().getIndexes().stream()
			.filter(idx -> idx.isPK()).findFirst().get();
		return Arrays.asList(new ChangePK(pk.getColumns(), pk.isClustered()));
	}

	public static boolean applicable(Optional<TableDesign> base, Optional<TableDesign> altered) {
		if(!base.isPresent() || !altered.isPresent()) {
			return false;
		}

		Optional<Indexes> basePk = base.get().getIndexes().stream()
				.filter(idx -> idx.isPK())
				.findFirst();
		Optional<Indexes> alteredPk = altered.get().getIndexes().stream()
				.filter(idx -> idx.isPK())
				.findFirst();
		if(!basePk.isPresent() || !alteredPk.isPresent()) {
			return false;
		}

		return !basePk.get().equals(alteredPk.get());
	}

	@Override
	public TableDesignBuilder apply(TableDesignBuilder builder) {
		return builder.pk(this.columnNames, this.clustred);
	}
}
