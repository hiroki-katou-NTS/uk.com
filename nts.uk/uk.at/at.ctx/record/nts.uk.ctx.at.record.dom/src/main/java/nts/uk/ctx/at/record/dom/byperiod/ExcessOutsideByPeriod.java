package nts.uk.ctx.at.record.dom.byperiod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;

/**
 * 期間別の時間外超過
 * @author shuichu_ishida
 */
@Getter
public class ExcessOutsideByPeriod implements Cloneable {

	/** 時間外超過 */
	private Map<Integer, ExcessOutsideItemByPeriod> excessOutsideItems;
	
	/**
	 * コンストラクタ
	 */
	public ExcessOutsideByPeriod(){
		
		this.excessOutsideItems = new HashMap<>();
	}
	
	/**
	 * ファクトリー
	 * @param excessOutsideItems 時間外超過
	 * @return 期間別の時間外超過
	 */
	public static ExcessOutsideByPeriod of(
			List<ExcessOutsideItemByPeriod> excessOutsideItems){
		
		ExcessOutsideByPeriod domain = new ExcessOutsideByPeriod();
		for (val excessOutsideItem : excessOutsideItems){
			val breakdownNo = excessOutsideItem.getBreakdownNo();
			domain.excessOutsideItems.putIfAbsent(breakdownNo, excessOutsideItem);
		}
		return domain;
	}
	
	@Override
	public ExcessOutsideByPeriod clone() {
		ExcessOutsideByPeriod cloned = new ExcessOutsideByPeriod();
		try {
			for (val item : this.excessOutsideItems.entrySet()){
				cloned.excessOutsideItems.putIfAbsent(item.getKey(), item.getValue().clone());
			}
		}
		catch (Exception e){
			throw new RuntimeException("ExcessOutsideByPeriod clone error.");
		}
		return cloned;
	}
}
