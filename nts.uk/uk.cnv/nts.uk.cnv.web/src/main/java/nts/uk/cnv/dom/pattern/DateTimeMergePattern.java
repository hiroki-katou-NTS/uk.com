package nts.uk.cnv.dom.pattern;

import lombok.Builder;
import lombok.Getter;
import nts.uk.cnv.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.service.ConversionInfo;

/**
 * 日時への型変換
 * @author ai_muto
 *
 */
@Getter
public class DateTimeMergePattern extends ConversionPattern {

	/** 変換元 **/
	private Join sourceJoin;

	private final String yyyymmdd;
	private final String yyyy;
	private final String mm;
	private final String yyyymm;
	private final String mmdd;
	private final String dd;
	private final String hh;
	private final String mi;
	private final String hhmi;
	private final String ss;
	private final String minutes;
	private final String yyyymmddhhmi;
	private final String yyyymmddhhmiss;

	@Builder
	public DateTimeMergePattern(
			ConversionInfo info,
			Join sourceJoin,
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
		super(info);
		this.sourceJoin = sourceJoin;
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

	@Override
	public ConversionSQL apply(ConversionSQL conversionSql) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
