package nts.uk.cnv.core.infra.entity.conversiontable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.core.dom.conversiontable.ConversionSource;

/**
 * コンバート元テーブル定義
 * @author ai_muto
 *
 */
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_SOURCES")
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

	@Column(name = "WHERE_CONDITION")
	private String whereCondition;

	@Column(name = "MEMO")
	private String memo;

	@Column(name = "DATE_COLUMN_NAME")
	private String dateColumnName;

	@Column(name = "STR_DATE_COLUMN_NAME")
	private String startColumnName;

	@Column(name = "END_DATE_COLUMN_NAME")
	private String endColumnName;

	@Column(name = "DATE_TYPE")
	private String dateType;

	@Override
	protected Object getKey() {
		return sourceId;
	}

	public ConversionSource toDomain(List<String> columns) {
		return
			new ConversionSource(
				this.getSourceId(),
				this.categoryName,
				this.sourceTableName,
				this.whereCondition,
				this.memo,
				wrapOptional(this.dateColumnName),
				wrapOptional(this.startColumnName),
				wrapOptional(this.endColumnName),
				wrapOptional(this.dateType),
				columns
			);
	}

	private Optional<String> wrapOptional(String value){
		return (value == null || value.isEmpty()) ? Optional.empty() : Optional.of(value);
	}

}
