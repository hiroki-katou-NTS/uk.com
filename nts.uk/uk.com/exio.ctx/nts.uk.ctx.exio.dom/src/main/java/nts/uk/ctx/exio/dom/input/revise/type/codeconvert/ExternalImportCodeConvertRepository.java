package nts.uk.ctx.exio.dom.input.revise.type.codeconvert;

import java.util.Optional;
import java.util.List;

public interface ExternalImportCodeConvertRepository {
    Optional<ExternalImportCodeConvert> get(String cid, String convertCd);
}
