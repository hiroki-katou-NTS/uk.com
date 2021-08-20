package nts.uk.ctx.at.shared.dom.dailyattdcal.empunitpricehistory;

/**
 * 
 * @author laitv
 * enum : 社員時間単価NO
 * path:UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.人件費計算.社員単価履歴.社員時間単価NO
 */

public enum UnitPrice {
	
	// 単価１ 
	Price_1(0),
	
	// 単価2
	Price_2(1), 
	
	// 単価3
	Price_3(2),
	
	// 単価4 
	Price_4(3),
	
	// 単価5
	Price_5(4), 
	
	// 単価6
	Price_6(5),
	
	// 単価7 
	Price_7(6),
	
	// 単価8
	Price_8(7), 
	
	// 単価9
	Price_9(8),
	
	// 単価10
	Price_10(9);
	
	public final int value;
	
	UnitPrice(int value){
		this.value = value;
	}
}
