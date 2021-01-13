package nts.uk.ctx.sys.auth.dom.adapter.checkpassword;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CheckBeforeChangePassImport {
    private boolean error;
    private List<PasswordMessageObject> message;
}
