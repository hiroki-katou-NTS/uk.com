package uk.cnv.client.infra.repository.base;

public class UkCnvRepositoryBase extends RepositoryBase {
	public UkCnvRepositoryBase() {
		super("com.microsoft.sqlserver.jdbc.SQLServerDriver", "UkCnvDbConnString");
	}
}
