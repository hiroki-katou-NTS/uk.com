package nts.uk.cnv.core.infra.entity.conversiontable.pattern;

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
import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;
import nts.uk.cnv.core.dom.conversiontable.pattern.ConversionPattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.FileIdPattern;
import nts.uk.cnv.core.infra.entity.conversiontable.ScvmtConversionTable;
import nts.uk.cnv.core.infra.entity.conversiontable.ScvmtConversionTablePk;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_TYPE_FILEID")
public class ScvmtConversionTypeFileId extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtConversionTablePk pk;

	@Column(name = "SOURCE_COLUMN_NAME")
	private String sourceColumnName;

	@Column(name = "FILE_TYPE")
	private String fileType;

	@Column(name = "KOJIN_ID_COLUMN_NAME")
	private String kojinIdColumnName;

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

	public FileIdPattern toDomain(ConversionInfo info, Join sourcejoin) {
		return new FileIdPattern(
				info,
				sourcejoin,
				sourceColumnName,
				fileType,
				kojinIdColumnName
			);
	}

	public static ScvmtConversionTypeFileId toEntity(
			ScvmtConversionTablePk pk,
			ConversionPattern conversionPattern) {

		if (!(conversionPattern instanceof FileIdPattern)) {
			return null;
		}

		FileIdPattern domain = (FileIdPattern) conversionPattern;

		return new ScvmtConversionTypeFileId(pk
				, domain.getSourceColumnName()
				, domain.getFileType().getId()
				, domain.getKojinIdColumnName()
				,null);
	}

}