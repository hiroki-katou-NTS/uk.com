///******************************************************************
// * Copyright (c) 2015 Nittsu System to present.                   *
// * All right reserved.                                            *
// *****************************************************************/
//package nts.uk.ctx.pr.core.infra.repository.support;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//import org.dbunit.database.DatabaseConfig;
//import org.dbunit.database.DatabaseConnection;
//import org.dbunit.database.DatabaseDataSet;
//import org.dbunit.database.DatabaseSequenceFilter;
//import org.dbunit.database.IDatabaseConnection;
//import org.dbunit.dataset.FilteredDataSet;
//import org.dbunit.dataset.IDataSet;
//import org.dbunit.dataset.csv.CsvDataSet;
//import org.dbunit.dataset.csv.CsvDataSetWriter;
//import org.dbunit.dataset.filter.ExcludeTableFilter;
//import org.dbunit.dataset.filter.IncludeTableFilter;
//import org.dbunit.ext.mssql.MsSqlDataTypeFactory;
//import org.dbunit.operation.DatabaseOperation;
//
//import lombok.Data;
//
///**
// * The Class DbTools.
// */
//public class DbTools {
//	
//	public static final Scanner SC = new Scanner(System.in);
//	
//	/**
//	 * The main method.
//	 *
//	 * @param args the arguments
//	 */
//	public static void main(String[] args) throws Exception {
//		// Get db.
//		DbInfo db = getDbInfo();
//		
//		// Read db information.
//		db.input();
//		
//		// Db connection.
//		Class.forName(db.getDriver());
//		Connection jdbcConnection = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
//		IDatabaseConnection dbUnitConn = new DatabaseConnection(jdbcConnection);
//		DatabaseConfig dbCfg = dbUnitConn.getConfig();
//		dbCfg.setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
//		db.setUp(dbCfg);
//
//		// Operation.
//		System.out.println();
//		System.out.println("Please select operation type");
//		System.out.println(String.format("\t %d: %s", 0, "Export"));
//		System.out.println(String.format("\t %d: %s", 1, "Import"));
//		System.out.print("Select: ");
//		int operation = SC.nextInt();
//		SC.nextLine();
//		
//		// Excluded / include filter.
//		System.out.println();
//		System.out.print("Please execlude table or leave blank (ex: hmh*, hop*): ");
//		String exclude = SC.nextLine();
//		
//		System.out.print("Please include table or leave blank (ex: hcm*): ");
//		String include = SC.nextLine();
//		
//		IncludeTableFilter tbInclude = new IncludeTableFilter();
//		ExcludeTableFilter tbExclude = null;
//		
//		if (exclude != null && exclude.length() != 0) {
//			tbExclude = new ExcludeTableFilter();
//			String[] ex = exclude.split(",");
//			for (String e : ex) {
//				tbExclude.excludeTable(e);
//			}
//		}
//		
//		if (include != null && include.length() != 0) {
//			String[] ix = include.split(",");
//			for (String i : ix) {
//				tbInclude.includeTable(i);
//			}
//		} else {
//			tbInclude.includeTable("h*");
//		}
//
//		Operation op = null;
//		switch (operation) {
//		case 0:
//			op = new ExportOperation(dbUnitConn, tbInclude, tbExclude);
//			break;
//		default:
//			op = new ImportOperation(dbUnitConn, tbInclude, tbExclude);
//			break;
//		}
//		op.input();
//		
//		System.out.println("Please wait........");
//		op.execute();
//		System.out.print("Done!");
//	}
//	
//	/**
//	 * Gets the db info.
//	 *
//	 * @return the db info
//	 */
//	public static DbInfo getDbInfo() {
//		List<DbInfo> dbList = new ArrayList<>();
//		dbList.add(new MssqlDbInfo());
//		dbList.add(new PgsqlDbInfo());
//		dbList.add(new OracleDbInfo());
//		
//		System.out.println();
//		System.out.println("Please select db type");
//		int index = 0;
//		for (DbInfo dbInfo : dbList) {
//			System.out.println(String.format("\t%d: %s", index, dbInfo.getTypeName()));
//			index++;
//		}
//		System.out.print("Select: ");
//		index = SC.nextInt();
//		SC.nextLine();
//		DbInfo db = dbList.get(index);
//		return db;
//	}
//}
//
///**
// * The Class Operation.
// */
//abstract class Operation {
//	protected IDatabaseConnection connection;
//	protected IncludeTableFilter include;
//	protected ExcludeTableFilter exclude;
//
//	public Operation(IDatabaseConnection connection, IncludeTableFilter include, ExcludeTableFilter exclude) {
//		super();
//		this.connection = connection;
//		this.include = include;
//		this.exclude = exclude;
//	}
//	
//	abstract void input();
//	
//	abstract void execute() throws Exception;
//}
//
//class ExportOperation extends Operation {
//
//	private String exportFolder;
//	
//	public ExportOperation(IDatabaseConnection connection, IncludeTableFilter include, ExcludeTableFilter exclude) {
//		super(connection, include, exclude);
//		// TODO Auto-generated constructor stub
//	}
//
//	@Override
//	void input() {
//		System.out.println();
//		System.out.print("Please input csv export folder: ");
//		this.exportFolder = DbTools.SC.nextLine();
//	}
//
//	@Override
//	void execute() throws Exception {
//		CsvDataSetWriter wt = new CsvDataSetWriter(this.exportFolder);
//		IDataSet ds = new DatabaseDataSet(this.connection, false, this.include);
//
//		if (this.exclude != null) {
//			ds = new FilteredDataSet(this.exclude, ds);
//		}
//
//		wt.write(ds);
//		
//		// Delete table-ordering.txt
//		if (!this.exportFolder.endsWith(File.separator))
//			this.exportFolder = this.exportFolder +  File.separator;
//		new File(this.exportFolder + CsvDataSet.TABLE_ORDERING_FILE).deleteOnExit();
//	}
//	
//}
//
//class ImportOperation extends Operation {
//
//	private String importFolder;
//	
//	public ImportOperation(IDatabaseConnection connection, IncludeTableFilter include, ExcludeTableFilter exclude) {
//		super(connection, include, exclude);
//	}
//
//	@Override
//	void input() {
//		System.out.println();
//		System.out.print("Please input csv import folder: ");
//		this.importFolder = DbTools.SC.nextLine();
//	}
//
//	@Override
//	void execute() throws Exception {		
//		// Create table-ordering.txt
//		String correctPath = this.importFolder;
//		if (!this.importFolder.endsWith(File.separator))
//			correctPath = this.importFolder +  File.separator;
//		File tblOrdering = new File(correctPath + CsvDataSet.TABLE_ORDERING_FILE);
//		tblOrdering.deleteOnExit();
//		tblOrdering.createNewFile();
//
//		// export all file name.
//		String[] tblsName = new FilteredDataSet(new DatabaseSequenceFilter(connection),
//				new DatabaseDataSet(this.connection, false, this.include)).getTableNames();
//		try (FileWriter bos = new FileWriter(tblOrdering)) {
//			for (String tbl : tblsName) {
//				File f = new File(correctPath + tbl + ".csv");
//				if (f.exists()) {
//					bos.write(tbl.toUpperCase());
//					bos.write("\r\n");
//				}
//			}
//		}
//
//		// LOAD CSV
//		IDataSet ds = new CsvDataSet(new File(this.importFolder));
//
//		if (this.include != null) {
//			ds = new FilteredDataSet(this.include, ds);
//		}
//		
//		if (this.exclude != null) {
//			ds = new FilteredDataSet(this.exclude, ds);
//		}
//
//		DatabaseOperation.CLEAN_INSERT.execute(this.connection,
//				new FilteredDataSet(new DatabaseSequenceFilter(connection), ds));
//		tblOrdering.deleteOnExit();
//	}
//	
//}
//
//
//@Data
//abstract class DbInfo {
//	private String typeName;
//	private String username;
//	private String password;
//	private String url;
//	private String driver;
//	public DbInfo(String typeName, String driver) {
//		this.typeName = typeName;
//		this.driver = driver;
//	}
//	
//	public void input() {
//		System.out.println("Please input db information");
//		System.out.print("\tConnection url: ");
//		this.url = DbTools.SC.nextLine();
//		
//		System.out.print("\tUser name: ");
//		this.username = DbTools.SC.nextLine();
//		
//		System.out.print("\tPassword: ");
//		this.password = DbTools.SC.nextLine();
//		
//		return;
//	}
//	
//	public abstract void setUp(DatabaseConfig dbConfig);
//}
//
//class MssqlDbInfo extends DbInfo {
//
//	/**
//	 * Instantiates a new mssql db info.
//	 */
//	public MssqlDbInfo() {
//		super("MS SQL", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
//	}
//
//	@Override
//	public void setUp(DatabaseConfig dbConfig) {
//		dbConfig.setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, Boolean.FALSE);
//		dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MsSqlDataTypeFactory());
//	}
//}
//
//class PgsqlDbInfo extends DbInfo {
//
//	public PgsqlDbInfo() {
//		super("PG SQL", "org.postgresql.Driver");
//	}
//
//	@Override
//	public void setUp(DatabaseConfig dbConfig) {
//		dbConfig.setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, Boolean.FALSE);
//	}
//}
//
//class OracleDbInfo extends DbInfo {
//
//	public OracleDbInfo() {
//		super("Oracle", "oracle.jdbc.driver.OracleDriver");
//	}
//
//	@Override
//	public void setUp(DatabaseConfig dbConfig) {
//		dbConfig.setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);
//	}
//}