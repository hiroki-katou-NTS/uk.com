package nts.uk.ctx.sys.env.dom.mailnoticeset;

import java.util.List;

public interface MailFunctionRepository {

	public List<MailFunction> findAll(Boolean proprietySendMailSettingAtr);
}
