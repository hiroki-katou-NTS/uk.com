package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex;

import java.io.Serializable;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * 残業フレックス加算
 * @author shuichi_ishida
 */
@Getter
public class OverTimeFlexAddition implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 枠NO */
	private OverTimeFrameNo frameNo;
	/** 加算する */
	private UseAtr addition;
	
	/**
	 * コンストラクタ
	 * @param frameNo
	 */
	public OverTimeFlexAddition(OverTimeFrameNo frameNo){
		
		this.frameNo = frameNo;
		this.addition = UseAtr.NOT_USE;
	}
	
	/**
	 * ファクトリー
	 * @param frameNo 枠NO
	 * @param addition 加算する
	 * @return 残業フレックス加算
	 */
	public static OverTimeFlexAddition of(OverTimeFrameNo frameNo, UseAtr addition){
		
		OverTimeFlexAddition domain = new OverTimeFlexAddition(frameNo);
		domain.addition = addition;
		return domain;
	}
}
