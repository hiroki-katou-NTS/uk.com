package nts.uk.cnv.dom.td.alteration;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.tabledesign.TableDesignBuilder;

/**
 * 変更
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Alteration {
	String tableName;
	AlterationMetaData metaData;
	List<AlterationContent> contents;

	public static Alteration createEmpty(String tableName, AlterationMetaData metaData) {
		return new Alteration(tableName, metaData, new ArrayList<>());
	}

	public void apply(TableDesignBuilder builder) {
		contents.stream().forEach(c -> {
			c.apply(builder);
		});
	}
}
