package nts.uk.ctx.pereg.infra.repository.mastercopy.helper;

import lombok.RequiredArgsConstructor;
import nts.arc.layer.infra.data.command.CommandProxy;
import nts.arc.layer.infra.data.jdbc.JdbcProxy;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.layer.infra.data.query.QueryProxy;
import nts.uk.ctx.pereg.infra.repository.mastercopy.CopyMethodOnConflict;

@RequiredArgsConstructor
public class CopyContext {

	public final JdbcProxy jdbc;
	
	public final CommandProxy command;
	
	public final QueryProxy query;
	
	public final ContractCode contractCode;
	
	public final CompanyId companyId;
	
	public final CopyMethodOnConflict copyMethodOnConflict;
	
	public NtsStatement jdbc(String sql) {
		return jdbc.query(sql);
	}

	@RequiredArgsConstructor
	public static class ContractCode {

		public final String source;
		
		public final String target;
		
		public static ContractCode same(String contractCode) {
			return new ContractCode(contractCode, contractCode);
		}
	}

	@RequiredArgsConstructor
	public static class CompanyId {

		public final String source;
		
		public final String target;
	}
}
