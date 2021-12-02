/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.ac.find.maildestination;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.env.pub.maildestination.IMailDestinationPub;
import nts.uk.ctx.sys.env.pub.maildestination.MailDestination;
import nts.uk.ctx.sys.env.pub.mailnoticeset.maildestination.MailAddressNotificationExport;
import nts.uk.ctx.sys.env.pub.mailnoticeset.maildestination.MailDestinationExport;
import nts.uk.ctx.sys.env.pub.mailnoticeset.maildestination.MailDestinationFunctionManageExport;
import nts.uk.ctx.sys.env.pub.mailnoticeset.maildestination.MailDestinationFunctionManagePub;
import nts.uk.ctx.sys.gateway.dom.loginold.adapter.MailDestinationAdapter;
import nts.uk.ctx.sys.gateway.dom.loginold.dto.AvailableMailAddressImport;
import nts.uk.ctx.sys.gateway.dom.loginold.dto.MailAddressNotificationImport;
import nts.uk.ctx.sys.gateway.dom.loginold.dto.MailDestiImport;
import nts.uk.ctx.sys.gateway.dom.loginold.dto.MailDestinationFunctionManageImport;
import nts.uk.ctx.sys.gateway.dom.loginold.dto.MailDestinationImport;
import nts.uk.ctx.sys.gateway.dom.loginold.dto.SentMailListImport;

/**
 * The Class MailDestinationAdapterImpl.
 */
@Stateless
public class MailDestinationAdapterImpl implements MailDestinationAdapter {

	/** The i mail destination pub. */
	@Inject
	private IMailDestinationPub iMailDestinationPub;
	
	@Inject
	private MailDestinationFunctionManagePub mailDestinationFunctionManagePub;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.login.adapter.MailDestinationAdapter#getMailofEmployee(java.lang.String, java.util.List, java.lang.Integer)
	 */
	@Override
	public MailDestinationImport getMailofEmployee(String cid, List<String> lstSid, Integer functionId) {
		MailDestinationImport mailDestinationImport = new MailDestinationImport();
		List<MailDestination> lstMail = iMailDestinationPub.getEmpEmailAddress(cid, lstSid, functionId);
		lstMail.stream().forEach(i -> {
			List<String> mailAdds = new ArrayList<>();
			i.getOutGoingMails().stream().forEach(e -> {
				if (e.getEmailAddress() != null) {
					mailAdds.add(e.getEmailAddress());
				}
			});
			mailDestinationImport.addMail(mailAdds);
		});
		List<String> distintMailLst = mailDestinationImport.getOutGoingMails().stream().distinct()
				.collect(Collectors.toList());
		mailDestinationImport.setOutGoingMails(distintMailLst);
		return mailDestinationImport;
	}
	
	/**
	 * Gets the mail desti of employee.
	 *
	 * @param cid the cid
	 * @param lstSid the lst sid
	 * @param functionId the function id
	 * @return the mail desti of employee
	 */
	@Override
	public MailDestiImport getMailDestiOfEmployee(String cid, List<String> lstSid, Integer functionId) {
		MailDestinationExport mailDestinationExport = this.mailDestinationFunctionManagePub.getEmployeeMails(cid, lstSid, functionId);
		MailDestiImport mailDestiImport = convertMailExportToImport(mailDestinationExport);
		return mailDestiImport;
	}
	
	/**
	 * Convert mail export to import.
	 *
	 * @param destinationExport the destination export
	 * @return the mail desti import
	 */
	private static MailDestiImport convertMailExportToImport(MailDestinationExport destinationExport) {
		MailAddressNotificationExport addressNotificationExport = destinationExport.getMailAddressNotification();
		// set SentMailListImport
		List<SentMailListImport> sentMailLists = destinationExport.getSentMailLists().stream()
																	.map(t -> new SentMailListImport(t.getMailAddresses(), t.getSid()))
																	.collect(Collectors.toList());
		
		// set AvailableMailAddressImport
		List<AvailableMailAddressImport> mailAddresses = addressNotificationExport.getMailAddresses()
					.stream()
					.map(t -> new AvailableMailAddressImport(t.getSid(), t.getOptCompanyMailAddress(), t.getOptCompanyMobileMailAddress(), t.getOptPersonalMailAddress(), t.getOptPersonalMobileMailAddress()))
					.collect(Collectors.toList());
		
		// set MailDestinationFunctionManageImport
		Optional<MailDestinationFunctionManageImport> mailDestinationFunctionManage;
		if (addressNotificationExport.getMailDestinationFunctionManage().isPresent()) {
			MailDestinationFunctionManageExport export = addressNotificationExport.getMailDestinationFunctionManage().get();
			mailDestinationFunctionManage = Optional.of(new MailDestinationFunctionManageImport(export.getFunctionId(), export.getUseCompanyMailAddress(), 
					export.getUseCompanyMobileMailAddress(), export.getUsePersonalMailAddress(), export.getUsePersonalMobileMailAddress()));
		} else {
			mailDestinationFunctionManage = Optional.empty();
		}
		
		// set MailAddressNotificationImport
		MailAddressNotificationImport mailAddressNotification = new MailAddressNotificationImport(mailAddresses, mailDestinationFunctionManage);
		
		return new MailDestiImport(mailAddressNotification, sentMailLists);
	}

}
