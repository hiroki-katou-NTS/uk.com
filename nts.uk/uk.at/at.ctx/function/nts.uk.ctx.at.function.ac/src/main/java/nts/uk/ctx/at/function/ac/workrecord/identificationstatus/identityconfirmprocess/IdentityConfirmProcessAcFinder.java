package nts.uk.ctx.at.function.ac.workrecord.identificationstatus.identityconfirmprocess;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.workrecord.identificationstatus.identityconfirmprocess.IdentityConfirmProcessAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.identificationstatus.identityconfirmprocess.IdentityConfirmProcessImport;
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
			return new IdentityConfirmProcessImport(objecReturn.getCid(), objecReturn.getUseDailySelfCk(),
					objecReturn.getUseMonthSelfCK(), objecReturn.getYourselfConfirmError());
		} else {
			return null;
		}
	}
}
