/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workdayoff.frame;

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
}
