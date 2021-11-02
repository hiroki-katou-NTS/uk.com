package nts.uk.cnv.core.infra.entity.conversiontable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.cnv.core.dom.conversiontable.ConversionRecord;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_RECORD")
public class ScvmtConversionRecord {

	@EmbeddedId
	public ScvmtConversionRecordPk pk;

	@Column(name = "SOURCE_ID")
	public String sourceId;

	@Column(name = "EXPLANATION")
	private String explanation;

	@Column(name = "WHERE_CONDITION")
	private String whereCondition;

	@Column(name = "REMOVE_DUPLICATE")
	private boolean removeDuplicate;

	public ConversionRecord toDomain() {
		return new ConversionRecord(
				this.pk.getCategoryName(),
				this.pk.getTargetTableName(),
				this.pk.getRecordNo(),
				this.sourceId,
				this.explanation,
				this.whereCondition,
				this.removeDuplicate);
	}
}
