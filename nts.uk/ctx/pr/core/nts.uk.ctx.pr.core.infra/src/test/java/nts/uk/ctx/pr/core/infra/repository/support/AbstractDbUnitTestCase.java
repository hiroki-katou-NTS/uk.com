///******************************************************************
// * Copyright (c) 2015 Nittsu System to present.                   *
// * All right reserved.                                            *
// *****************************************************************/
//package nts.uk.ctx.pr.core.infra.repository.support;
//
//import java.lang.reflect.Field;
//import java.sql.Connection;
//import java.sql.DatabaseMetaData;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import javax.ejb.EJB;
//import javax.inject.Inject;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//
//import org.dbunit.DBTestCase;
//import org.dbunit.database.DatabaseConfig;
//import org.dbunit.database.DatabaseConnection;
//import org.dbunit.database.DatabaseSequenceFilter;
//import org.dbunit.database.IDatabaseConnection;
//import org.dbunit.dataset.AbstractDataSet;
//import org.dbunit.dataset.DataSetException;
//import org.dbunit.dataset.FilteredDataSet;
//import org.dbunit.dataset.IDataSet;
//import org.dbunit.dataset.excel.XlsDataSet;
//import org.dbunit.dataset.xml.XmlDataSet;
//import org.dbunit.ext.mssql.MsSqlDataTypeFactory;
//import org.dbunit.operation.DatabaseOperation;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.runner.RunWith;
//
//import mockit.Deencapsulation;
//import mockit.Injectable;
//import mockit.StrictExpectations;
//import mockit.Tested;
//import mockit.integration.junit4.JMockit;
//import nts.uk.shr.infra.data.DefaultEntityManagerLoader;
//
///**
// * The Class AbstractDbUnitTestCase.
// */
//@RunWith(JMockit.class)
//public abstract class AbstractDbUnitTestCase extends DBTestCase {
//
//	/** The data connection. */
//	@Injectable
//	private DefaultEntityManagerLoader dataConnection;
//
//	/* MOCK CONNECT */
//	/** The Constant entityManagerFactory. */
//	private static final EntityManagerFactory entityManagerFactory = Persistence
//			.createEntityManagerFactory("UK");
//
//	/** The entity manager map. */
//	ThreadLocal<EntityManager> entityManagerMap = new InheritableThreadLocal<>();
//
//	/**
//	 * Gets the jpa connection.
//	 *
//	 * @return the jpa connection
//	 */
//	protected IDatabaseConnection getJpaConnection() {
//		try {
//			EntityManager em = entityManagerMap.get();
//
//			if (em == null) {
//				em = entityManagerFactory.createEntityManager();
//				entityManagerMap.set(em);
//			}
//
//			beginJpaConnection();
//			Connection jdbc = em.unwrap(java.sql.Connection.class);
//			IDatabaseConnection conDbUnit = new DatabaseConnection(jdbc);
//			DatabaseConfig dbCfg = conDbUnit.getConfig();
//
//			// Set feature base on driver.
//			DatabaseMetaData dbMetadata = jdbc.getMetaData();
//			// MS SQL
//			if (dbMetadata.getDatabaseProductName().toLowerCase()
//					.contains("Microsoft SQL Server".toLowerCase())) {
//				dbCfg.setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, Boolean.TRUE);
//				dbCfg.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
//						new MsSqlDataTypeFactory());
//			}
//
//			// POSTGRES
//			if (dbMetadata.getDatabaseProductName().toLowerCase()
//					.contains("postgres".toLowerCase())) {
//				dbCfg.setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, Boolean.TRUE);
//			}
//
//			// Oracle.
//
//			// Other.
//			dbCfg.setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
//			return conDbUnit;
//		} catch (Exception ex) {
//			throw new RuntimeException(ex);
//		}
//	}
//
//	/**
//	 * Execute test.
//	 *
//	 * @param <T>
//	 *            the generic type
//	 * @param supplier
//	 *            the supplier
//	 * @return the t
//	 */
//	protected <T> T executeTest(java.util.function.Supplier<? extends T> supplier) {
//		try {
//			return supplier.get();
//		} finally {
//		}
//	}
//
//	/**
//	 * Execute test with transaction.
//	 *
//	 * @param <T>
//	 *            the generic type
//	 * @param supplier
//	 *            the supplier
//	 * @return the t
//	 */
//	protected <T> T executeTestWithTransaction(java.util.function.Supplier<? extends T> supplier) {
//		try {
//			this.beginJpaConnection();
//			return supplier.get();
//		} finally {
//			this.closeCurrentJpaConnection();
//		}
//	}
//
//	/**
//	 * Gets the entity manager.
//	 *
//	 * @return the entity manager
//	 */
//	protected EntityManager getEntityManager() {
//		// Get em.
//		EntityManager em = entityManagerMap.get();
//
//		if (em == null) {
//			em = entityManagerFactory.createEntityManager();
//			entityManagerMap.set(em);
//		}
//
//		return em;
//	}
//
//	/**
//	 * Execute db unit operation.
//	 *
//	 * @param operation
//	 *            the operation
//	 * @param dataset
//	 *            the dataset
//	 */
//	protected void executeDbUnitOperation(DatabaseOperation operation, AbstractDataSet dataset) {
//		try {
//			IDatabaseConnection connection = getJpaConnection();
//			AbstractDataSet fullDs = new FilteredDataSet(new DatabaseSequenceFilter(connection),
//					dataset);
//			operation.execute(connection, fullDs);
//			this.closeCurrentJpaConnection();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			throw new RuntimeException(ex);
//		}
//	}
//
//	/**
//	 * Execute mock entity manager.
//	 */
//	private void executeMockEntityManager() {
//		// Get em.
//		EntityManager em = entityManagerMap.get();
//
//		if (em == null) {
//			em = entityManagerFactory.createEntityManager();
//			entityManagerMap.set(em);
//		}
//
//		// Expectation.
//		new StrictExpectations() {
//			{
//				// Get entity manager.
//				dataConnection.getEntityManager();
//				this.result = entityManagerMap.get();
//			}
//		};
//	}
//
//	/**
//	 * Begin jpa connection.
//	 */
//	private void beginJpaConnection() {
//		EntityManager em = entityManagerMap.get();
//
//		if (em == null) {
//			em = entityManagerFactory.createEntityManager();
//			entityManagerMap.set(em);
//		}
//
//		if (!em.getTransaction().isActive()) {
//			em.getTransaction().begin();
//		}
//	}
//
//	/**
//	 * Close current jpa connection.
//	 */
//	private void closeCurrentJpaConnection() {
//		EntityManager em = entityManagerMap.get();
//		if (em != null) {
//			if (em.getTransaction().getRollbackOnly()) {
//				em.getTransaction().rollback();
//			} else {
//				em.flush();
//				em.getTransaction().commit();
//			}
//		}
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.dbunit.DatabaseTestCase#getSetUpOperation()
//	 */
//	@Override
//	protected org.dbunit.operation.DatabaseOperation getSetUpOperation() throws Exception {
//		return DatabaseOperation.NONE;
//	};
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.dbunit.DatabaseTestCase#getTearDownOperation()
//	 */
//	@Override
//	protected DatabaseOperation getTearDownOperation() throws Exception {
//		return DatabaseOperation.NONE;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.dbunit.DatabaseTestCase#getDataSet()
//	 */
//	@Override
//	protected IDataSet getDataSet() throws Exception {
//		// No need to do any thing.
//		return null;
//	}
//
//	/**
//	 * Gets the data set.
//	 *
//	 * @param fileName
//	 *            the file name
//	 * @return the data set
//	 * @throws DataSetException
//	 *             the data set exception
//	 * @throws Exception
//	 *             the exception
//	 */
//	protected XlsDataSet getDataSet(String fileName) throws DataSetException, Exception {
//		String callerClass = new Exception().getStackTrace()[1].getClassName();
//		String resourcePath = callerClass.replace('.', '/');
//		return new XlsDataSet(
//				this.getClass().getResourceAsStream('/' + resourcePath + "_" + fileName));
//	}
//
//	/**
//	 * Gets the xml data set.
//	 *
//	 * @param fileName
//	 *            the file name
//	 * @return the xml data set
//	 * @throws DataSetException
//	 *             the data set exception
//	 * @throws Exception
//	 *             the exception
//	 */
//	protected XmlDataSet getXmlDataSet(String fileName) throws DataSetException, Exception {
//		String callerClass = new Exception().getStackTrace()[1].getClassName();
//		String resourcePath = callerClass.replace('.', '/');
//		return new XmlDataSet(
//				this.getClass().getResourceAsStream('/' + resourcePath + "_" + fileName));
//	}
//
//	/**
//	 * Generic invok method.
//	 *
//	 * @param obj
//	 *            the obj
//	 * @param methodName
//	 *            the method name
//	 * @param paramCount
//	 *            the param count
//	 * @param params
//	 *            the params
//	 * @return the object
//	 */
//	public Object genericInvokMethod(Object obj, String methodName, int paramCount,
//			Object... params) {
//		Object requiredObj = null;
//		Object[] parameters = new Object[paramCount];
//		Class<?>[] classArray = new Class<?>[paramCount];
//		for (int i = 0; i < paramCount; i++) {
//			parameters[i] = params[i];
//			classArray[i] = params[i].getClass();
//		}
//
//		requiredObj = Deencapsulation.invoke(obj, methodName, params);
//
//		return requiredObj;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.dbunit.DatabaseTestCase#setUp()
//	 */
//	@Before
//	public void setUp() {
//		this.executeMockEntityManager();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.dbunit.DatabaseTestCase#tearDown()
//	 */
//	@After
//	public void tearDown() throws Exception {
//		EntityManager em = entityManagerMap.get();
//
//		if (em != null && em.getTransaction().isActive()) {
//			em.getTransaction().commit();
//		}
//
//		if (em != null && em.isOpen()) {
//			em.clear();
//			em.close();
//		}
//	}
//
//	/**
//	 * Fully inject.
//	 *
//	 * @param object
//	 *            the object
//	 */
//	protected void fullyInject(Object object) {
//		Class<?> clazz = object.getClass();
//
//		List<Object> allValues = getAllField(clazz).stream().map(f -> {
//			return Deencapsulation.getField(object, f.getName());
//		}).collect(Collectors.toList());
//
//		inject(object, clazz, allValues);
//	}
//
//	/**
//	 * Inject.
//	 *
//	 * @param object
//	 *            the object
//	 * @param clazz
//	 *            the clazz
//	 * @param allValues
//	 *            the all values
//	 */
//	private void inject(Object object, Class<?> clazz, List<Object> allValues) {
//		List<Field> needInjectField = Arrays.asList(clazz.getDeclaredFields()).stream()
//				.filter(field -> {
//					Tested t = field.getAnnotation(Tested.class);
//					return t == null;
//				}).collect(Collectors.toList());
//
//		for (Field field : needInjectField) {
//			// Get field value.
//			Object fieldVal = Deencapsulation.getField(object, field.getName());
//
//			List<Field> fieldSubFields = getAllField(field.getType());
//			for (Field subField : fieldSubFields) {
//				if (subField.getAnnotation(Inject.class) == null
//						&& subField.getAnnotation(EJB.class) == null)
//					continue;
//
//				Object value = allValues.stream().filter(val -> {
//					return subField.getType().isInstance(val);
//				}).findFirst().orElse(null);
//
//				if (value == null) {
//					try {
//						if (subField.getType().isInterface()) {
//							value = subField.getType().getDeclaredClasses()[0].newInstance();
//						} else {
//							value = subField.getType().newInstance();
//						}
//					} catch (Exception ex) {
//						throw new RuntimeException("Can not find: " + field.getType().getName()
//								+ " " + subField.getType().getName());
//					}
//				}
//				Deencapsulation.setField(fieldVal, subField.getName(), value);
//			}
//		}
//	}
//
//	/**
//	 * Inject.
//	 *
//	 * @param object
//	 *            the object
//	 * @param allValues
//	 *            the all values
//	 * @param field
//	 *            the field
//	 */
//	private void inject(Object object, List<Object> allValues, Field field) {
//
//	}
//
//	/**
//	 * Gets the all field.
//	 *
//	 * @param clazz
//	 *            the clazz
//	 * @return the all field
//	 */
//	protected List<Field> getAllField(Class clazz) {
//		List<Field> field = new ArrayList<>();
//		Class tmpClass = clazz;
//		while (tmpClass != null) {
//			field.addAll(Arrays.asList(tmpClass.getDeclaredFields()));
//			tmpClass = tmpClass.getSuperclass();
//		}
//		return field;
//	}
//}