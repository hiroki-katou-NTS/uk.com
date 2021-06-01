package nts.uk.ctx.exio.dom.input.revise.type.codeconvert;

import java.util.Optional;
import java.util.List;

/**
* 受入コード変換
*/
public interface ExternalImportCodeConvertRepository
{

    List<ExternalImportCodeConvert> getAll();
    
    List<ExternalImportCodeConvert> get(String cid);

    Optional<ExternalImportCodeConvert> get(String cid, String convertCd);

    void add(ExternalImportCodeConvert domain);

    void update(ExternalImportCodeConvert domain);

    void remove(String cid, String convertCd);

}
