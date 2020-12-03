package nts.uk.ctx.pereg.infra.repository.mastercopy.helper;

import lombok.RequiredArgsConstructor;
import nts.arc.layer.infra.data.command.CommandProxy;
import nts.arc.layer.infra.data.jdbc.JdbcProxy;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.layer.infra.data.query.QueryProxy;
import nts.uk.ctx.pereg.infra.repository.mastercopy.CopyMethodOnConflict;
import nts.uk.shr.com.context.AppContexts;

@RequiredArgsConstructor
public class CopyContext {

	public final JdbcProxy jdbc;
	
	public final CommandProxy command;
	
	public final QueryProxy query;
	
	public final String contractCode = AppContexts.user().contractCode();
	
	public final CompanyId companyId = new CompanyId();
	
	public final CopyMethodOnConflict copyMethodOnConflict;
	
	public NtsStatement jdbc(String sql) {
		return jdbc.query(sql);
	}
	
	public static class CompanyId {

		public final String source = AppContexts.user().zeroCompanyIdInContract();
		
		public final String target = AppContexts.user().companyId();
	}
}
