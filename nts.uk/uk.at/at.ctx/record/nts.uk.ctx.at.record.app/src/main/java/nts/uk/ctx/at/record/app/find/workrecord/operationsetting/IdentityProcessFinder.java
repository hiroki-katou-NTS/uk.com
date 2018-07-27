package nts.uk.ctx.at.record.app.find.workrecord.operationsetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcessRepository;

@Stateless
/**
* 本人確認処理の利用設定
*/
public class IdentityProcessFinder
{

    @Inject
    private IdentityProcessRepository finder;

    public List<IdentityProcessDto> getAllIdentityProcess(){
        return finder.getAllIdentityProcess().stream().map(item -> IdentityProcessDto.fromDomain(item))
                .collect(Collectors.toList());
    }
    
    /**
     * @param cid
     * @return
     */
    public IdentityProcessDto getAllIdentityProcessById(String cid){
    	IdentityProcessDto identityProcessDto = null;
    	if (finder.getIdentityProcessById(cid).isPresent()) {
    		identityProcessDto = IdentityProcessDto.fromDomain(finder.getIdentityProcessById(cid).get());
    	}
    	
    	return identityProcessDto;
    }
}
