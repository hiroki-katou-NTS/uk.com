/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workdayoff.frame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@Getter
@Setter
/**
 * The Class WorkdayoffFrame.
 */
//休出枠
@AllArgsConstructor
public class WorkdayoffFrame extends AggregateRoot{
	
	/** The company id. */
	// 会社ID
	private String companyId;
	
	/** The workdayoff fr no. */
	//休出枠NO
	private WorkdayoffFrameNo workdayoffFrNo;
	
	/** The use classification. */
	//使用区分
	private NotUseAtr useClassification;
	
	/** The transfer fr name. */
	//振替枠名称
	private WorkdayoffFrameName transferFrName;
	
	/** The workdayoff fr name. */
	//休出枠名称
	private WorkdayoffFrameName workdayoffFrName;
	
	/** The role */
	//役割
	private WorkdayoffFrameRole role;
	
	/**
	 * Instantiates a new workdayoff frame.
	 *
	 * @param memento the memento
	 */
	public WorkdayoffFrame(WorkdayoffFrameGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.workdayoffFrNo = memento.getWorkdayoffFrameNo();
		this.useClassification = memento.getUseClassification();
		this.transferFrName = memento.getTransferFrameName();
		this.workdayoffFrName = memento.getWorkdayoffFrameName();
		this.role = memento.getRole();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkdayoffFrameSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkdayoffFrameNo(this.workdayoffFrNo);
		memento.setUseClassification(this.useClassification);
		memento.setTransferFrameName(this.transferFrName);
		memento.setWorkdayoffFrameName(this.workdayoffFrName);
		memento.setRole(role);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((workdayoffFrNo == null) ? 0 : workdayoffFrNo.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkdayoffFrame other = (WorkdayoffFrame) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		
		if (workdayoffFrNo == null) {
			if (other.workdayoffFrNo != null)
				return false;
		} else if (!workdayoffFrNo.equals(other.workdayoffFrNo))
			return false;
		
		return true;
	}
	
	/** 対象日の実績から休出時間を求める区分を取得する */
	public HolidayAtr getHolWorkAtrforDailyRecord(Require require, WorkInformation workInfo) {
		
		switch (role) {
		case NON_STATUTORY_HOLIDAYS:
			/** 「法定外休出」を返す */
			return HolidayAtr.NON_STATUTORY_HOLIDAYS;
		case STATUTORY_HOLIDAYS:
			/** 「法定内休出」を返す */
			return HolidayAtr.STATUTORY_HOLIDAYS;
		case MIX_WITHIN_OUTSIDE_STATUTORY:
			
			/** 勤務種類を取得する */
			val holAtr = require.workType(this.companyId, workInfo.getWorkTypeCode().v())
									.map(c -> {
										val typeSet = c.getWorkTypeSet();
										if (typeSet == null) {
											return HolidayAtr.NON_STATUTORY_HOLIDAYS;
										}
										return typeSet.getHolidayAtr();
									}).orElse(HolidayAtr.NON_STATUTORY_HOLIDAYS);
			
			if (holAtr == HolidayAtr.STATUTORY_HOLIDAYS) {
				/** 「法定内休出」を返す */
				return HolidayAtr.STATUTORY_HOLIDAYS;
			}
			
		default:
			/** 「法定外休出」を返す */
			return HolidayAtr.NON_STATUTORY_HOLIDAYS;
		}
	}

	public boolean isUse() {
		return this.useClassification == NotUseAtr.USE;
	}
	
	public static interface Require {
		
		Optional<WorkType> workType(String companyId, String workTypeCd);
	}
	
	/**
	 * 	[1] 休出枠NOに対応する日次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getDaiLyAttendanceIdByNo() {
		switch(this.workdayoffFrNo.v().intValue()) {
		case 1:
			return Arrays.asList(266,267,268,269,270,777,848);
		case 2: 
			return Arrays.asList(271,272,273,274,275,778,849);
		case 3: 
			return Arrays.asList(276,277,278,279,280,779,850);
		case 4: 
			return Arrays.asList(281,282,283,284,285,780,851);
		case 5: 
			return Arrays.asList(286,287,288,289,290,781,852);
		case 6: 
			return Arrays.asList(291,292,293,294,295,782,853);
		case 7: 
			return Arrays.asList(296,297,298,299,300,783,854);
		case 8: 
			return Arrays.asList(301,302,303,304,305,784,855);
		case 9: 
			return Arrays.asList(306,307,308,309,310,785,856);
		default : //10
			return Arrays.asList(311,312,313,314,315,786,857);
		}
	}
	
	/**
	 * 	[2] 休出枠NOに対応する月次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getMonthlyAttendanceIdByNo() {
		switch(this.workdayoffFrNo.v().intValue()) {
		case 1:
			return Arrays.asList(110,121,132,143,154,165,175,1826,1836,2223,2233);
		case 2: 
			return Arrays.asList(111,122,133,144,155,166,176,1827,1837,2224,2234);
		case 3: 
			return Arrays.asList(112,123,134,145,156,167,177,1828,1838,2225,2235);
		case 4: 
			return Arrays.asList(113,124,135,146,157,168,178,1829,1839,2226,2236);
		case 5: 
			return Arrays.asList(114,125,136,147,158,169,179,1830,1840,2227,2237);
		case 6: 
			return Arrays.asList(115,126,137,148,159,170,180,1831,1841,2228,2238);
		case 7: 
			return Arrays.asList(116,127,138,149,160,171,181,1832,1842,2229,2239);
		case 8: 
			return Arrays.asList(117,128,139,150,161,172,182,1833,1843,2230,2240);
		case 9: 
			return Arrays.asList(118,129,140,151,162,173,183,1834,1844,2231,2241);
		default : //10
			return Arrays.asList(119,130,141,152,163,174,184,1835,1845,2232,2242);
		}
	}
	
	/**
	 * 	[3] 利用できない日次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getDailyAttendanceIdNotAvailable() {
		if(this.useClassification == NotUseAtr.NOT_USE) {
			return this.getDaiLyAttendanceIdByNo();
		}
		return new ArrayList<>();
	}
	
	/**
	 * 	[4] 利用できない月次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getMonthlyAttendanceIdNotAvailable() {
		if(this.useClassification == NotUseAtr.NOT_USE) {
			return this.getMonthlyAttendanceIdByNo();
		}
		return new ArrayList<>();
	}
	
	
	
}
