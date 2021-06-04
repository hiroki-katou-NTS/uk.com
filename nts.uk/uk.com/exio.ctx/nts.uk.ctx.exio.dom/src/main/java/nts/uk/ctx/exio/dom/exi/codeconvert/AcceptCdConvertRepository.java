package nts.uk.ctx.exio.dom.exi.codeconvert;

import java.util.Optional;
import java.util.List;

/**
* 受入コード変換
*/
public interface AcceptCdConvertRepository
{

    List<AcceptCdConvert> getAllAcceptCdConvert();
    
    List<AcceptCdConvert> getAcceptCdConvertByCompanyId(String cid);

    Optional<AcceptCdConvert> getAcceptCdConvertById(String cid, String convertCd);

    void add(AcceptCdConvert domain);

    void update(AcceptCdConvert domain);

    void remove(String cid, String convertCd);

}
