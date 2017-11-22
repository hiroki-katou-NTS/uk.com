/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.pred.dto;

import nts.uk.ctx.at.shared.dom.predset.PredetemineTimeSetMemento;
import nts.uk.ctx.at.shared.dom.predset.PrescribedTimezoneSetting;

/**
 * The Class PredDto.
 */
public class PredDto implements PredetemineTimeSetMemento {

	/** The company id. */
	// 会社ID
	public String companyId;

	/** The range time day. */
	// １日の範囲時間
	public int rangeTimeDay;

	/** The sift CD. */
	// コード
	public String siftCD;

	/** The addition set ID. */
	// 所定時間
	public String additionSetID;

	/** The night shift. */
	// 夜勤区分
	public boolean nightShift;

	/** The prescribed timezone setting. */
	// 所定時間帯
	public PrescribedTimezoneSetting prescribedTimezoneSetting;

	/** The start date clock. */
	// 日付開始時刻
	public int startDateClock;

	/** The predetermine. */
	// 残業を含めた所定時間帯を設定する
	public boolean predetermine;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeSetMemento#setCompanyID(java.lang.String)
	 */
	@Override
	public void setCompanyID(String companyID) {
		this.companyId = companyID;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeSetMemento#setRangeTimeDay(int)
	 */
	@Override
	public void setRangeTimeDay(int rangeTimeDay) {
		this.rangeTimeDay = rangeTimeDay;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeSetMemento#setSiftCD(java.lang.String)
	 */
	@Override
	public void setSiftCD(String siftCD) {
		this.siftCD = siftCD;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeSetMemento#setAdditionSetID(java.lang.String)
	 */
	@Override
	public void setAdditionSetID(String additionSetID) {
		this.additionSetID = additionSetID;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeSetMemento#setNightShift(boolean)
	 */
	@Override
	public void setNightShift(boolean nightShift) {
		this.nightShift = nightShift;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeSetMemento#setPrescribedTimezoneSetting(nts.uk.ctx.at.shared.dom.predset.PrescribedTimezoneSetting)
	 */
	@Override
	public void setPrescribedTimezoneSetting(PrescribedTimezoneSetting prescribedTimezoneSetting) {
		this.prescribedTimezoneSetting= prescribedTimezoneSetting;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeSetMemento#setStartDateClock(int)
	 */
	@Override
	public void setStartDateClock(int startDateClock) {
		this.startDateClock = startDateClock;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeSetMemento#setPredetermine(boolean)
	 */
	@Override
	public void setPredetermine(boolean predetermine) {
		this.predetermine = predetermine;
	}
}
