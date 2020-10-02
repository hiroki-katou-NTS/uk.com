package nts.uk.screen.at.ws.kaf.kaf021;

import lombok.Getter;
import lombok.Setter;
import nts.uk.screen.at.app.kaf021.query.a.EmployeeBasicInfoDto;

import java.util.List;

@Setter
@Getter
public class EmployeeInfoParam {
    private List<EmployeeBasicInfoDto> employees;
}
