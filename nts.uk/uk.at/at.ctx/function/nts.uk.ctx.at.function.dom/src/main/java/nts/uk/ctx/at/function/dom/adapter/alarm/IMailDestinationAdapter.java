package nts.uk.ctx.at.function.dom.adapter.alarm;

public interface IMailDestinationAdapter {
	// get list mail by list sID
	MailDestinationAlarmImport getEmpEmailAddress(String cID, String sIDs, Integer functionID);
}
