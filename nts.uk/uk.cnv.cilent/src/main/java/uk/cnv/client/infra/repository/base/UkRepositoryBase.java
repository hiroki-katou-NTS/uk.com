package uk.cnv.client.infra.repository.base;

public abstract class UkRepositoryBase extends RepositoryBase {
	public UkRepositoryBase() {
		super("com.microsoft.sqlserver.jdbc.SQLServerDriver", "UkDbConnString");
	}
}
