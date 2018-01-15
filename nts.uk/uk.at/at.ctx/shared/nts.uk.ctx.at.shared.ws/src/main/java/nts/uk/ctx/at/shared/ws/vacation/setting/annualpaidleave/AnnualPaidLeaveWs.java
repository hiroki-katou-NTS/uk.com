/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.vacation.setting.annualpaidleave;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.vacation.setting.annualpaidleave.AnnualPaidLeaveSaveCommand;
import nts.uk.ctx.at.shared.app.command.vacation.setting.annualpaidleave.AnnualPaidLeaveSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.vacation.setting.annualpaidleave.AnnualPaidLeaveFinder;
import nts.uk.ctx.at.shared.app.find.vacation.setting.annualpaidleave.dto.AnnualPaidLeaveSettingFindDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.DisplayDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.MaxDayReference;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPriority;

/**
 * The Class AnnualPaidLeaveWs.
 */
@Path("ctx/at/share/vacation/setting/annualpaidleave/")
@Produces("application/json")
public class AnnualPaidLeaveWs extends WebService {

    /** The annual finder. */
    @Inject
    private AnnualPaidLeaveFinder annualFinder;

    /** The annual paid handler. */
    @Inject
    private AnnualPaidLeaveSaveCommandHandler annualPaidHandler;
    
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
     * Find apply permission.
     *
     * @return the list
     */
    @POST
    @Path("find/applypermission")
    public List<EnumConstant> findApplyPermission() {
        return EnumAdaptor.convertToValueNameList(ApplyPermission.class);
    }
    
    /**
     * Find preemption permit.
     *
     * @return the list
     */
    @POST
    @Path("find/preemptionpermit")
    public List<EnumConstant> findPreemptionPermit() {
        return EnumAdaptor.convertToValueNameList(AnnualPriority.class);
    }
    
    /**
     * Find display division.
     *
     * @return the list
     */
    @POST
    @Path("find/displaydivision")
    public List<EnumConstant> findDisplayDivision() {
        return EnumAdaptor.convertToValueNameList(DisplayDivision.class);
    }
    
    /**
     * Find time vacation digestive unit.
     *
     * @return the list
     */
    @POST
    @Path("find/timeunit")
    public List<EnumConstant> findTimeVacationDigestiveUnit() {
        return EnumAdaptor.convertToValueNameList(TimeDigestiveUnit.class);
    }
    
    /**
     * Find max day reference.
     *
     * @return the list
     */
    @POST
    @Path("find/maxdayreference")
    public List<EnumConstant> findMaxDayReference() {
        return EnumAdaptor.convertToValueNameList(MaxDayReference.class);
    }
    
    /**
     * Save.
     *
     * @param command the command
     */
    @POST
    @Path("save")
    public void save(AnnualPaidLeaveSaveCommand command) {
        this.annualPaidHandler.handle(command);
    }

    /**
     * Find by company id.
     *
     * @return the annual paid leave setting find dto
     */
    @POST
    @Path("find/setting")
    public AnnualPaidLeaveSettingFindDto findByCompanyId() {
        return this.annualFinder.findByCompanyId();
    }
}
