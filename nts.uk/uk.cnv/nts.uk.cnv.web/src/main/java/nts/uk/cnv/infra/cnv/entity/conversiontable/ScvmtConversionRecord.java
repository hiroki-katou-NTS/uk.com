package nts.uk.cnv.infra.cnv.entity.conversiontable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.cnv.dom.cnv.conversiontable.ConversionRecord;

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

	public ConversionRecord toDomain() {
		return new ConversionRecord(
				this.pk.getCategoryName(),
				this.pk.getTargetTableName(),
				this.pk.getRecordNo(),
				this.sourceId,
				this.explanation);
	}
}
