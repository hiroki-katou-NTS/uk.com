package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calculationsettings.shorttimework;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSetting;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.childcareset.ShortTimeWorkGetRange;

/**
 * 短時間勤務の計算
 * @author shuichi_ishida
 */
@Getter
public class CalcOfShortTimeWork {

	/** 会社ID */
	private String companyId;
	/** 計算方法 */
	private CalcMethodOfShortTimeWork calcMethod;
	
	/**
	 * コンストラクタ
	 * @param companyId 会社ID
	 */
	public CalcOfShortTimeWork(String companyId){
		this.companyId = companyId;
		this.calcMethod = CalcMethodOfShortTimeWork.WITHIOUT;
	}
	
	/**
	 * ファクトリー
	 * @param companyId 会社ID
	 * @param calcMethod 計算方法
	 * @return 短時間勤務の計算
	 */
	public static CalcOfShortTimeWork of(
			String companyId,
			CalcMethodOfShortTimeWork calcMethod){
		
		CalcOfShortTimeWork myclass = new CalcOfShortTimeWork(companyId);
		myclass.calcMethod = calcMethod;
		return myclass;
	}
	
	/**
	 * ファクトリー（Java型）
	 * @param companyId 会社ID
	 * @param calcMethod 計算方法
	 * @return 短時間勤務の計算
	 */
	public static CalcOfShortTimeWork createFromJavaType(
			String companyId,
			int calcMethod){
		
		CalcOfShortTimeWork myclass = new CalcOfShortTimeWork(companyId);
		myclass.calcMethod = EnumAdaptor.valueOf(calcMethod, CalcMethodOfShortTimeWork.class);
		return myclass;
	}
	
	/**
	 * 内数外数による取得範囲の判断
	 * @param childCareAtr 育児介護区分
	 * @param addSetting 加算設定
	 * @param commonSet 就業時間帯の共通設定
	 * @return 短時間勤務取得範囲
	 */
	public ShortTimeWorkGetRange checkGetRangeByWithinOut(
			ChildCareAtr childCareAtr,
			AddSetting addSetting,
			WorkTimezoneCommonSet commonSet){

		// 計算ｊ方法を確認する
		if (this.calcMethod == CalcMethodOfShortTimeWork.WITHIOUT){
			// 控除設定による短時間勤務取得範囲の判断
			return addSetting.checkShortTimeWorkGetRangeByDeductSet(childCareAtr, commonSet);
		}
		// 結果　←　「そのまま取得する」
		return ShortTimeWorkGetRange.NORMAL_GET;
	}
}
