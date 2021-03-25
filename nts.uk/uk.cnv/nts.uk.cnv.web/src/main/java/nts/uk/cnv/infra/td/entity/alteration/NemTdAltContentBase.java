package nts.uk.cnv.infra.td.entity.alteration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.val;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.td.alteration.content.AddTable;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.infra.td.entity.alteration.column.NemTdAltAddColumn;
import nts.uk.cnv.infra.td.entity.alteration.column.NemTdAltChangeColumnComment;
import nts.uk.cnv.infra.td.entity.alteration.column.NemTdAltChangeColumnJpName;
import nts.uk.cnv.infra.td.entity.alteration.column.NemTdAltChangeColumnName;
import nts.uk.cnv.infra.td.entity.alteration.column.NemTdAltChangeColumnType;
import nts.uk.cnv.infra.td.entity.alteration.column.NemTdAltDeleteColumn;
import nts.uk.cnv.infra.td.entity.alteration.index.NemTdAltChangeIndex;
import nts.uk.cnv.infra.td.entity.alteration.index.NemTdAltChangePrimaryKey;
import nts.uk.cnv.infra.td.entity.alteration.index.NemTdAltChangeUniqueKey;
import nts.uk.cnv.infra.td.entity.alteration.table.NemTdAltAddTable;
import nts.uk.cnv.infra.td.entity.alteration.table.NemTdAltAddTableColumn;
import nts.uk.cnv.infra.td.entity.alteration.table.NemTdAltAddTableIndex;
import nts.uk.cnv.infra.td.entity.alteration.table.NemTdAltChangeTableJpName;
import nts.uk.cnv.infra.td.entity.alteration.table.NemTdAltChangeTableName;
import nts.uk.cnv.infra.td.entity.alteration.table.NemTdAltDeleteTable;

public abstract class NemTdAltContentBase extends JpaEntity implements Serializable {
	@Override
	protected Object getKey() {
		return null;
	}

	public static List<JpaEntity> toEntity(String alterationId, int seq, AlterationContent ac) {
		List<JpaEntity> result = new ArrayList<>();
		NemTdAltContentPk pk = new NemTdAltContentPk(alterationId, seq);
		switch(ac.getType()) {
		case TABLE_CREATE:
			val domain = (AddTable)ac;
			result.add(NemTdAltAddTable.toEntity(pk, domain));
			result.addAll(NemTdAltAddTableColumn.toEntity(pk, domain));
			result.addAll(NemTdAltAddTableIndex.toEntity(pk, domain));
			break;
		case TABLE_NAME_CHANGE:
			result.add(NemTdAltChangeTableName.toEntity(pk, ac));
			break;
		case TABLE_JPNAME_CHANGE:
			result.add(NemTdAltChangeTableJpName.toEntity(pk, ac));
			break;
		case COLUMN_ADD:
			result.add(NemTdAltAddColumn.toEntity(pk, ac));
		case COLUMN_NAME_CHANGE:
			result.add(NemTdAltChangeColumnName.toEntity(pk, ac));
			break;
		case COLUMN_TYPE_CHANGE:
			result.add(NemTdAltChangeColumnType.toEntity(pk, ac));
			break;
		case COLUMN_JPNAME_CHANGE:
			result.add(NemTdAltChangeColumnJpName.toEntity(pk, ac));
			break;
		case COLUMN_COMMENT_CHANGE:
			result.add(NemTdAltChangeColumnComment.toEntity(pk, ac));
			break;
		case PRIMARY_KEY_CHANGE:
			result.addAll(NemTdAltChangePrimaryKey.toEntity(pk, ac));
			break;
		case UNIQUE_KEY_CHANGE:
			result.addAll(NemTdAltChangeUniqueKey.toEntity(pk, ac));
			break;
		case INDEX_CHANGE:
			result.addAll(NemTdAltChangeIndex.toEntity(pk, ac));
			break;
		case COLUMN_DELETE:
			result.add(NemTdAltDeleteColumn.toEntity(pk, ac));
			break;
		case TABLE_DROP:
			result.add(NemTdAltDeleteTable.toEntity(pk));
			break;
		}

		return result;
	}
}
