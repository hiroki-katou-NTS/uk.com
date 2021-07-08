package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.codeconvert;

import java.util.Optional;

public interface ExternalImportCodeConvertRepository {
    Optional<ExternalImportCodeConvert> get(String cid, CodeConvertCode convertCd);
}
