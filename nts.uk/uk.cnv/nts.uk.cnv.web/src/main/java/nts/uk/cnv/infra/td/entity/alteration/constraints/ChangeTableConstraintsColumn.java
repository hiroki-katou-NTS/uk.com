package nts.uk.cnv.infra.td.entity.alteration.constraints;

import static java.util.stream.Collectors.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.arc.layer.infra.data.query.QueryProxy;

@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ChangeTableConstraintsColumn extends JpaEntity implements Serializable {

	@EmbeddedId
	public ChangeTableConstraintsColumnPk pk;
	
	@Column(name = "COLUMN_ORDER")
	public int columnOrder;
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static <E extends ChangeTableConstraintsColumn> List<E> toEntities(
			ChangeTableConstraintsPk parentPk,
			List<String> sortedColumnIds,
			BiFunction<ChangeTableConstraintsColumnPk, Integer, E> constructor) {
		

		List<E> entities = new ArrayList<>();
		
		for (int i = 0; i < sortedColumnIds.size(); i++) {
			
			String columnId = sortedColumnIds.get(i);
			int columnOrder = i + 1;
			
			val column = constructor.apply(
					ChangeTableConstraintsColumnPk.create(parentPk, columnId),
					columnOrder);
			
			entities.add(column);
		}
		
		return entities;
	}
	
	public static <E extends ChangeTableConstraintsColumn> List<String> getSortedColumnIds(
			QueryProxy queryProxy,
			Class<E> entityClass,
			ChangeTableConstraintsPk pk) {
		
		String entityName = entityClass.getSimpleName();
		String sql = "SELECT col"
				+ " FROM " + entityName + " col"
				+ " WHERE col.pk.alterationId = :alterationId"
				+ "   AND col.pk.seqNo = :seqNo"
				+ "   AND col.pk.suffix = :suffix";
		
		return queryProxy.query(sql, entityClass)
				.setParameter("alterationId", pk.alterationId)
				.setParameter("seqNo", pk.seqNo)
				.setParameter("suffix", pk.suffix)
				.getList().stream()
				.sorted(Comparator.comparing(e -> e.columnOrder))
				.map(e -> e.pk.columnId)
				.collect(toList());
	}
}
