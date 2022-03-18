package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.days.YearlyDays;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AttendanceRate;

/**
 * 年休付与テーブル設定
 * 
 * @author TanLV
 *
 */

@Getter
@AllArgsConstructor
public class GrantHdTblSet extends AggregateRoot implements Serializable{
	
	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

	/* 会社ID */
	private String companyId;

	/* コード */
	private YearHolidayCode yearHolidayCode;

	/* 名称 */
	private YearHolidayName yearHolidayName;

	/* 計算方法 */
	private CalculationMethod calculationMethod;

	/* 計算基準 */
	private StandardCalculation standardCalculation;

	/* 一斉付与を利用する */
	private UseSimultaneousGrant useSimultaneousGrant;

	/* 一斉付与日 */
	private Integer simultaneousGrandMonthDays;

	/* 備考 */
	private YearHolidayNote yearHolidayNote;

	private List<GrantCondition> grantConditions;

	@Override
	public void validate() {
		super.validate();

		// 一斉付与を利用する」がTRUEの場合は必ず一斉付与日を登録すること
		if (UseSimultaneousGrant.USE.equals(this.useSimultaneousGrant) && this.simultaneousGrandMonthDays == null) {
			throw new BusinessException("Msg_261");
		}

		for (int i = 0; i < this.grantConditions.size(); i++) {
			GrantCondition currentCondition = this.grantConditions.get(i);
			
			//まず、「付与条件」のvalidate()チェック。OKだったら、複数の「付与条件」の関係性をチェックする処理へ
			currentCondition.validate(this.getCalculationMethod());

			if (i == 0) {
				if(!currentCondition.isUse())
					throw new RuntimeException();
				
				continue;
			}

			// 条件NO：1、条件値 > 条件NO：2、条件値 > 条件NO：3、条件値 > 条件NO：4、条件値 > 条件NO：5、条件値
			if(this.grantConditions.get(i - 1).isUse()) {
				Double firstValue = this.grantConditions.get(i - 1).getConditionValueToDouble();
				
				if(currentCondition.isUse()) {
					if (firstValue <= currentCondition.getConditionValueToDouble()) {
						throw new BusinessException("Msg_264");
					}
				}				
			}
		}
	}

	public GrantHdTblSet(String companyId, YearHolidayCode yearHolidayCode, YearHolidayName yearHolidayName,
			CalculationMethod calculationMethod, StandardCalculation standardCalculation,
			UseSimultaneousGrant useSimultaneousGrant, int simultaneousGrandMonthDays, YearHolidayNote yearHolidayNote,
			List<GrantCondition> grantConditions) {

		this.companyId = companyId;
		this.yearHolidayCode = yearHolidayCode;
		this.yearHolidayName = yearHolidayName;
		this.calculationMethod = calculationMethod;
		this.standardCalculation = standardCalculation;
		this.useSimultaneousGrant = useSimultaneousGrant;
		this.simultaneousGrandMonthDays = simultaneousGrandMonthDays;
		this.yearHolidayNote = yearHolidayNote;
		this.grantConditions = grantConditions;
	}

	public static GrantHdTblSet createFromJavaType(String companyId, String yearHolidayCode, String yearHolidayName,
			int calculationMethod, int standardCalculation, int useSimultaneousGrant, int simultaneousGrandMonthDays,
			String yearHolidayNote, List<GrantCondition> grantConditions) {
		return new GrantHdTblSet(companyId, new YearHolidayCode(yearHolidayCode), new YearHolidayName(yearHolidayName),
				EnumAdaptor.valueOf(calculationMethod, CalculationMethod.class),
				EnumAdaptor.valueOf(standardCalculation, StandardCalculation.class),
				EnumAdaptor.valueOf(useSimultaneousGrant, UseSimultaneousGrant.class), simultaneousGrandMonthDays,
				new YearHolidayNote(yearHolidayNote), grantConditions);
	}
	
	/**
	 * 日数と出勤率から年休付与テーブルを取得する
	 * @param attendanceRate 出勤率
	 * @param prescribedDays 所定日数
	 * @param workingDays 労働日数
	 * @param deductedDays 控除日数
	 * @return 付与条件
	 */
	// 2018.7.27 add shuichi_ishida
	public Optional<GrantCondition> getGrantCondition(
			AttendanceRate attendanceRate,
			YearlyDays prescribedDays,
			YearlyDays workingDays,
			YearlyDays deductedDays,
			Optional<DatePeriod> period){
	
		// 計算方法をチェックする
		double criteria = attendanceRate.v().doubleValue();
		if (this.calculationMethod == CalculationMethod.WORKING_DAY){
			criteria = workingDays.v() + deductedDays.v();
		}
		
		// 付与条件を取得
		GrantCondition target = null;
		this.grantConditions.sort((a, b) -> a.getConditionNo() - b.getConditionNo());
		
		List<GrantCondition> forSerch=this.getConditionsForSerch(period);
		for (val grantCondition : forSerch){		
			// 基準値と付与条件．条件値を比較
			if (grantCondition.isLessThanEqual(criteria)) {
				target = grantCondition;
				break;
			}
		}
		
		// 付与条件を返す
		return Optional.ofNullable(target);
	}
	
	/**
	 * サーチするための条件一覧を作成する。
	 * 労働日数で判断する場合は、按分した条件にする。
	 * @param period
	 * @return
	 */
	private List<GrantCondition> getConditionsForSerch(Optional<DatePeriod> period) {
		return this.grantConditions.stream().filter(c -> c.isUse()).map(c -> {
			if (this.getCalculationMethod().equals(CalculationMethod.ATTENDENCE_RATE))
				return c;
			if (this.getCalculationMethod().equals(CalculationMethod.WORKING_DAY))
				return c.toDivideProportionately(period);
			throw new RuntimeException();
		}).collect(Collectors.toList());
	}
}
