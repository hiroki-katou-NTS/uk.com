/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.goout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

/**
 * @author Hoangdd
 * 外出管理
 */
@Getter
public class OutManage extends AggregateRoot{
	/** The company ID. */
	// 会社ID
	private String companyID;
	
	/** The max usage. */
	// 最大使用回数
	private MaxGoOut maxUsage;
	
	/** The init value reason go out. */
	// 外出理由の初期値
	private GoingOutReason initValueReasonGoOut;
	
	
	/**
	 * Instantiates a new out manage.
	 *
	 * @param memento the memento
	 */
	public OutManage(OutManageGetMemento memento) {
		this.companyID = memento.getCompanyID();
		this.maxUsage = memento.getMaxUsage();
		this.initValueReasonGoOut = memento.getInitValueReasonGoOut();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(OutManageSetMemento memento) {
		memento.setCompanyID(this.companyID);
		memento.setMaxUsage(this.maxUsage.v());
		memento.setInitValueReasonGoOut(this.initValueReasonGoOut.value);
	}

	public OutManage(String companyID, MaxGoOut maxUsage, GoingOutReason initValueReasonGoOut) {
		super();
		this.companyID = companyID;
		this.maxUsage = maxUsage;
		this.initValueReasonGoOut = initValueReasonGoOut;
	}
	
	/**
	 *  [1] 外出に対応する日次の勤怠項目を絞り込む
	 * @return
	 */
	public List<Integer> getDaiLyAttendanceID(){
		List<Integer> listAttdId = new ArrayList<>();
		for(int i = 1;i<=10;i++) {
			listAttdId.addAll(this.getDaiLyAttdIdByNo(i));
		}
		return listAttdId;
	}
	
	
	/**
	 * 	[2] 利用できない日次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getDaiLyAttendanceIDNotAvailable(){
		List<Integer> listAttdId = new ArrayList<>();
		for(int i = this.maxUsage.v()+1;i<=10;i++) {
			listAttdId.addAll(this.getDaiLyAttdIdByNo(i));
		}
		return listAttdId;
	}
	
	private List<Integer> getDaiLyAttdIdByNo(int no) {
		switch(no) {
		case 1:
			return Arrays.asList(86,87,88,1196,1197,1216,1217,90,91);
		case 2: 
			return Arrays.asList(93,94,95,1198,1199,1218,1219,97,98);
		case 3: 
			return Arrays.asList(100,101,102,1200,1201,1220,1221,104,105);
		case 4: 
			return Arrays.asList(107,108,109,1202,1203,1222,1223,111,112);
		case 5: 
			return Arrays.asList(114,115,116,1204,1205,1224,1225,118,119);
		case 6: 
			return Arrays.asList(121,122,123,1206,1207,1226,1227,125,126);
		case 7: 
			return Arrays.asList(128,129,130,1208,1209,1228,1229,132,133);
		case 8: 
			return Arrays.asList(135,136,137,1210,1211,1230,1231,139,140);
		case 9: 
			return Arrays.asList(142,143,144,1212,1213,1232,1233,146,147);
		default : //10
			return Arrays.asList(149,150,151,1214,1215,1234,1235,153,154);
		}
	}
	
}

