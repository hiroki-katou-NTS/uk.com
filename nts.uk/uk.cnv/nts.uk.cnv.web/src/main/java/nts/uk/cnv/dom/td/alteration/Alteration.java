package nts.uk.cnv.dom.td.alteration;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.gul.text.IdentifierUtil;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspectBuilder;

/**
 * 変更
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Alteration implements Comparable<Alteration>{
	String alterId;
	String tableId;
	AlterationMetaData metaData;
	List<AlterationContent> contents;

	/** 変更内容が空のおるたを作る **/
	public static Alteration createEmpty(String tableId, AlterationMetaData metaData) {
		return new Alteration(
				IdentifierUtil.randomUniqueId(),
				tableId,
				metaData,
				new ArrayList<>());
	}

	public void apply(TableProspectBuilder builder) {
		contents.stream().forEach(c -> {
			c.apply(this.alterId, builder);
		});
	}

	@Override
	public int compareTo(Alteration other) {
		return this.metaData.time.compareTo(other.metaData.time);
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
