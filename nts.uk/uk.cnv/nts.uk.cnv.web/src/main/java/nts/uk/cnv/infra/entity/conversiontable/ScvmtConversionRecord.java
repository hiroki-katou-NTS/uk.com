package nts.uk.cnv.infra.entity.conversiontable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.cnv.dom.conversiontable.ConversionRecord;

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

//	@OrderBy(value = "pk.targetColumnName asc")
//	@OneToMany(targetEntity = ScvmtConversionTable.class, mappedBy = "record", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(name = "SCVMT_CONVERSION_TABLE")
//	public List<ScvmtConversionTable> conversionTables;

	public ConversionRecord toDomain() {
		return new ConversionRecord(this.pk.getRecordNo(), this.sourceId);
	}
}
