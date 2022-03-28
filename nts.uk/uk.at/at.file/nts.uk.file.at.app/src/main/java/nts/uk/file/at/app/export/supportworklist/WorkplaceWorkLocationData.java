package nts.uk.file.at.app.export.supportworklist;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkPlaceInforExport;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;

import java.util.List;

@AllArgsConstructor
@Data
public class WorkplaceWorkLocationData {

    private List<WorkPlaceInforExport> workplaceInfoList;

    private List<WorkLocation> workLocations;
}
