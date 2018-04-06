/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.calculation.holiday;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class WorkFlexAdditionSet.
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
// フレックス勤務の加算設定
public class WorkFlexAdditionSet extends AggregateRoot{

	/** 会社ID */
	private String companyId;

	/** 実働のみで計算する */
	private CalcActualOperationAtr calcActualOperation1;

	/** インターバル免除時間を含めて計算する */
	private int exemptTaxTime1;

	/** 育児・介護時間を含めて計算する */
	private int incChildNursingCare1;

	/** フレックスの所定超過時 */
	private PredExcessTimeflexAtr predeterminedOvertime1;

	/** 加算する */
	private int additionTime1;

	/** 遅刻・早退を控除しない */
	private int notDeductLateleave1;

	/** インターバル免除時間を含めて計算する */
	private int exemptTaxTime2;

	/** 欠勤時間をマイナスする */
	private int minusAbsenceTime2;

	/** 実働のみで計算する */
	private CalcActualOperationAtr calcActualOperation2;

	/** 育児・介護時間を含めて計算する */
	private int incChildNursingCare2;

	/** 遅刻・早退を控除しない */
	private int notDeductLateleave2;

	/**フレックスの所定不足時*/
	private PredExcessTimeflexAtr predeterminDeficiency2;	
	
	/** 加算する */
	private int additionTime2;
	
	/*B6_21*/
	/*就業時間帯毎の設定を可能とする*/
	private boolean enableSetPerWorkHour1;
	
	/*B6_22*/
	/*就業時間帯毎の設定を可能とする*/
	private boolean enableSetPerWorkHour2;
	
	/*B6_23*/
	/*月次法定内のみ加算*/
	private NotUseAtr additionWithinMonthlyStatutory;

	public static WorkFlexAdditionSet createFromJavaType(String companyId,
			int calcActualOperation1, int exemptTaxTime1, int incChildNursingCare1, 
			int predeterminedOvertime1,
			int additionTime1, int notDeductLateleave1, int exemptTaxTime2,
			int minusAbsenceTime2, int calcActualOperation2, int incChildNursingCare2, 
			int notDeductLateleave2,
			int predeterminDeficiency2,int additionTime2, 
			int enableSetPerWorkHour1, int enableSetPerWorkHour2,
			int additionWithinMonthlyStatutory) {
		return new WorkFlexAdditionSet(companyId, EnumAdaptor.valueOf(calcActualOperation1, CalcActualOperationAtr.class),
				exemptTaxTime1, incChildNursingCare1, EnumAdaptor.valueOf(predeterminedOvertime1,PredExcessTimeflexAtr.class) , additionTime1, notDeductLateleave1
				, exemptTaxTime2, minusAbsenceTime2, EnumAdaptor.valueOf(calcActualOperation2, CalcActualOperationAtr.class),
				incChildNursingCare2, notDeductLateleave2, EnumAdaptor.valueOf(predeterminDeficiency2,PredExcessTimeflexAtr.class), additionTime2,
				enableSetPerWorkHour1 == 1 ? true : false, enableSetPerWorkHour2 == 1 ? true : false,
				EnumAdaptor.valueOf(additionWithinMonthlyStatutory, NotUseAtr.class));
	}
}
