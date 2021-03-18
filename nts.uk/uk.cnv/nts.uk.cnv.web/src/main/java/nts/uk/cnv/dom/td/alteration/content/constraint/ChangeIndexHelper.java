package nts.uk.cnv.dom.td.alteration.content.constraint;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import lombok.val;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableConstraints;

public class ChangeIndexHelper {

	public static <T, C extends AlterationContent> List<AlterationContent> create(
			Optional<? extends TableDesign> base,
			Optional<TableDesign> altered,
			Function<TableConstraints, List<T>> getConstraints,
			Function<T, C> createChange) {

		val bases = getConstraints.apply(base.get().getConstraints());
		val altereds = getConstraints.apply(altered.get().getConstraints());

		List<AlterationContent> result = new ArrayList<>();
		for(int i=0; i<altereds.size(); i++) {
			val baseIdx = bases.get(i);
			val alterdIdx = altereds.get(i);
			if(!baseIdx.equals(alterdIdx)) {
				result.add(createChange.apply(alterdIdx));
			}
		}

		return result;
	}

	public static <T> boolean applicable(
			Optional<? extends TableDesign> base,
			Optional<TableDesign> altered,
			Function<TableConstraints, List<T>> getConstraints) {

		if(!base.isPresent() || !altered.isPresent()) {
			return false;
		}

		val bases = getConstraints.apply(base.get().getConstraints());
		val altereds = getConstraints.apply(altered.get().getConstraints());

		if(bases.size() != altereds.size()) {
			return true;
		}

		for(int i=0; i<altereds.size(); i++) {
			T baseIdx = bases.get(i);
			T alterdIdx = altereds.get(i);
			if(!baseIdx.equals(alterdIdx)) {
				return true;
			}
		}

		return false;
	}

}
