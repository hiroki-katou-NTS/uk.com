package nts.uk.cnv.dom.td.alteration;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import nts.uk.cnv.dom.td.alteration.content.AddColumn;
import nts.uk.cnv.dom.td.alteration.content.AddTable;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.alteration.content.ChangeColumnComment;
import nts.uk.cnv.dom.td.alteration.content.ChangeColumnJpName;
import nts.uk.cnv.dom.td.alteration.content.ChangeColumnName;
import nts.uk.cnv.dom.td.alteration.content.ChangeColumnType;
import nts.uk.cnv.dom.td.alteration.content.ChangeIndex;
import nts.uk.cnv.dom.td.alteration.content.ChangePK;
import nts.uk.cnv.dom.td.alteration.content.ChangeTableJpName;
import nts.uk.cnv.dom.td.alteration.content.ChangeTableName;
import nts.uk.cnv.dom.td.alteration.content.ChangeUK;
import nts.uk.cnv.dom.td.alteration.content.RemoveColumn;
import nts.uk.cnv.dom.td.alteration.content.RemoveTable;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;

public enum AlterationType {
	TABLE_CREATE(
			AddTable::create,
			AddTable::applicable),
	TABLE_NAME_CHANGE(
			ChangeTableName::create,
			ChangeTableName::applicable),
	TABLE_JPNAME_CHANGE(
			ChangeTableJpName::create,
			ChangeTableJpName::applicable),
	COLUMN_ADD(
			AddColumn::create,
			AddColumn::applicable),
	COLUMN_NAME_CHANGE(
			ChangeColumnName::create,
			ChangeColumnName::applicable),
	COLUMN_TYPE_CHANGE(
			ChangeColumnType::create,
			ChangeColumnType::applicable),
	COLUMN_JPNAME_CHANGE(
			ChangeColumnJpName::create,
			ChangeColumnJpName::applicable),
	COLUMN_COMMENT_CHANGE(
			ChangeColumnComment::create,
			ChangeColumnComment::applicable),
	PRIMARY_KEY_CHANGE(
			ChangePK::create,
			ChangePK::applicable),
	UNIQUE_KEY_CHANGE(
			ChangeUK::create,
			ChangeUK::applicable),
	INDEX_CHANGE(
			ChangeIndex::create,
			ChangeIndex::applicable),
	COLUMN_DELETE(
			RemoveColumn::create,
			RemoveColumn::applicable),
	TABLE_DROP(
			RemoveTable::create,
			RemoveTable::applicable);

	private BiFunction<Optional<? extends TableDesign>, Optional<TableDesign>, List<AlterationContent>> content;
	private BiFunction<Optional<? extends TableDesign>, Optional<TableDesign>, Boolean> applicable;

	private AlterationType(
			BiFunction<Optional<? extends TableDesign>, Optional<TableDesign>, List<AlterationContent>> content,
			BiFunction<Optional<? extends TableDesign>, Optional<TableDesign>, Boolean> applicable) {
		this.content = content;
		this.applicable = applicable;
	}

	public List<AlterationContent> createContent(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		return this.content.apply(base, altered);
	}

	public boolean applicable(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		return this.applicable.apply(base, altered);
	}

	public boolean isAffectTableList() {
		return (	this == AlterationType.TABLE_CREATE ||
					this == AlterationType.TABLE_NAME_CHANGE ||
					this == AlterationType.TABLE_JPNAME_CHANGE ||
					this == AlterationType.TABLE_DROP );
	}
}
