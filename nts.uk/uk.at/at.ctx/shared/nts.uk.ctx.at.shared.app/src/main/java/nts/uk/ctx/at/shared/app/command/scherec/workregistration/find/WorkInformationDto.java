package nts.uk.ctx.at.shared.app.command.scherec.workregistration.find;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkInformationDto {

    private String cid;
    private Integer frameNo;
    private String code;

}
