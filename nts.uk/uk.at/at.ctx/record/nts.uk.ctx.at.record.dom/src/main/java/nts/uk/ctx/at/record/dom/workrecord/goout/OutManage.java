/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.goout;

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
	 * 	[1] 外出に対応する日次の勤怠項目を絞り込む
	 * @return
	 */
//	public List<Integer> getDaiLyAttendanceID
//	public List<Integer> getDaiLyAttendanceIdByNo() {
//		switch(this.overtimeWorkFrNo.v().intValue()) {
//		case 1:
//			return Arrays.asList(216,217,218,219,220,767,838);
//		case 2: 
//			return Arrays.asList(221,222,223,224,225,768,839);
//		case 3: 
//			return Arrays.asList(226,227,228,229,230,769,840);
//		case 4: 
//			return Arrays.asList(231,232,233,234,235,770,841);
//		case 5: 
//			return Arrays.asList(236,237,238,239,240,771,842);
//		case 6: 
//			return Arrays.asList(241,242,243,244,245,772,843);
//		case 7: 
//			return Arrays.asList(246,247,248,249,250,773,844);
//		case 8: 
//			return Arrays.asList(251,252,253,254,255,774,845);
//		case 9: 
//			return Arrays.asList(256,257,258,259,260,775,846);
//		default : //10
//			return Arrays.asList(261,262,263,264,265,776,847);
//		}
//	}
	
}

