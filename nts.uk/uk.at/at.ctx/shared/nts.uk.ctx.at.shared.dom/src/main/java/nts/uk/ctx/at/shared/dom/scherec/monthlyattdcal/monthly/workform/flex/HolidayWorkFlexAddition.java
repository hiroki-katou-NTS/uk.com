package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex;

import java.io.Serializable;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;

/**
 * 休日出勤フレックス加算
 * @author shuichu_ishida
 */
@Getter
public class HolidayWorkFlexAddition implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 枠NO */
	private HolidayWorkFrameNo frameNo;
	/** 加算する */
	private UseAtr addition;
	
	/**
	 * コンストラクタ
	 * @param frameNo
	 */
	public HolidayWorkFlexAddition(HolidayWorkFrameNo frameNo){
		
		this.frameNo = frameNo;
		this.addition = UseAtr.NOT_USE;
	}
	
	/**
	 * ファクトリー
	 * @param frameNo 枠NO
	 * @param addition 加算する
	 * @return 残業フレックス加算
	 */
	public static HolidayWorkFlexAddition of(HolidayWorkFrameNo frameNo, UseAtr addition){
		
		HolidayWorkFlexAddition domain = new HolidayWorkFlexAddition(frameNo);
		domain.addition = addition;
		return domain;
	}
}
