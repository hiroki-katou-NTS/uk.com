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
import nts.uk.cnv.dom.td.tabledesign.TableDesign;

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
	PRIMARY_KEY_CHANGE(
			ChangePK::create,
			ChangePK::applicable),
	UNIQUE_KEY_CHANGE(
			ChangeUK::create,
			ChangeUK::applicable),
	INDEX_CHANGE(
			ChangeIndex::create,
			ChangeIndex::applicable),
	TABLE_DROP(
			RemoveTable::create,
			RemoveTable::applicable),
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
//	COLUMN_CONTAINT_CHANGE(
//			ChangeColumnContaint::create,
//			ChangeColumnContaint::applicable),
//	COLUMN_DEFAULT_VALUE_CHANGE(
//			ChangeDefaultValue::create,
//			ChangeDefaultValue::applicable),
	COLUMN_DELETE(
			RemoveColumn::create,
			RemoveColumn::applicable);

	private BiFunction<Optional<TableDesign>, Optional<TableDesign>, List<AlterationContent>> content;
	private BiFunction<Optional<TableDesign>, Optional<TableDesign>, Boolean> applicable;

	private AlterationType(BiFunction<Optional<TableDesign>, Optional<TableDesign>, List<AlterationContent>> content,
			BiFunction<Optional<TableDesign>, Optional<TableDesign>, Boolean> applicable) {
		this.content = content;
		this.applicable = applicable;
	}

	public List<AlterationContent> createContent(Optional<TableDesign> base, Optional<TableDesign> altered) {
		return this.content.apply(base, altered);
	}

	public boolean applicable(Optional<TableDesign> base, Optional<TableDesign> altered) {
		return this.applicable.apply(base, altered);
	}
}
