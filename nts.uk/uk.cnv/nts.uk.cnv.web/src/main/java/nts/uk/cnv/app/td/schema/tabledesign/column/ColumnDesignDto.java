package nts.uk.cnv.app.td.schema.tabledesign.column;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;

@Data
@NoArgsConstructor
public class ColumnDesignDto {

	String id;
	String name;
	String jpName;
	DefineColumnTypeDto type;
	String comment;
	int dispOrder;
	
	public ColumnDesignDto(ColumnDesign d) {
		this.id = d.getId();
		this.name = d.getName();
		this.jpName = d.getJpName();
		this.type = new DefineColumnTypeDto(d.getType());
		this.comment = d.getComment();
		this.dispOrder = d.getDispOrder();
	}
	
	public ColumnDesign toDomain() {
		return new ColumnDesign(
				id,
				name,
				jpName,
				type.toDomain(),
				comment,
				dispOrder);
	}
}
