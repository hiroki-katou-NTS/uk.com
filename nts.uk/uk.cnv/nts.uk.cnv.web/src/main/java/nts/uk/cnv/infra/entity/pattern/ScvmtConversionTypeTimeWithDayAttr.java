package nts.uk.cnv.infra.entity.pattern;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.pattern.TimeWithDayAttrPattern;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionTablePk;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_TYPE_TIME_WITH_DAY_ATTR")
public class ScvmtConversionTypeTimeWithDayAttr extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtConversionTablePk pk;

	@Column(name = "SOURCE_NO")
	private int sourceNo;

	@Column(name = "TIME_COLUMN_NAME")
	private String timeColumnName;

	@Column(name = "DAY_ATTR_COLUMN_NAME")
	private String dayAttrColumnName;

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

}
