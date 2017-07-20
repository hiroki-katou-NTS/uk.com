/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.dailypattern;

import java.util.ArrayList;
import java.util.List;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetMemento;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetting;
import nts.uk.ctx.at.shared.infra.entity.dailypattern.KdpstDailyPatternSet;
import nts.uk.ctx.at.shared.infra.entity.dailypattern.KdpstDailyPatternSetPK;
import nts.uk.ctx.at.shared.infra.entity.dailypattern.KdpstDailyPatternVal;
import nts.uk.ctx.at.shared.infra.entity.dailypattern.KdpstDailyPatternValPK;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaDailyPatternSetMemento.
 */
public class JpaDailyPatternSetMemento implements DailyPatternSetMemento{
	
    /** The pattern calendar. */
    private KdpstDailyPatternSet patternCalendar;

	/**
	 * Instantiates a new jpa daily pattern set memento.
	 *
	 * @param patternCalendar the pattern calendar
	 */
//    private KcvmtContCalendarVal patternCalendarVal;
    
	/**
	 * @param patternCalendar
	 */
	public JpaDailyPatternSetMemento(KdpstDailyPatternSet patternCalendar) {
		  // check exist primary key
        if (patternCalendar.getKcsmtContCalendarSetPK() == null) {
        	patternCalendar.setKcsmtContCalendarSetPK(new KdpstDailyPatternSetPK());
        }
		this.patternCalendar = patternCalendar;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String setCompanyId) {
		 this.patternCalendar.getKcsmtContCalendarSetPK().setCid(setCompanyId);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarSetMemento#setPatternCode(java.lang.String)
	 */
	@Override
	public void setPatternCode(String setPatternCode) {
		this.patternCalendar.getKcsmtContCalendarSetPK().setPatternCd(setPatternCode);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarSetMemento#setPatternName(java.lang.String)
	 */
	@Override
	public void setPatternName(String setPatternName) {
		this.patternCalendar.setPatternName(setPatternName);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarSetMemento#setWorkTypeCodes(java.util.List)
	 */
	@Override
	public void setWorkTypeCodes(List<String> setWorkTypeCodes) {
		List<KdpstDailyPatternVal> listWorkType = this.patternCalendar.getListContCalender();
        if (CollectionUtil.isEmpty(listWorkType)) {
            listWorkType = new ArrayList<>();
        }
        for (int i = 0; i < setWorkTypeCodes.size(); i++) {
            String workTypeCode = setWorkTypeCodes.get(i);
            
            KdpstDailyPatternValPK pk = new KdpstDailyPatternValPK();
            pk.setCid(this.patternCalendar.getKcsmtContCalendarSetPK().getCid());
            pk.setDispOrder(i);
            pk.setPatternCd(this.patternCalendar.getKcsmtContCalendarSetPK().getPatternCd());
            
            KdpstDailyPatternVal contCalendarVal = listWorkType.stream()
                    .filter(entity -> entity.getKcvmtContCalendarValPK().getPatternCd() == pk.getPatternCd()
                    		&& entity.getKcvmtContCalendarValPK().getDispOrder() == pk.getDispOrder())
		            .findFirst()
		            .orElse(new KdpstDailyPatternVal(pk));
            
            contCalendarVal.setWorkTypeSetCd(workTypeCode);
            listWorkType.add(contCalendarVal);
        }
        this.patternCalendar.setListContCalender(listWorkType);
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarSetMemento#setWorkHouseCodes(java.util.List)
	 */
	@Override
	public void setWorkHouseCodes(List<String> setWorkHouseCodes) {
		List<KdpstDailyPatternVal> listWorkType = this.patternCalendar.getListContCalender();
        if (CollectionUtil.isEmpty(listWorkType)) {
            listWorkType = new ArrayList<>();
        }
        for (int i = 0; i < setWorkHouseCodes.size(); i++) {
            String workTypeCode = setWorkHouseCodes.get(i);
            
            KdpstDailyPatternValPK pk = new KdpstDailyPatternValPK();
            pk.setCid(this.patternCalendar.getKcsmtContCalendarSetPK().getCid());
            pk.setDispOrder(i);
            pk.setPatternCd(this.patternCalendar.getKcsmtContCalendarSetPK().getPatternCd());
            
            KdpstDailyPatternVal contCalendarVal = listWorkType.stream()
                    .filter(entity -> entity.getKcvmtContCalendarValPK().getPatternCd() == pk.getPatternCd()
                    		&& entity.getKcvmtContCalendarValPK().getDispOrder() == pk.getDispOrder())
		            .findFirst()
		            .orElse(new KdpstDailyPatternVal(pk));
            
            contCalendarVal.setWorkTypeSetCd(workTypeCode);
            listWorkType.add(contCalendarVal);
        }
        this.patternCalendar.setListContCalender(listWorkType);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarSetMemento#setCalendarSetting(nts.uk.ctx.at.shared.dom.patterncalendar.CalendarSetting)
	 */
	@Override
	public void setCalendarSetting(DailyPatternSetting setCalendarSetting) {
//		 JpaCalendarSettingSetMemento memento = new JpaCalendarSettingSetMemento(this.patternCalendarVal);
//		 setCalendarSetting.saveToMemento(memento);
	}
	
	
	
	
	
	
}
