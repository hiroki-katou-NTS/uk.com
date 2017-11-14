package nts.uk.ctx.bs.company.dom.company;

/**
 * 期首月
 * @author yennth
 *
 */
public enum MonthStr {
	one(1, "1月"),
	two(2, "2月"),
	three(3, "3月"),
	four(4, "4月"),
	five(5, "5月"),
	six(6, "6月"),
	seven(7, "7月"),
	eight(8, "8月"),
	nine(9, "9月"),
	ten(10, "10月"),
	eleven(11, "11月"),
	twelve(12, "12月");
	public final int value;
	public String month;
	private MonthStr (int value, String month){
		this.value = value;
		this.month = month;
	}
}
