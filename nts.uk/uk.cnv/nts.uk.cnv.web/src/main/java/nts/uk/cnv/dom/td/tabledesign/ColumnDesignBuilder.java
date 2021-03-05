package nts.uk.cnv.dom.td.tabledesign;

import nts.uk.cnv.dom.td.tabledefinetype.DataType;

public class ColumnDesignBuilder {
	private int id;
	private String name;
	private String jpName;
	private DataType type;
	private int maxLength;
	private int scale;

	private boolean nullable;

	private boolean primaryKey;
	private int primaryKeySeq;
	private boolean uniqueKey;
	private int uniqueKeySeq;

	private String defaultValue;
	private String comment;

	private String check;

	public ColumnDesignBuilder(ColumnDesign base) {
		this.id = base.getId();
		this.name = base.getName();
		this.jpName = base.getJpName();
		this.type = base.getType();
		this.maxLength = base.getMaxLength();
		this.scale = base.getScale();
		this.nullable = base.isNullable();
		this.primaryKey = base.isPrimaryKey();
		this.primaryKeySeq = base.getPrimaryKeySeq();
		this.uniqueKey = base.isUniqueKey();
		this.uniqueKeySeq = base.getUniqueKeySeq();
		this.defaultValue = base.getDefaultValue();
		this.comment = base.getComment();
		this.check = base.getCheck();
	}

	public ColumnDesign build() {
		return new ColumnDesign(
				this.id,
				this.name,
				this.jpName,
				this.type,
				this.maxLength,
				this.scale,
				this.nullable,
				this.primaryKey,
				this.primaryKeySeq,
				this.uniqueKey,
				this.uniqueKeySeq,
				this.defaultValue,
				this.comment,
				this.check);
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

	public ColumnDesignBuilder pk(boolean isPrimaryKey, int seq) {
		this.primaryKey = isPrimaryKey;
		this.primaryKeySeq = seq;
		return this;
	}

	public ColumnDesignBuilder uk(boolean isUniqueKey, int seq) {
		this.uniqueKey = isUniqueKey;
		this.uniqueKeySeq = seq;
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
