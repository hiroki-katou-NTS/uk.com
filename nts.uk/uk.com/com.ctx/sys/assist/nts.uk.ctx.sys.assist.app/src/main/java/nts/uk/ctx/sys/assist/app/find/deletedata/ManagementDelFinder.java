package nts.uk.ctx.sys.assist.app.find.deletedata;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.deletedata.ManagementDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.ManagementDeletionRepository;

@Stateless
/**
* データ保存動作管理
*/
public class ManagementDelFinder
{

    @Inject
    private ManagementDeletionRepository repository;
 
    public ManagementDelDto findManagementDelById(String delId){
    	Optional<ManagementDeletion> optManagementDel = this.repository.getManagementDeletionById(delId);
		if (optManagementDel.isPresent()) {
			return ManagementDelDto.fromDomain(optManagementDel.get());
		}
		return null;
    }

}
