package nts.uk.ctx.pr.core.app.find.companystatuwrite;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateCompanyWriteDto {

    private String codeSelected;
    private List<CusCompanyWriteDto> companies;
    private String msg;
    public CreateCompanyWriteDto() {
        super();
    }
}
