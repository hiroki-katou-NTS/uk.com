package nts.uk.cnv.infra.entity.pattern;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversionsql.JoinAtr;
import nts.uk.cnv.dom.conversionsql.TableName;
import nts.uk.cnv.dom.pattern.ParentJoinPattern;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionTablePk;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_TYPE_PARENT")
public class ScvmtConversionTypeParent extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtConversionTablePk pk;

	@Column(name = "SOURCE_NO")
	private int sourceNo;

	@Column(name = "PARENT_TABLE_NAME")
	private String parentName;

	@Column(name = "PARENT_COLUMN_NAME")
	private String parentColumnName;

	@Column(name = "JOIN_PARENT_COLUMNS")
	private String joinParentColumns;

	@Column(name = "JOIN_SOURCE_COLUMNS")
	private String joinSourceColumns;

	@Override
	protected Object getKey() {
		return pk;
	}

	public ParentJoinPattern toDomain(ConversionInfo info, Join sourceJoin) {
		//TODO:これじゃあだめ
		return new ParentJoinPattern(
				info,
				sourceJoin,
				new Join(
					new TableName(info.getTargetDatabaseName(), info.getTargetSchema(), parentName, "parent_" + parentColumnName),
					JoinAtr.OuterJoin,
					new ArrayList<>()),
				parentColumnName
			);
	}

}
