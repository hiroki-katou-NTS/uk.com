package uk.cnv.client.infra.repository.base;

import uk.cnv.client.UkConvertProperty;

public class UkRepositoryBase extends RepositoryBase {
	public UkRepositoryBase() {
		super("com.microsoft.sqlserver.jdbc.SQLServerDriver", UkConvertProperty.UK_DBCONN_STR);
	}
}
