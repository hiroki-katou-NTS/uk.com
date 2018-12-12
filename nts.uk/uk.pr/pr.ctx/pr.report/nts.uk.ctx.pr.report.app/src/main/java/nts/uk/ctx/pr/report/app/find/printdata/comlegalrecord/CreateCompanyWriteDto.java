package nts.uk.ctx.pr.report.app.find.printdata.comlegalrecord;

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