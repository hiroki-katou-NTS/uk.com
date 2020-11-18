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
import nts.uk.cnv.dom.conversiontable.pattern.ConversionPattern;
import nts.uk.cnv.dom.conversiontable.pattern.TimeWithDayAttrPattern;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionTable;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionTablePk;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_TYPE_TIME_WITH_DAY_ATTR")
public class ScvmtConversionTypeTimeWithDayAttr extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtConversionTablePk pk;

	@Column(name = "TIME_COLUMN_NAME")
	private String timeColumnName;

	@Column(name = "DAY_ATTR_COLUMN_NAME")
	private String dayAttrColumnName;

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

	public TimeWithDayAttrPattern toDomain(ConversionInfo info, Join sourceJoin) {
		return new TimeWithDayAttrPattern(
				info,
				sourceJoin,
				this.timeColumnName,
				this.dayAttrColumnName
			);
	}

	public static ScvmtConversionTypeTimeWithDayAttr toEntity(ScvmtConversionTablePk pk, ConversionPattern conversionPattern) {
		if (!(conversionPattern instanceof TimeWithDayAttrPattern)) {
			return null;
		}

		TimeWithDayAttrPattern domain = (TimeWithDayAttrPattern) conversionPattern;

		return new ScvmtConversionTypeTimeWithDayAttr(pk, domain.getTimeColumn(), domain.getDayAttrColumn(), null);
	}

}
