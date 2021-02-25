/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.vacation.setting.nursingleave;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.vacation.setting.nursingleave.ChildNursingLeaveRequest;
import nts.uk.ctx.at.shared.app.command.vacation.setting.nursingleave.NursingLeaveCommand;
import nts.uk.ctx.at.shared.app.command.vacation.setting.nursingleave.NursingLeaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.vacation.setting.managementclassification.lstemployee.childnursing.nextstartdate.ManagementClassificationLstEmployeeDto;
import nts.uk.ctx.at.shared.app.find.vacation.setting.nursingleave.ChildNursingLeaveFinder;
import nts.uk.ctx.at.shared.app.find.vacation.setting.nursingleave.NursingLeaveFinder;
import nts.uk.ctx.at.shared.app.find.vacation.setting.nursingleave.dto.NursingLeaveSettingDto;
import nts.uk.ctx.at.shared.app.find.worktype.absenceframe.AbsenceFrameDto;
import nts.uk.ctx.at.shared.app.find.worktype.absenceframe.AbsenceFrameFinder;
import nts.uk.ctx.at.shared.app.find.worktype.specialholidayframe.SpecialHolidayFrameDto;
import nts.uk.ctx.at.shared.app.find.worktype.specialholidayframe.SpecialHolidayFrameFinder;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Class NursingLeaveWs.
 */
@Path("ctx/at/share/vacation/setting/nursingleave/")
@Produces("application/json")
public class NursingLeaveWs extends WebService {
    
    /** The nursing handler. */
    @Inject
    private NursingLeaveCommandHandler nursingHandler;
    
    /** The nursing finder. */
    @Inject
    private NursingLeaveFinder nursingFinder;
    
    /** The special holiday frame finder. */
    @Inject
    private SpecialHolidayFrameFinder specialHolidayFrameFinder;
    
    /** The absence frame finder. */
    @Inject
    private AbsenceFrameFinder absenceFrameFinder;
    
    /** 介護休暇ダイアログ起動. */
    @Inject
    private ChildNursingLeaveFinder ChildNursingLeaveFinder;
    
    /**
     * Find manage distinct.
     *
     * @return the list
     */
    @POST
    @Path("find/managedistinct")
    public List<EnumConstant> findManageDistinct() {
        return EnumAdaptor.convertToValueNameList(ManageDistinct.class);
    }
    
    /**
     * Save.
     *
     * @param command the command
     */
    @POST
    @Path("save")
    public void save(NursingLeaveCommand command) {
        this.nursingHandler.handle(command);
    }

    /**
     * Find by company id.
     *
     * @return the list
     */
    @POST
    @Path("find/setting")
    public List<NursingLeaveSettingDto> findByCompanyId() {
        return this.nursingFinder.findNursingLeaveByCompanyId();
    }
    
    /**
     * Find list work type code by company id.
     *
     * @return the list
     */
    @POST
    @Path("find/listworktypecode")
    public List<String> findListWorkTypeCodeByCompanyId() {
        return this.nursingFinder.findListWorkTypeCodeByCompanyId();
    }
    
    /**
     * Find all special holiday.
     *
     * @return the list
     */
    @POST
    @Path("find/allspecialholiday")
    public List<SpecialHolidayFrameDto> findAllSpecialHoliday() {
    	return this.specialHolidayFrameFinder.findByCompanyIdWithoutEventAndSpecialHoliday();
    }
    
    /**
     * Find all absence frame.
     *
     * @return the list
     */
    @POST
    @Path("find/allabsenceframce")
    public List<AbsenceFrameDto> findAllAbsenceFrame() {
    	return this.absenceFrameFinder.findByCompanyIdWithoutEventAndSpecialHoliday();
    }
    
    @POST
    @Path("find/childnursingleave")
    public ManagementClassificationLstEmployeeDto findAllChildNursingLeave(ChildNursingLeaveRequest request) {
    	return this.ChildNursingLeaveFinder.findByListEmployeeIdAndRef(request.getLstEmployees(), request.getBaseDate());
    }
    
}
