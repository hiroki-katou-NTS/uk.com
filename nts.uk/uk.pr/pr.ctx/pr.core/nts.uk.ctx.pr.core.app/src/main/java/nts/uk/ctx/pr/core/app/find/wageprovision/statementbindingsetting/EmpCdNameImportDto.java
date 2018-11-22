package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpCdNameImport;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class EmpCdNameImportDto {

    /** The code. */
    private String code;

    /** The name. */
    private String name;

    public static List<EmpCdNameImportDto> fromDomain(List<EmpCdNameImport> empCdNameImport){
        List<EmpCdNameImportDto> empCdNameImportDto = new ArrayList<>();
        empCdNameImportDto = empCdNameImport.stream().map(item ->{
            return new EmpCdNameImportDto(item.getCode(),item.getName());
        }).collect(Collectors.toList());

        return empCdNameImportDto;
    }
}
