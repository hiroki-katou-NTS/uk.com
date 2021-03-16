package nts.uk.cnv.dom.td.alteration;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;

/**
 * oruta
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Alteration implements Comparable<Alteration> {
	
	/** oruta ID */
	String alterId;
	
	/** Feature ID */
	String featureId;
	
	/** 作成日時 */
	GeneralDateTime createdAt;
	
	/** テーブルID */
	String tableId;
	
	/** メタ情報 */
	AlterationMetaData metaData;
	
	/** 内容 */
	List<AlterationContent> contents;

	public static Optional<Alteration> create(
			String featureId,
			String tableId,
			AlterationMetaData meta,
			Optional<? extends TableDesign> base,
			Optional<TableDesign> altered) {

		if (base.equals(altered)) {
			return Optional.empty();
		}

		val contents = Arrays.stream(AlterationType.values())
			.filter(type -> type.applicable(base, altered))
			.flatMap(type -> type.createContent(base, altered).stream())
			.collect(toList());

		return Optional.of(new Alteration(
				IdentifierUtil.randomUniqueId(),
				featureId,
				GeneralDateTime.now(),
				tableId,
				meta,
				contents));
	}

	public void apply(TableProspectBuilder builder) {
		contents.stream().forEach(c -> {
			c.apply(this.alterId, builder);
		});
	}

	@Override
	public int compareTo(Alteration other) {
		return this.createdAt.compareTo(other.createdAt);
	}

	/**
	 * alterId以外が一致しているか（UnitTest用）
	 * @param obj
	 * @return
	 */
	public boolean equalsExcludingId(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alteration other = (Alteration) obj;
		if (contents == null) {
			if (other.contents != null)
				return false;
		} else if (!contents.equals(other.contents))
			return false;
		if (metaData == null) {
			if (other.metaData != null)
				return false;
		} else if (!metaData.equals(other.metaData))
			return false;
		if (tableId == null) {
			if (other.tableId != null)
				return false;
		} else if (!tableId.equals(other.tableId))
			return false;
		return true;
	}
}
