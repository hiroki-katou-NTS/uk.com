package nts.uk.screen.com.app.find.cmm030.a.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetSetEmployeeListDto {

	private List<String> employeeIds;
}
