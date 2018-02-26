package nts.uk.ctx.exio.app.find.exi.codeconvert;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.exio.dom.exi.codeconvert.CdConvertDetailsRepository;

@Stateless
/**
* コード変換詳細
*/
public class CdConvertDetailsFinder
{

    @Inject
    private CdConvertDetailsRepository finder;

    public List<CdConvertDetailsDto> getAllCdConvertDetails(){
        return finder.getAllCdConvertDetails().stream().map(item -> CdConvertDetailsDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
