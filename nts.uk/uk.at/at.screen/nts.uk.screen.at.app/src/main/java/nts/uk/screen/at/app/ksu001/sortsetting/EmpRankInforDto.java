package nts.uk.screen.at.app.ksu001.sortsetting;

import lombok.Value;

@Value
public class EmpRankInforDto {
	/**社員ID **/
	public String empId; 
	/** ランクコード **/
	public String rankCode;
	/**ランク記号 **/
	public String rankName;
}
