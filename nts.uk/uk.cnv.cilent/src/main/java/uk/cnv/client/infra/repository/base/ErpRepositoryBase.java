package uk.cnv.client.infra.repository.base;

import uk.cnv.client.UkConvertProperty;

public abstract class ErpRepositoryBase extends RepositoryBase {
	public ErpRepositoryBase() {
		super("com.microsoft.sqlserver.jdbc.SQLServerDriver", UkConvertProperty.ERP_DBCONN_STR);
	}
}
