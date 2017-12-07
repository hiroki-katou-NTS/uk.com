package nts.uk.ctx.sys.auth.ws.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvialabilityFunctionPermissionDto {

    private String functionNo;
    private boolean availability;
    private String roleId;
    private String companyId;

}
