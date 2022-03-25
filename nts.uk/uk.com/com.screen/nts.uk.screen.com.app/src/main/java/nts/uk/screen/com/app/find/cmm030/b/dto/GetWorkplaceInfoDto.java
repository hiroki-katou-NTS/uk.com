package nts.uk.screen.com.app.find.cmm030.b.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.bs.employee.pub.workplace.AffAtWorkplaceExport;

@Data
@AllArgsConstructor
public class GetWorkplaceInfoDto {

	private List<AffAtWorkplaceExport> workplaces;
}
