package nts.uk.ctx.at.record.app.find.workrecord.operationsetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcessRepository;

@Stateless
/**
* 承認処理の利用設定
*/
public class ApprovalProcessFinder
{

    @Inject
    private ApprovalProcessRepository finder;

    public List<ApprovalProcessDto> getAllApprovalProcess(){
        return finder.getAllApprovalProcess().stream().map(item -> ApprovalProcessDto.fromDomain(item))
                .collect(Collectors.toList());
    }
    
    public ApprovalProcessDto getApprovalProcessById(String cid){
    	ApprovalProcessDto approvalProcessDto = null;
    	if (finder.getApprovalProcessById(cid).isPresent()) {
    		ApprovalProcess approvalProcess = finder.getApprovalProcessById(cid).get();
    		approvalProcessDto = ApprovalProcessDto.fromDomain(approvalProcess);
    	}
    	
    	return  approvalProcessDto;
    }
}
