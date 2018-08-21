package nts.uk.ctx.sys.assist.infra.repository.mastercopy.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.sys.assist.dom.mastercopy.handler.DataCopyHandler;

/**
 * @author locph
 */
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class PersonalInfoDataCopyHandler implements DataCopyHandler {
//    private CopyMethod copyMethod;
//    private String companyId;
//    private PersonalInfoDataCopyAdapter adapter;

    @Override
    public void doCopy() {
//       adapter.copy(companyId, copyMethod.value);
    }
}
