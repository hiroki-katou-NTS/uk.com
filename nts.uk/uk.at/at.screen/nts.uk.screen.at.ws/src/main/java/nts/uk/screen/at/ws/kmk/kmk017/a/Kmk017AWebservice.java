package nts.uk.screen.at.ws.kmk.kmk017.a;

import nts.uk.screen.at.app.kmk.kmk017.worktimeworkplace.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("screen/at/kmk017/a")
@Produces("application/json")
public class Kmk017AWebservice {

    @Inject
    private WorkManagementMultipleProcessor multipleProcessor;

    @Inject
    private WorkTimeSettingProcessor workTimeSettingProcessor;

    @Inject
    private WorkTimeWorkplaceProcessor workTimeWorkplaceProcessor;

    @Inject
    private ChooseWorkplaceProcessor chooseWorkplaceProcessor;

    @POST
    @Path("multipleWork")
    public WorkManagementMultipleDto getMultipleWork() {
        return this.multipleProcessor.find();
    }

    @POST
    @Path("workTimeSetting")
    public List<WorkTimeSettingDto> getWorkTimeSetting() {
        return this.workTimeSettingProcessor.findWorkTimeSetting();
    }

    @POST
    @Path("workTimeWorkpalce")
    public List<WorkTimeWorkplaceDto> getWorkTimeWorkplace() {
        return this.workTimeWorkplaceProcessor.findWorkTimeWorkplace();
    }

    @POST
    @Path("getDetail")
    public List<ChooseWorkplaceDto> getWorkTimeWorkplace(RequestPrams requestPrams) {
        return this.chooseWorkplaceProcessor.getWorkingHoursAtWork(requestPrams);
    }

}
