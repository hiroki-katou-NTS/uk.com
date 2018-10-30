package nts.uk.ctx.at.function.ac.workrecord.identificationstatus.identityconfirmprocess;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.adapter.workrecord.identificationstatus.identityconfirmprocess.IdentityConfirmProcessAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.identificationstatus.identityconfirmprocess.IdentityConfirmProcessImport;
import nts.uk.ctx.at.function.dom.adapter.workrecord.identificationstatus.identityconfirmprocess.SelfConfirmErrorImport;
import nts.uk.ctx.at.record.pub.workrecord.identificationstatus.identityconfirmprocess.IdentityConfirmProcessExport;
import nts.uk.ctx.at.record.pub.workrecord.identificationstatus.identityconfirmprocess.IdentityConfirmProcessPub;

/**
 * 
 * @author thuongtv
 *
 */

@Stateless
public class IdentityConfirmProcessAcFinder implements IdentityConfirmProcessAdapter {

	@Inject
	private IdentityConfirmProcessPub identityConfirmProcessPub;

	@Override
	public IdentityConfirmProcessImport getIdentityConfirmProcess(String cid) {
		IdentityConfirmProcessExport objecReturn= identityConfirmProcessPub.getIdentityConfirmProcess(cid);
		if (objecReturn != null) {
			Optional<SelfConfirmErrorImport> yourSelfConfirmError = Optional.empty();
			if(objecReturn.getYourSelfConfirmError().isPresent()){
				yourSelfConfirmError = Optional.of(EnumAdaptor.valueOf(objecReturn.getYourSelfConfirmError().get().value, SelfConfirmErrorImport.class));
			}
			return new IdentityConfirmProcessImport(objecReturn.getCompanyId(), objecReturn.isUseConfirmByYourself(), objecReturn.isUseIdentityOfMonth(),
					yourSelfConfirmError);
		} else {
			return null;
		}
	}
}
