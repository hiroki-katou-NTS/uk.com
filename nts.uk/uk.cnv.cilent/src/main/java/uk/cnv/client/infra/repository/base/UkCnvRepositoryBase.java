package uk.cnv.client.infra.repository.base;

import uk.cnv.client.UkConvertProperty;

public class UkCnvRepositoryBase extends RepositoryBase {
	public UkCnvRepositoryBase() {
		super("com.microsoft.sqlserver.jdbc.SQLServerDriver", UkConvertProperty.UKCNV_DBCONN_STR);
	}
}
