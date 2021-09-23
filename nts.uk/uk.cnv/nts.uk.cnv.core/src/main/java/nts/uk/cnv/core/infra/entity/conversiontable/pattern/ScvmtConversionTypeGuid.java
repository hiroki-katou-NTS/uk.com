package nts.uk.cnv.core.infra.entity.conversiontable.pattern;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nemunoki.oruta.shr.tabledefinetype.DatabaseSpec;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.core.dom.conversiontable.pattern.ConversionPattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.GuidPattern;
import nts.uk.cnv.core.infra.entity.conversiontable.ScvmtConversionTable;
import nts.uk.cnv.core.infra.entity.conversiontable.ScvmtConversionTablePk;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_TYPE_GUID")
public class ScvmtConversionTypeGuid extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtConversionTablePk pk;

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

	public GuidPattern toDomain(DatabaseSpec spec) {
		return new GuidPattern(spec);
	}

	public static ScvmtConversionTypeGuid toEntity(ScvmtConversionTablePk pk, ConversionPattern conversionPattern) {
		if (!(conversionPattern instanceof GuidPattern)) {
			return null;
		}

		return new ScvmtConversionTypeGuid(pk, null);
	}

}
