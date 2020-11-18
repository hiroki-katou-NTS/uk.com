package nts.uk.cnv.dom.conversiontable.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import lombok.Builder;
import lombok.Getter;
import nts.uk.cnv.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversionsql.SelectSentence;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.dom.tabledefinetype.DataType;
import nts.uk.cnv.dom.tabledefinetype.DatabaseSpec;

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
		DatabaseSpec spec = this.info.getDatebaseType().spec();

		conversionSql.getFrom().addJoin(sourceJoin);

		List<String> datetime = new ArrayList<>();
		if(   (this.yyyymmddhhmi != null && !this.yyyymmddhhmi.isEmpty())
			||(this.yyyymmddhhmiss != null && !this.yyyymmddhhmiss.isEmpty())) {

			String nvarchar;
			String ss;
			if(this.yyyymmddhhmiss != null && !this.yyyymmddhhmiss.isEmpty()) {
				nvarchar = spec.cast(this.yyyymmddhhmiss, DataType.NVARCHAR, 20);
				ss = spec.subString(nvarchar, 13, 2);
			}
			else  {
				nvarchar = spec.cast(this.yyyymmddhhmi, DataType.NVARCHAR, 20);
				if(this.ss != null && !this.ss.isEmpty()) {
					ss = spec.cast(this.ss, DataType.NVARCHAR, 20);
				}
				else {
					ss = "'00'";
				}
			}
			datetime.add(spec.left(nvarchar, 4));	// yyyy
			datetime.add("'-'");
			datetime.add(spec.subString(nvarchar, 5, 2));	// mm
			datetime.add("'-'");
			datetime.add(spec.subString(nvarchar, 7, 2));	// dd
			datetime.add("' '");
			datetime.add(spec.subString(nvarchar, 9, 2));	// dd
			datetime.add("':'");
			datetime.add(spec.subString(nvarchar, 11, 2));	// mi
			datetime.add("':'");
			datetime.add(ss);	// ss

		}
		else {
			String ymd = createYmd();
			String time = createTime();
			datetime.add(ymd);
			datetime.add("' '");
			datetime.add(time);
		}

		String expression = spec.cast(spec.join(datetime), DataType.DATETIME, 0);
		conversionSql.getSelect().add(
			new SelectSentence(
				new ColumnExpression(Optional.empty(), expression),
				new TreeMap<>()
			)
		);
		return conversionSql;
	}

	private String createYmd() {
		DatabaseSpec spec = this.info.getDatebaseType().spec();
		List<String> date = new ArrayList<>();
		String yyyy, mm, dd;
		yyyy = mm = dd = "";

		if (this.yyyymmdd != null && !this.yyyymmdd.isEmpty()) {
			String nvarchar = spec.cast(this.yyyymmdd, DataType.NVARCHAR, 20);
			yyyy = spec.left(nvarchar, 4);
			mm = spec.subString(nvarchar, 5, 2);
			dd = spec.subString(nvarchar, 7, 2);
		}
		else if(this.yyyymm != null && !this.yyyymm.isEmpty()) {
			String nvarchar = spec.cast(this.yyyymm, DataType.NVARCHAR, 20);
			yyyy = spec.left(nvarchar, 4);
			mm = spec.subString(nvarchar, 5, 2);

			if (this.dd != null && !this.dd.isEmpty()) {
				dd = spec.cast(this.dd, DataType.NVARCHAR, 20);
			}
			else {
				dd = "'01'";
			}
		}
		else if ( this.yyyy != null && !this.yyyy.isEmpty()
				&& (this.mmdd != null && !this.mmdd.isEmpty())) {
			String nvarchar = spec.cast(this.mmdd, DataType.NVARCHAR, 20);
			yyyy = spec.cast(this.yyyy, DataType.NVARCHAR, 20);
			mm = spec.left(nvarchar, 2);
			dd = spec.right(nvarchar, 2);

		}
		else {
			yyyy = spec.cast(this.yyyy, DataType.NVARCHAR, 20);
			mm = spec.cast(this.mm, DataType.NVARCHAR, 20);

			if (this.dd != null && !this.dd.isEmpty()) {
				dd = spec.cast(this.dd, DataType.NVARCHAR, 20);
			}
			else {
				dd = "'01'";
			}
		}
		date.add(yyyy);	// yyyy
		date.add("'-'");
		date.add(mm);	// mm
		date.add("'-'");
		date.add(dd);	// dd
		return spec.join(date);
	}

	private String createTime() {
		DatabaseSpec spec = this.info.getDatebaseType().spec();
		List<String> date = new ArrayList<>();
		String hh, mi, ss;
		hh = mi = ss = "";

		if(this.ss != null && !this.ss.isEmpty()) {
			ss = spec.cast(this.ss, DataType.NVARCHAR, 20);
		}
		else {
			ss = "'00'";
		}

		if (this.hhmi != null && !this.hhmi.isEmpty()) {
			date.add(this.hhmi);
			date.add("':'");
			date.add(ss);
			return spec.join(date);
		}

		if (this.minutes != null && !this.minutes.isEmpty()) {
			hh = "(" + this.minutes + "/ 60)";
			mi = spec.mod(this.minutes, "60");
		}
		else {
			hh = (this.hh == null || this.hh.isEmpty()) ? "'00'" : spec.cast(this.hh, DataType.NVARCHAR, 20);
			mi = (this.mi == null || this.mi.isEmpty()) ? "'00'" : spec.cast(this.mi, DataType.NVARCHAR, 20);
		}
		date.add(hh);
		date.add("':'");
		date.add(mi);
		date.add("':'");
		date.add(ss);

		return spec.join(date);
	}

}
