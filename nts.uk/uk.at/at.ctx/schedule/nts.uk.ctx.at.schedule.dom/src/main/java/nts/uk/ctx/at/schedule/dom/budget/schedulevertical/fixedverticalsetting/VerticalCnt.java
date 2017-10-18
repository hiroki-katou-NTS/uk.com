package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting;


import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 縦計時間帯設定
 * @author phongtq
 *
 */
@Getter
public class VerticalCnt extends AggregateRoot {

	/**会社ID**/
	private String companyId;
	
	/** 固定縦計設定NO **/
	private int fixedItemAtr;
	
	/** 縦計回数集計NO **/
	private int verticalCountNo;
	
	public VerticalCnt (String companyId, int fixedItemAtr, int verticalCountNo){
		super();
		this.companyId = companyId;
		this.fixedItemAtr = fixedItemAtr;
		this.verticalCountNo = verticalCountNo;
	}

	public static VerticalCnt createFromJavaType(String companyId, int fixedItemAtr, int verticalCountNo){
		return new VerticalCnt(companyId, fixedItemAtr,verticalCountNo);
	}
}
