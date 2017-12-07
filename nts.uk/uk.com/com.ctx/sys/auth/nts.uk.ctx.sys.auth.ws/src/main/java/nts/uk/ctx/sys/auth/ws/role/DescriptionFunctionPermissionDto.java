package nts.uk.ctx.sys.auth.ws.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DescriptionFunctionPermissionDto {

    private String functionNo;
    private boolean initialValue;
    private String displayName;
    private int displayOrder;
    private String description;

}
