package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * 時間外フレックス加算
 * @author shuichi_ishida
 */
@Getter
public class OutsideTimeFlexAddition implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 残業 */
	private Map<OverTimeFrameNo, OverTimeFlexAddition> overTimeMap;
	/** 休日出勤 */
	private Map<HolidayWorkFrameNo, HolidayWorkFlexAddition> holidayWorkMap;
	
	/**
	 * コンストラクタ
	 */
	public OutsideTimeFlexAddition(){
		
		this.overTimeMap = new HashMap<>();
		this.holidayWorkMap = new HashMap<>();
	}
	
	/**
	 * ファクトリー
	 * @param overTimeList 残業
	 * @param holidayWorkList 休日出勤
	 * @return 時間外フレックス加算
	 */
	public static OutsideTimeFlexAddition of(
			List<OverTimeFlexAddition> overTimeList,
			List<HolidayWorkFlexAddition> holidayWorkList){
		
		OutsideTimeFlexAddition domain = new OutsideTimeFlexAddition();
		for (val overTime : overTimeList){
			domain.overTimeMap.putIfAbsent(overTime.getFrameNo(), overTime);
		}
		for (val holidayWork : holidayWorkList){
			domain.holidayWorkMap.putIfAbsent(holidayWork.getFrameNo(), holidayWork);
		}
		return domain;
	}
}
