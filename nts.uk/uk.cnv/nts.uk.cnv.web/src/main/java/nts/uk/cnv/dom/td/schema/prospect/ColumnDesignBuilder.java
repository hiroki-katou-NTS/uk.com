package nts.uk.cnv.dom.td.schema.prospect;

import nts.uk.cnv.dom.td.schema.tabledesign.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.DefineColumnType;
import nts.uk.cnv.dom.td.tabledefinetype.DataType;

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
		this.type = base.getType();
		this.maxLength = base.getMaxLength();
		this.scale = base.getScale();
		this.nullable = base.isNullable();
		this.defaultValue = base.getDefaultValue();
		this.comment = base.getComment();
		this.check = base.getCheck();
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

	public ColumnDesignBuilder type(DataType type, int maxLength, int scale, boolean nullable) {
		this.type = type;
		this.maxLength = maxLength;
		this.scale = scale;
		this.nullable = nullable;
		return this;
	}

	public ColumnDesignBuilder defaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
		return this;
	}

	public ColumnDesignBuilder comment(String comment) {
		this.comment = comment;
		return this;
	}

	public ColumnDesignBuilder check(String check) {
		this.check = check;
		return this;
	}
}
