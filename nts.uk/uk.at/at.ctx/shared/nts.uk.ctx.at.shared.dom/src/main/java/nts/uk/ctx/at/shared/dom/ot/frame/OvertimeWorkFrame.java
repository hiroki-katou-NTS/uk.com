/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.ot.frame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWorkEnum;

@Getter
@Setter
/**
 * The Class OvertimeWorkFrame.
 */
//残業枠枠
public class OvertimeWorkFrame extends AggregateRoot{
	
	/** The company id. */
	// 会社ID
	private String companyId;
	
	/** The overtime work fr no. */
	//残業枠NO
	private OvertimeWorkFrameNo overtimeWorkFrNo;
	
	/** The use classification. */
	//使用区分
	private NotUseAtr useClassification;
	
	/** The transfer fr name. */
	//振替枠名称
	private OvertimeWorkFrameName transferFrName;
	
	/** The overtime work fr name. */
	//残業枠名称
	private OvertimeWorkFrameName overtimeWorkFrName;
	
	/** The role. */
	// 役割
	private RoleOvertimeWorkEnum role;
	
	/** The transfer atr. */
	// 代休振替対象
	private NotUseAtr transferAtr;
	
	/**
	 * Instantiates a new overtime work frame.
	 *
	 * @param memento the memento
	 */
	public OvertimeWorkFrame(OvertimeWorkFrameGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.overtimeWorkFrNo = memento.getOvertimeWorkFrameNo();
		this.useClassification = memento.getUseClassification();
		this.transferFrName = memento.getTransferFrameName();
		this.overtimeWorkFrName = memento.getOvertimeWorkFrameName();
		this.role = memento.getRole();
		this.transferAtr = memento.getTransferAtr();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(OvertimeWorkFrameSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setOvertimeWorkFrameNo(this.overtimeWorkFrNo);
		memento.setUseClassification(this.useClassification);
		memento.setTransferFrameName(this.transferFrName);
		memento.setOvertimeWorkFrameName(this.overtimeWorkFrName);
		memento.setRole(this.role);
		memento.setTransferAtr(this.transferAtr);
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
		result = prime * result + ((overtimeWorkFrNo == null) ? 0 : overtimeWorkFrNo.hashCode());
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
		OvertimeWorkFrame other = (OvertimeWorkFrame) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		
		if (overtimeWorkFrNo == null) {
			if (other.overtimeWorkFrNo != null)
				return false;
		} else if (!overtimeWorkFrNo.equals(other.overtimeWorkFrNo))
			return false;
		
		return true;
	}
	
	public boolean isUse() {
		return useClassification == NotUseAtr.USE;
	}
	
	/**
	 * 	[1] 残業枠NOに対応する日次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getDaiLyAttendanceIdByNo() {
		switch(this.overtimeWorkFrNo.v().intValue()) {
		case 1:
			return Arrays.asList(216,217,218,219,220,767,838);
		case 2: 
			return Arrays.asList(221,222,223,224,225,768,839);
		case 3: 
			return Arrays.asList(226,227,228,229,230,769,840);
		case 4: 
			return Arrays.asList(231,232,233,234,235,770,841);
		case 5: 
			return Arrays.asList(236,237,238,239,240,771,842);
		case 6: 
			return Arrays.asList(241,242,243,244,245,772,843);
		case 7: 
			return Arrays.asList(246,247,248,249,250,773,844);
		case 8: 
			return Arrays.asList(251,252,253,254,255,774,845);
		case 9: 
			return Arrays.asList(256,257,258,259,260,775,846);
		default : //10
			return Arrays.asList(261,262,263,264,265,776,847);
		}
	}
	
	/**
	 * 	[2] 残業枠NOに対応する月次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getMonthlyAttendanceIdByNo() {
		switch(this.overtimeWorkFrNo.v().intValue()) {
		case 1:
			return Arrays.asList(35,46,57,90,1364,1375,1386,1804,1814,2203,2213);
		case 2: 
			return Arrays.asList(36,47,58,91,1365,1376,1387,1805,1815,2204,2214);
		case 3: 
			return Arrays.asList(37,48,59,92,1366,1377,1388,1806,1816,2205,2215);
		case 4: 
			return Arrays.asList(38,49,60,93,1367,1378,1389,1807,1817,2206,2216);
		case 5: 
			return Arrays.asList(39,50,61,94,1368,1379,1390,1808,1818,2207,2217);
		case 6: 
			return Arrays.asList(40,51,62,95,1369,1380,1391,1809,1819,2208,2218);
		case 7: 
			return Arrays.asList(41,52,63,96,1370,1381,1392,1810,1820,2209,2219);
		case 8: 
			return Arrays.asList(42,53,64,97,1371,1382,1393,1811,1821,2210,2220);
		case 9: 
			return Arrays.asList(43,54,65,98,1372,1383,1394,1812,1822,2211,2221);
		default : //10
			return Arrays.asList(44,55,66,99,1373,1384,1395,1813,1823,2212,2222);
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

	public OvertimeWorkFrame(String companyId, OvertimeWorkFrameNo overtimeWorkFrNo, NotUseAtr useClassification,
			OvertimeWorkFrameName transferFrName, OvertimeWorkFrameName overtimeWorkFrName, RoleOvertimeWorkEnum role,
			NotUseAtr transferAtr) {
		super();
		this.companyId = companyId;
		this.overtimeWorkFrNo = overtimeWorkFrNo;
		this.useClassification = useClassification;
		this.transferFrName = transferFrName;
		this.overtimeWorkFrName = overtimeWorkFrName;
		this.role = role;
		this.transferAtr = transferAtr;
	}
	
	
}
