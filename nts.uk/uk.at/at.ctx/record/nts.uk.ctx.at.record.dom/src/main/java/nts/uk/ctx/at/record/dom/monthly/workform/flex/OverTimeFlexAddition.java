package nts.uk.ctx.at.record.dom.monthly.workform.flex;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * 残業フレックス加算
 * @author shuichu_ishida
 */
@Getter
public class OverTimeFlexAddition {

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
