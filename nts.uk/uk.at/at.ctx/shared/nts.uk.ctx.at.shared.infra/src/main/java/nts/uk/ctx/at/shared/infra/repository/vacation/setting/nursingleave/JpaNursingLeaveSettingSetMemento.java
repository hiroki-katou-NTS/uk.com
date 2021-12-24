/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.nursingleave;

import java.util.List;
import java.util.Optional;

//import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KnlmtNursingLeaveSetPK;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KshmtHdnursingLeave;
import nts.uk.shr.com.time.calendar.MonthDay;

/**
 * The Class JpaNursingVacationSettingSetMemento.
 */
public class JpaNursingLeaveSettingSetMemento implements NursingLeaveSettingSetMemento {
	
	/** The Constant INDEX_NURSING_SETTING. */
    private static final int INDEX_NURSING_SETTING = 0;
    
    /** The Constant INDEX_CHILD_NURSING_SETTING. */
    private static final int INDEX_CHILD_NURSING_SETTING = 1;
    

    /** The entity nursing. */
    private KshmtHdnursingLeave entityNursing;

    /**
     * Instantiates a new jpa nursing leave setting set memento.
     *
     * @param entityNursing the entity nursing
     */
    public JpaNursingLeaveSettingSetMemento(KshmtHdnursingLeave entityNursing) {
        // check exist primary key
        if (entityNursing.getKnlmtNursingLeaveSetPK() == null) {
            entityNursing.setKnlmtNursingLeaveSetPK(new KnlmtNursingLeaveSetPK());
        }
        this.entityNursing = entityNursing;
    }

    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setCompanyId(java.lang.String)
     */
    @Override
    public void setCompanyId(String companyId) {
        this.entityNursing.getKnlmtNursingLeaveSetPK().setCid(companyId);
    }

    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setManageType(nts.uk.ctx.at.shared.dom.
     * vacation.setting.ManageDistinct)
     */
    @Override
    public void setManageType(ManageDistinct manageType) {
        this.entityNursing.setManageType(manageType.value);
    }

    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setNursingCategory(nts.uk.ctx.at.shared.
     * dom.vacation.setting.nursingleave.NursingCategory)
     */
    @Override
    public void setNursingCategory(NursingCategory nursingCategory) {
        this.entityNursing.getKnlmtNursingLeaveSetPK().setNursingCtr(nursingCategory.value);
    }

    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setStartMonthDay(java.lang.Integer)
     */
    @Override
    public void setStartMonthDay(MonthDay  startMonthDay) {
    	int monthday = startMonthDay.getMonth() * 100 + startMonthDay.getDay();
    	this.entityNursing.setStartMonthDay(monthday);
    }

    @Override
	public void setMaxPersonSetting(List<MaxPersonSetting> maxPersonSetting) {
    	if(!maxPersonSetting.isEmpty() && maxPersonSetting.size() == 2) {
    		if(maxPersonSetting.get(INDEX_NURSING_SETTING).getNursingNumberPerson() != null) {
    			this.entityNursing.setNursingNumPerson(maxPersonSetting.get(INDEX_NURSING_SETTING).getNursingNumberPerson().v());
    		} else {
    			this.entityNursing.setNursingNumPerson(0);
    		}
    		if(maxPersonSetting.get(INDEX_NURSING_SETTING).getNursingNumberLeaveDay() !=null) {
    			this.entityNursing.setNursingNumLeaveDay(maxPersonSetting.get(INDEX_NURSING_SETTING).getNursingNumberLeaveDay().v());
    		} else {
    			this.entityNursing.setNursingNumLeaveDay(0);
    		}
    		
    		if(maxPersonSetting.get(INDEX_CHILD_NURSING_SETTING).getNursingNumberPerson() != null) {
    			this.entityNursing.setNursingNumPerson2(maxPersonSetting.get(INDEX_CHILD_NURSING_SETTING).getNursingNumberPerson().v());
    		} else {
    			this.entityNursing.setNursingNumPerson2(0);
    		}
    		if(maxPersonSetting.get(INDEX_CHILD_NURSING_SETTING).getNursingNumberLeaveDay() !=null) {
    			this.entityNursing.setNursingNumLeaveDay2(maxPersonSetting.get(INDEX_CHILD_NURSING_SETTING).getNursingNumberLeaveDay().v());
    		} else {
    			this.entityNursing.setNursingNumLeaveDay2(0);
    		}
    	}
	}

	@Override
	public void setHdspFrameNo(Optional<Integer> hdspFrameNo) {
		if (hdspFrameNo.isPresent())
			this.entityNursing.setHdspFrameNo(hdspFrameNo.get());
		else
			this.entityNursing.setHdspFrameNo(null);
	}

	@Override
	public void setAbsenceFrameNo(Optional<Integer> absenceFrameNo) {
		if (absenceFrameNo.isPresent())
			this.entityNursing.setAbsenceFrameNo(absenceFrameNo.get());
		else
			this.entityNursing.setAbsenceFrameNo(null);
	}

	@Override
	public void setTimeVacationDigestUnit(TimeVacationDigestUnit timeVacationDigestUnit) {
		this.entityNursing.setDigestiveUnit(timeVacationDigestUnit.getDigestUnit().value);
		this.entityNursing.setTimeManageAtr(timeVacationDigestUnit.getManage().value);

	}

	@Override
	public void setNumPer1(Integer numPer1) {
		this.entityNursing.setNursingNumPerson(1);		
	}

	@Override
	public void setNumPer2(Integer numPer2) {
		this.entityNursing.setNursingNumPerson2(2);
	}
	
    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setWorkTypeCodes(java.util.List)
     */
//    @Override
//    public void setWorkTypeCodes(List<String> workTypeCodes) {
//        List<KnlmtNursingWorkType> listWorkType = this.entityNursing.getListWorkType();
//        if (CollectionUtil.isEmpty(listWorkType)) {
//            listWorkType = new ArrayList<>();
//        }
//        List<KnlmtNursingWorkType> newListWorkType = new ArrayList<>();
//        for (int i = 0; i < workTypeCodes.size(); i++) {
//            String workTypeCode = workTypeCodes.get(i);
//            int nursingCtr = this.entityNursing.getKnlmtNursingLeaveSetPK().getNursingCtr();
//            int orderNumber = i;
//
//            KnlmtNursingWorkTypePK pk = new KnlmtNursingWorkTypePK();
//            pk.setCid(this.entityNursing.getKnlmtNursingLeaveSetPK().getCid());
//            pk.setNursingCtr(nursingCtr);
//            pk.setOrderNumber(orderNumber);
//
//            KnlmtNursingWorkType entityWorkType = listWorkType.stream()
//                    .filter(entity -> entity.getKnlmtNursingWorkTypePK().getNursingCtr() == nursingCtr
//                            && entity.getKnlmtNursingWorkTypePK().getOrderNumber() == orderNumber)
//                    .findFirst()
//                    .orElse(new KnlmtNursingWorkType(pk));
//
//            entityWorkType.setWorkTypeCode(workTypeCode);
//            newListWorkType.add(entityWorkType);
//        }
//        this.entityNursing.setListWorkType(newListWorkType);
//    }
}
