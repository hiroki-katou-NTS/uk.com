package nts.uk.ctx.exio.app.find.exi.codeconvert;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.exio.dom.exi.codeconvert.AcceptCdConvertRepository;

@Stateless
/**
* 受入コード変換
*/
public class AcceptCdConvertFinder
{

    @Inject
    private AcceptCdConvertRepository finder;

    public List<AcceptCdConvertDto> getAllAcceptCdConvert(){
        return finder.getAllAcceptCdConvert().stream().map(item -> AcceptCdConvertDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
