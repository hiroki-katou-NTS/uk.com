package uk.cnv.client.infra.repository.base;

public abstract class ErpRepositoryBase extends RepositoryBase {
	public ErpRepositoryBase() {
		super("com.microsoft.sqlserver.jdbc.SQLServerDriver","ErpDbConnString");
	}
}
