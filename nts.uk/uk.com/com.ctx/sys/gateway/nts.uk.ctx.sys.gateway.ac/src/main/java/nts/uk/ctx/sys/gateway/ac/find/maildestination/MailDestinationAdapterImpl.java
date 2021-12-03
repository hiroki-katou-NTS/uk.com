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
import nts.uk.ctx.sys.env.pub.mailnoticeset.maildestination.AvailableMailAddressExport;
import nts.uk.ctx.sys.env.pub.mailnoticeset.maildestination.MailAddressNotificationExport;
import nts.uk.ctx.sys.env.pub.mailnoticeset.maildestination.MailDestinationExport;
import nts.uk.ctx.sys.env.pub.mailnoticeset.maildestination.MailDestinationFunctionManageExport;
import nts.uk.ctx.sys.env.pub.mailnoticeset.maildestination.MailDestinationFunctionManagePub;
import nts.uk.ctx.sys.gateway.dom.loginold.adapter.MailDestinationAdapter;
import nts.uk.ctx.sys.gateway.dom.loginold.dto.AvailableMailAddressImport;
import nts.uk.ctx.sys.gateway.dom.loginold.dto.MailDestiImport;
import nts.uk.ctx.sys.gateway.dom.loginold.dto.MailDestinationFunctionManageImport;
import nts.uk.ctx.sys.gateway.dom.loginold.dto.MailDestinationImport;

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
		
		// set MailDestinationFunctionManageImport
		Optional<MailDestinationFunctionManageImport> mailDestinationFunctionManage;
		if (addressNotificationExport.getMailDestinationFunctionManage().isPresent()) {
			MailDestinationFunctionManageExport export = addressNotificationExport.getMailDestinationFunctionManage().get();
			mailDestinationFunctionManage = Optional.of(new MailDestinationFunctionManageImport(export.getFunctionId(), export.getUseCompanyMailAddress(), 
					export.getUseCompanyMobileMailAddress(), export.getUsePersonalMailAddress(), export.getUsePersonalMobileMailAddress()));
		} else {
			mailDestinationFunctionManage = Optional.empty();
		}
		
		// set AvailableMailAddressImport
		Optional<AvailableMailAddressImport> availableMailAddressImport;
		if (!addressNotificationExport.getMailAddresses().isEmpty()) {
			AvailableMailAddressExport addressExport = addressNotificationExport.getMailAddresses().get(0);
			String companyMailAddress = addressExport.getOptCompanyMailAddress().map(t -> t).orElse(null);
			String companyMobileMailAddress = addressExport.getOptCompanyMobileMailAddress().map(t -> t).orElse(null);
			String personalMailAddress = addressExport.getOptPersonalMailAddress().map(t -> t).orElse(null);
			String personalMobileMailAddress = addressExport.getOptPersonalMobileMailAddress().map(t -> t).orElse(null);
			availableMailAddressImport = Optional.of(new AvailableMailAddressImport(
					companyMailAddress,
					companyMobileMailAddress,
					personalMailAddress,
					personalMobileMailAddress));
		} else {
			availableMailAddressImport = Optional.empty();
		}
		
		return new MailDestiImport(availableMailAddressImport, mailDestinationFunctionManage);
	}

}
