package nts.uk.ctx.at.shared.app.find.calculation.holiday;
/**
 * @author phongtq
 * 休暇加算時間設定
 */
import java.math.BigDecimal;

import lombok.Data;
@Data
public class HolidayAddtionDto {
	/** 会社ID */
	private String companyId;

	/** 1日 */
	private BigDecimal oneDay;

	/** 午前 */
	private BigDecimal morning;

	/** 午後 */
	private BigDecimal afternoon;

	/** 年休 */
	private int annualHoliday;

	/** 特別休暇 */
	private int specialHoliday;

	/** 積立年休 */
	private int yearlyReserved;

	/** 通常勤務の加算設定*/
	private RegularWorkDto regularWork;

	/**フレックス勤務の加算設定*/
	private FlexWorkDto flexWork;

	/**変形労働勤務の加算設定*/
	private WorkDepLaborDto irregularWork;
	
	/*時給者の加算設定*/
	private HourlyPaymentAdditionSetDto hourlyPaymentAdditionSet;
	
	/*就業時間の加算設定管理*/
	private int addSetManageWorkHour;
	
	/*B2_28*/
	/*加算方法*/
	private int addingMethod1;
	
	/*B2_28*/
	/*勤務区分*/
	private int workClass1;
	
	/*B2_33*/
	/*加算方法*/
	private int addingMethod2;
	
	/*B2_33*/
	/*勤務区分*/
	private int workClass2;

	// 参照先設定
	private int refAtrCom;

	// 個人別設定参照先
	private Integer refAtrEmp;
	
}
