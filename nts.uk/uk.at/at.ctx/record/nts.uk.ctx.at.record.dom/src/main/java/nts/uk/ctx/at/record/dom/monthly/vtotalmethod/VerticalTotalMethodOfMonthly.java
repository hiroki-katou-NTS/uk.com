package nts.uk.ctx.at.record.dom.monthly.vtotalmethod;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 月別実績の縦計方法
 * @author shuichu_ishida
 */
@Getter
@Setter
@AllArgsConstructor
public class VerticalTotalMethodOfMonthly extends AggregateRoot {

	/** 会社ID */
	private String companyId;
	/** 振出日数 */
	private TADaysCountOfMonthlyAggr transferAttendanceDays;
//	/** 特定日 */
//	private SpecTotalCountMonthly specTotalCountMonthly;
	
	/**
	 * コンストラクタ
	 * @param companyId 会社ID
	 */
	public VerticalTotalMethodOfMonthly(String companyId){
		
		super();
		this.companyId = companyId;
		this.transferAttendanceDays = new TADaysCountOfMonthlyAggr();
	}
	
	/**
	 * ファクトリー
	 * @param companyId 会社ID
	 * @param transferAttendanceDays 振出日数
	 * @return 月別実績の縦計方法
	 */
	public static VerticalTotalMethodOfMonthly of(String companyId,
			TADaysCountOfMonthlyAggr transferAttendanceDays){

		VerticalTotalMethodOfMonthly domain = new VerticalTotalMethodOfMonthly(companyId);
		domain.transferAttendanceDays = transferAttendanceDays;
		return domain;
	}
	
	public static VerticalTotalMethodOfMonthly createFromJavaType(String companyId, int taAttendance) {
//		return new VerticalTotalMethodOfMonthly(companyId, TADaysCountOfMonthlyAggr.of(EnumAdaptor.valueOf(taAttendance, TADaysCountCondOfMonthlyAggr.class)),
//				SpecTotalCountMonthly.createFromJavaType(specDayOfTotalMonCon, specCount));
		return new VerticalTotalMethodOfMonthly(companyId, TADaysCountOfMonthlyAggr.of(EnumAdaptor.valueOf(taAttendance, TADaysCountCondOfMonthlyAggr.class)));
	}
}
