package nts.sample.pktest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class PrimaryKeyPerformance extends JpaRepository {
	
	private static int count = 10;
	
	private static List<String> guids = null;
	private static List<Integer> intIds = null;
	private static List<String> vcharIds = null;
	
	public void prepare() {
		guids = getGuids();
		Collections.shuffle(guids);

		intIds = IntStream.range(0, 1000000).boxed().collect(Collectors.toList());
		Collections.shuffle(intIds);
		
		vcharIds = IntStream.range(0, 1000000).boxed().map(i -> i.toString()).collect(Collectors.toList());
		Collections.shuffle(vcharIds);
	}
	
	public static class Results {
		public int data15_guid;
		public int data15_int;
		public int data15_clust_guid;
		public int data15_clust_int;
		public int data15_2k_guid;
		public int data15_2k_int;
		public int data3_guid;
		public int data3_int;
		public int data3_2k_guid;
		public int data3_2k_int;
	}
	
	public Results bulk() {

		guid("PKTEST15_GUID3");
		integer("PKTEST15_INT3");
		
		Results results = new Results();
		List<Runnable> tasks = new ArrayList<>();
		tasks.add(() -> results.data15_guid = guid("PKTEST15_GUID"));
		tasks.add(() -> results.data15_int = integer("PKTEST15_INT"));
		tasks.add(() -> results.data15_clust_guid = guid("PKTEST15_GUID2"));
		tasks.add(() -> results.data15_clust_int = integer("PKTEST15_INT2"));
		tasks.add(() -> results.data15_2k_guid = guid2k("PKTEST15_2K_GUID"));
		tasks.add(() -> results.data15_2k_int = integer2k("PKTEST15_2K_INT"));
		tasks.add(() -> results.data3_guid = guid("PKTEST3_GUID"));
		tasks.add(() -> results.data3_int = integer("PKTEST3_INT"));
		tasks.add(() -> results.data3_2k_guid = guid2k("PKTEST3_2K_GUID"));
		tasks.add(() -> results.data3_2k_int = integer2k("PKTEST3_2K_INT"));
		
		Collections.shuffle(tasks);
		tasks.forEach(t -> t.run());
		
		return results;
	}
	
	public int guid2k(String tableName) {
		String sql = "select * from " + tableName + " a"
				+ " where a.PK1 = @id and a.PK2 = @id";
		return executeStringKey(sql, guids);
	}
	
	public int guid(String tableName) {
		String sql = "select * from " + tableName + " a"
				+ " where a.PK = @id";
		return executeStringKey(sql, guids);
	}
	
	public int integer2k(String tableName) {
		String sql = "select * from " + tableName + " a"
				+ " where a.PK1 = @id and a.PK2 = @id";
		return executeIntKey(sql, intIds);
	}
	
	public int integer(String tableName) {
		String sql = "select * from " + tableName + " a"
				+ " where a.PK = @id";
		return executeIntKey(sql, intIds);
	}
	
	public void test() {
		
		String sql = "select * from hoge where id1 = @id2 and id2 = @id2";
		List<String> name = this.jdbcProxy().query(sql)
				.paramString("id1", "aa")
				.paramInt("id2", 3)
				.getList(rec -> {
					return rec.getString("name");
				});
		
	}

	private int executeStringKey(String sql, List<String> keys) {

		return execute(sql, keys, key -> this.jdbcProxy().query(sql).paramString("id", key).getSingle(rec -> 1));
		
	}

	private int executeIntKey(String sql, List<Integer> keys) {

		return execute(sql, keys, key -> this.jdbcProxy().query(sql).paramInt("id", key).getSingle(rec -> 1));
	}
	
	private <T> int execute(String sql, List<T> keys, Consumer<T> process) {
		long elapsedNano = 0;
		
		
		for (int i = 0; i < count; i++) {
			T key = keys.get(i);
			
			long start = System.nanoTime();
			process.accept(key);
			elapsedNano += System.nanoTime() - start;
		}
		
		return (int)(elapsedNano / 1000 / 1000);
	}

	private List<String> getGuids() {
		String sql = "select PK from PKTEST_GUID";
		List<String> keys = this.jdbcProxy().query(sql).getList(rec -> rec.getString("PK"));
		return keys;
	}

}
