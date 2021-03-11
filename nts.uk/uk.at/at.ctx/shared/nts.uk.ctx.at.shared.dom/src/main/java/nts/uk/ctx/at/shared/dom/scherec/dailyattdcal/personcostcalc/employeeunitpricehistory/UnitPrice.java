package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.employeeunitpricehistory;

/**
 * 
 * @author Doan Duy Hung
 * enum : 社員時間単価NO
 */

public enum UnitPrice {
	
	// 単価１
	Price_1(0),
	
	// 単価2
	Price_2(1), 
	
	// 単価3
	Price_3(2),
	
	// 単価4
	Standard(3),
	
	// 単価5
	Contract(4);
	
	public final int value;
	
	UnitPrice(int value){
		this.value = value;
	}
}
