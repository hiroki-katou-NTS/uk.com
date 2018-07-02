package nts.uk.ctx.exio.dom.exo.cdconvert;

import java.util.Optional;
import java.util.List;

/**
* コード変換詳細
*/
public interface CdConvertDetailRepository
{

    List<CdConvertDetail> getAllCdConvertDetail();

    Optional<CdConvertDetail> getCdConvertDetailById();

    void add(CdConvertDetail domain);

    void update(CdConvertDetail domain);

    void remove();

}
