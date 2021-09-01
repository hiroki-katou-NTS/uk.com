package nts.uk.ctx.exio.infra.entity.input.transfer.conversion.pattern;

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
import nts.uk.ctx.exio.infra.entity.input.transfer.conversion.ScvmtConversionTable;
import nts.uk.ctx.exio.infra.entity.input.transfer.conversion.ScvmtConversionTablePk;

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
		// 外部受入の移送処理ではファイルID変換を使う予定がないためファイル種別や個人ID列名は未設定
		return new FileIdPattern(
				info,
				sourcejoin,
				this.sourceColumnName,
				null,
				null
			);
	}

	public static ScvmtConversionTypeFileId toEntity(ScvmtConversionTablePk pk, ConversionPattern conversionPattern) {
		if (!(conversionPattern instanceof FileIdPattern)) {
			return null;
		}

		FileIdPattern domain = (FileIdPattern) conversionPattern;

		return new ScvmtConversionTypeFileId(pk, domain.getSourceColumnName(), null);
	}

}
