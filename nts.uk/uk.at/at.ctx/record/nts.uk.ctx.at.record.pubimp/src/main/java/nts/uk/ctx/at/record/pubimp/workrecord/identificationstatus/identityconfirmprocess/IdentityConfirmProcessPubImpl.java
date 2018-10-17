package nts.uk.ctx.at.record.pubimp.workrecord.identificationstatus.identityconfirmprocess;

import java.util.Optional;

/**
 * author : thuongtv
 */

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcessRepository;
import nts.uk.ctx.at.record.pub.workrecord.identificationstatus.identityconfirmprocess.IdentityConfirmProcessExport;
import nts.uk.ctx.at.record.pub.workrecord.identificationstatus.identityconfirmprocess.IdentityConfirmProcessPub;

@Stateless
public class IdentityConfirmProcessPubImpl implements IdentityConfirmProcessPub {

	@Inject
    private IdentityProcessRepository finder;

	@Override
	public IdentityConfirmProcessExport getIdentityConfirmProcess(String cid) {
		Optional<IdentityProcess> identityProcess = finder.getIdentityProcessById(cid);
		if(identityProcess.isPresent())
		return IdentityConfirmProcessExport.fromDomain(identityProcess.get());
		return null;
	}
	
}
