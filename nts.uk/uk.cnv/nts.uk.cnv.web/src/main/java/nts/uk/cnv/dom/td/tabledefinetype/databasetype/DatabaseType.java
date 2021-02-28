package nts.uk.cnv.dom.td.tabledefinetype.databasetype;

import nts.uk.cnv.dom.td.tabledefinetype.DatabaseSpec;

public enum DatabaseType {
	sqlserver(new SqlServerSpec()),
	postgres(new PostgresSpec());

	private final DatabaseSpec spec;

    private DatabaseType(DatabaseSpec spec) {
        this.spec = spec;
    }

    public DatabaseSpec spec() {
    	return spec;
    }

}
