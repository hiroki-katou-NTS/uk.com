package nts.uk.cnv.infra.entity.pattern;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversiontable.pattern.ConversionPattern;
import nts.uk.cnv.dom.conversiontable.pattern.DateTimeMergePattern;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionTable;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionTablePk;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_TYPE_DATETIME_MERGE")
public class ScvmtConversionTypeDateTimeMerge extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtConversionTablePk pk;

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

	@Column(name = "MINUTES")
	private String minutes;

	@Column(name = "YYYYMMDDHHMI")
	private String yyyymmddhhmi;

	@Column(name = "YYYYMMDDHHMISS")
	private String yyyymmddhhmiss;

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

	@Builder
	public ScvmtConversionTypeDateTimeMerge(
			ScvmtConversionTablePk pk,
			String yyyymmdd,
			String yyyy,
			String mm,
			String yyyymm,
			String mmdd,
			String dd,
			String hh,
			String mi,
			String hhmi,
			String ss,
			String minutes,
			String yyyymmddhhmi,
			String yyyymmddhhmiss) {
		this.pk = pk;
		this.yyyymmdd = yyyymmdd;
		this.yyyy = yyyy;
		this.mm = mm;
		this.yyyymm = yyyymm;
		this.mmdd = mmdd;
		this.dd = dd;
		this.hh = hh;
		this.mi = mi;
		this.hhmi = hhmi;
		this.ss = ss;
		this.minutes = minutes;
		this.yyyymmddhhmi = yyyymmddhhmi;
		this.yyyymmddhhmiss = yyyymmddhhmiss;
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
			.minutes(minutes)
			.yyyymmddhhmi(yyyymmddhhmi)
			.yyyymmddhhmiss(yyyymmddhhmiss)
			.build();
	}

	public static ScvmtConversionTypeDateTimeMerge toEntity(ScvmtConversionTablePk pk, ConversionPattern conversionPattern) {
		if (!(conversionPattern instanceof DateTimeMergePattern)) {
			return null;
		}

		DateTimeMergePattern domain = (DateTimeMergePattern) conversionPattern;

		return ScvmtConversionTypeDateTimeMerge.builder()
				.pk(pk)
				.yyyymmdd(domain.getYyyymmdd())
				.yyyy(domain.getYyyy())
				.mm(domain.getMm())
				.yyyymm(domain.getYyyymm())
				.mmdd(domain.getMmdd())
				.dd(domain.getDd())
				.hh(domain.getHh())
				.mi(domain.getMi())
				.hhmi(domain.getHhmi())
				.ss(domain.getSs())
				.minutes(domain.getMinutes())
				.yyyymmddhhmi(domain.getYyyymmddhhmi())
				.yyyymmddhhmiss(domain.getYyyymmddhhmiss())
				.build();
	}

}
