package nts.uk.ctx.exio.dom.exi.codeconvert;

import java.util.Optional;
import java.util.List;

/**
* コード変換詳細
*/
public interface CdConvertDetailsRepository
{

    List<CdConvertDetails> getAllCdConvertDetails();

    Optional<CdConvertDetails> getCdConvertDetailsById(String cid, String convertCd, int lineNumber);

    void add(CdConvertDetails domain);

    void update(CdConvertDetails domain);

    void remove(String cid, String convertCd, int lineNumber);

}
