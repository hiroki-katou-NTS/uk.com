package nts.uk.cnv.dom.databasetype;

public enum DatabaseType {
	sqlserver(new SqlServerSpec()),
	postgres(new PostgresSpec());
	
	private final DataBaseSpec spec;

    private DatabaseType(DataBaseSpec spec) {
        this.spec = spec;
    }
    
    public DataBaseSpec spec() {
    	return spec;
    }
    
}
