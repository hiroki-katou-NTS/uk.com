package nts.uk.ctx.at.record.pubimp.workrecord.identificationstatus.identityconfirmprocess;

import java.util.Optional;

/**
 * author : thuongtv
 */

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.IdentityProcessUseSet;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentityProcessUseSetRepository;
import nts.uk.ctx.at.record.pub.workrecord.identificationstatus.identityconfirmprocess.IdentityConfirmProcessExport;
import nts.uk.ctx.at.record.pub.workrecord.identificationstatus.identityconfirmprocess.IdentityConfirmProcessPub;

@Stateless
public class IdentityConfirmProcessPubImpl implements IdentityConfirmProcessPub {

	@Inject
	IdentityProcessUseSetRepository repo;

	@Override
	public IdentityConfirmProcessExport getIdentityConfirmProcess(String cid) {
		Optional<IdentityProcessUseSet> identityProcess = repo.findByKey(cid);
		if(identityProcess.isPresent())
		return IdentityConfirmProcessExport.fromDomain(identityProcess.get());
		return null;
	}
	
}
