package nts.uk.ctx.exio.app.command.exo.authset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;
import nts.uk.shr.com.permit.RestoreAvailabilityPermission;

import java.util.List;

@Value
public class RegisterOrUpdateExOutputCtgAuthSettingCommand {
    String roleId;

    List<FunctionAuthSetting> functionAuthSettings;
}

@NoArgsConstructor
class AvailabilityPermissionData implements RestoreAvailabilityPermission {
    String cid;

    String roleId;

    int functionNo;

    boolean available;

    public AvailabilityPermissionData(String cid, String roleId, int functionNo, boolean available) {
        this.cid = cid;
        this.roleId = roleId;
        this.functionNo = functionNo;
        this.available = available;
    }

    @Override
    public String companyId() {
        return this.cid;
    }

    @Override
    public String roleId() {
        return this.roleId;
    }

    @Override
    public int functionNo() {
        return this.functionNo;
    }

    @Override
    public boolean isAvailable() {
        return this.available;
    }

}

@AllArgsConstructor
@NoArgsConstructor
@Getter
class FunctionAuthSetting {
    private int functionNo;

    private boolean available;
}


