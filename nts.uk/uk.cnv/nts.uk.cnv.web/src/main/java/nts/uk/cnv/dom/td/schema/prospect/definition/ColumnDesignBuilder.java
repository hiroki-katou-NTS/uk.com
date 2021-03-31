package nts.uk.cnv.dom.td.schema.prospect.definition;

import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DataType;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DefineColumnType;

public class ColumnDesignBuilder {
	private String id;
	private String name;
	private String jpName;
	private DataType type;
	private int maxLength;
	private int scale;

	private boolean nullable;

	private String defaultValue;
	private String comment;

	private String check;

	private int dispOrder;

	public ColumnDesignBuilder(ColumnDesign base) {
		this.id = base.getId();
		this.name = base.getName();
		this.jpName = base.getJpName();
		this.type = base.getType().getDataType();
		this.maxLength = base.getType().getLength();
		this.scale = base.getType().getScale();
		this.nullable = base.getType().isNullable();
		this.defaultValue = base.getType().getDefaultValue();
		this.comment = base.getComment();
		this.check = base.getType().getCheckConstraint();
		this.dispOrder = base.getDispOrder();
	}

	public ColumnDesign build() {
		return new ColumnDesign(
				this.id,
				this.name,
				this.jpName,
				new DefineColumnType(
						this.type,
						this.maxLength,
						this.scale,
						this.nullable,
						this.defaultValue,
						this.check),
				this.comment,
				this.dispOrder);
	}

	public ColumnDesignBuilder name(String name) {
		this.name = name;
		return this;
	}

	public ColumnDesignBuilder jpName(String jpName) {
		this.jpName = jpName;
		return this;
	}

	public ColumnDesignBuilder type(DataType type, int maxLength, int scale, boolean nullable, String defaultValue, String check) {
		this.type = type;
		this.maxLength = maxLength;
		this.scale = scale;
		this.nullable = nullable;
		this.defaultValue = defaultValue;
		this.check = check;
		return this;
	}

	public ColumnDesignBuilder comment(String comment) {
		this.comment = comment;
		return this;
	}

}
