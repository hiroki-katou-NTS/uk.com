package nts.uk.screen.com.app.find.cmm030.b.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.bs.employee.pub.employee.ResultRequest600Export;

@Data
@AllArgsConstructor
public class GetApprovalAuthorityDto {

	private List<ResultRequest600Export> employees;
}
