package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.premiumtime;

import java.io.Serializable;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 集計割増時間
 * @author shuichi_ishida
 */
@Getter
public class AggregatePremiumTime implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 割増時間項目No */
	private int premiumTimeItemNo;
	/** 時間 */
	private AttendanceTimeMonth time;
	
	/**
	 * コンストラクタ
	 */
	public AggregatePremiumTime(int premiumTimeItemNo){
		
		this.premiumTimeItemNo = premiumTimeItemNo;
		this.time = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param premiumTimeItemNo 割増時間項目No
	 * @param time 時間
	 * @return 集計割増時間
	 */
	public static AggregatePremiumTime of(
			int premiumTimeItemNo,
			AttendanceTimeMonth time){
		
		val domain = new AggregatePremiumTime(premiumTimeItemNo);
		domain.time = time;
		return domain;
	}
	
	/**
	 * 時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinutesToTime(int minutes){
		this.time = this.time.addMinutes(minutes);
	}
}
