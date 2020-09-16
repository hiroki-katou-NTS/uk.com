package nts.uk.cnv.infra.entity.conversiontable;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversionsql.JoinAtr;
import nts.uk.cnv.dom.conversionsql.TableName;
import nts.uk.cnv.dom.conversiontable.ConversionSource;
import nts.uk.cnv.dom.service.ConversionInfo;

/**
 * コンバート元テーブル定義
 * @author ai_muto
 *
 */
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_TABLE")
public class ScvmtConversionSources extends JpaEntity implements Serializable  {
	private static final long serialVersionUID = 1L;

	/* 主キー */
	@Id
	@Column(name = "SOURCE_ID")
	public String sourceId;

	@Column(name = "CATEGORY_NAME")
	public String categoryName;

	@Column(name = "SOURCE_TBL_NAME")
	private String sourceTableName;

	@Column(name = "ALIAS")
	private String alias;

	@Column(name = "WHERE_CONDITION")
	private String whereCondition;

	@Column(name = "MEMO")
	private String memo;

	@Override
	protected Object getKey() {
		return sourceId;
	}

	public ConversionSource toDomain(ConversionInfo info) {
		return
			new ConversionSource(
				this.sourceTableName,
				this.alias,
				this.whereCondition,
				this.memo,
				new Join(
						new TableName(
							info.getSourceDatabaseName(),
							info.getSourceSchema(),
							this.sourceTableName,
							this.alias
						),
					JoinAtr.Main,
					new ArrayList<>()
				)
			);
	}

}
