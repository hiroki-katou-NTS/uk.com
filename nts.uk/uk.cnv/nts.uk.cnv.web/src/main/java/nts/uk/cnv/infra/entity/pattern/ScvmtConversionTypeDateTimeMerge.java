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
import nts.uk.cnv.dom.pattern.DateTimeMergePattern;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionTablePk;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_TYPE_DATETIME_MERGE")
public class ScvmtConversionTypeDateTimeMerge extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtConversionTablePk pk;

	@Column(name = "SOURCE_NO")
	private int SourceNo;

	@Column(name = "YYYYMMDD")
	private String yyyymmdd;

	@Column(name = "YYYY")
	private String yyyy;

	@Column(name = "MM")
	private String mm;

	@Column(name = "YYYYMM")
	private String yyyymm;

	@Column(name = "MMDD")
	private String mmdd;

	@Column(name = "DD")
	private String dd;

	@Column(name = "HH")
	private String hh;

	@Column(name = "MI")
	private String mi;

	@Column(name = "HHMI")
	private String hhmi;

	@Column(name = "SS")
	private String ss;

	@Override
	protected Object getKey() {
		return pk;
	}

	public DateTimeMergePattern toDomain(ConversionInfo info, Join sourcejoin) {
		return DateTimeMergePattern.builder()
			.info(info)
			.sourceJoin(sourcejoin)
			.yyyymmdd(this.yyyymmdd)
			.yyyy(this.yyyy)
			.mm(this.mm)
			.yyyymm(this.yyyymm)
			.mmdd(this.mmdd)
			.dd(this.dd)
			.hh(this.hh)
			.mi(this.mi)
			.hhmi(this.hhmi)
			.ss(this.ss)
			.build();

	}

}
