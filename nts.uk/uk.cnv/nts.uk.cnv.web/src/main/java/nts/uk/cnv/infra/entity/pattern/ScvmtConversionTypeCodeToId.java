package nts.uk.cnv.infra.entity.pattern;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversiontable.pattern.CodeToIdPattern;
import nts.uk.cnv.dom.conversiontable.pattern.ConversionPattern;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionTable;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionTablePk;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_TYPE_CODE_TO_ID")
public class ScvmtConversionTypeCodeToId extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtConversionTablePk pk;

	@Column(name = "SOURCE_COLUMN_NAME")
	private String sourceColumnName;

	@Column(name = "CODE_TO_ID_TYPE")
	private String codeToIdType;

	@Column(name = "CCD_COLUMN_NAME")
	private String companyCodeColumnName;

	@OneToOne(optional=true) @PrimaryKeyJoinColumns({
        @PrimaryKeyJoinColumn(name="CATEGORY_NAME", referencedColumnName="CATEGORY_NAME"),
        @PrimaryKeyJoinColumn(name="TARGET_TBL_NAME", referencedColumnName="TARGET_TBL_NAME"),
        @PrimaryKeyJoinColumn(name="RECORD_NO", referencedColumnName="RECORD_NO"),
        @PrimaryKeyJoinColumn(name="TARGET_COLUMN_NAME", referencedColumnName="TARGET_COLUMN_NAME")
    })
	private ScvmtConversionTable conversionTable;

	@Override
	protected Object getKey() {
		return pk;
	}

	public CodeToIdPattern toDomain(ConversionInfo info, Join sourcejoin) {
		return new CodeToIdPattern(
				info,
				sourcejoin,
				this.sourceColumnName,
				this.codeToIdType,
				this.companyCodeColumnName);
	}

	public static ScvmtConversionTypeCodeToId toEntity(ScvmtConversionTablePk pk, ConversionPattern conversionPattern) {
		if (!(conversionPattern instanceof CodeToIdPattern)) {
			return null;
		}

		CodeToIdPattern domain = (CodeToIdPattern) conversionPattern;

		return new ScvmtConversionTypeCodeToId(
				pk,
				domain.getSourceColumnName(),
				domain.getCodeToIdType().name(),
				(domain.getSourceCcdColumnName().isPresent()) ? domain.getSourceCcdColumnName().get() : null,
				null);
	}

}
