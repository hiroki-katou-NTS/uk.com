package nts.uk.ctx.at.function.app.nrl.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.function.Function;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.app.nrl.exceptions.InvalidFieldDataException;

/**
 * Exchange struct.
 * 
 * @author manhnd
 */
public class ExchangeStruct {

	/**
	 * Data
	 */
	private List<Record> ds;
	
	/**
	 * Fields
	 */
	private List<Field> fields = new ArrayList<>();
	
	/**
	 * Integer index
	 */
	private Hashtable<String, Integer> integerIndex = new Hashtable<>();
	
	private ExchangeStruct() {}
	
	public static ExchangeStruct newInstance() {
		return new ExchangeStruct();
	}
	
	/**
	 * Add field.
	 * @param name name
	 * @param size size
	 * @return ExchangeStruct
	 */
	public ExchangeStruct addField(String name, int size) {
		addField(name, size, t -> true);
		return this;
	}
	
	/**
	 * Add field.
	 * @param name name
	 * @param size size
	 * @param validator validator
	 * @return ExchangeStruct
	 */
	public ExchangeStruct addField(String name, int size, Function<String, Boolean> validator) {
		integerIndex.put(name, fields.size());
		fields.add(new Field(name, size, validator));
		return this;
	}
	
	/**
	 * Parse append text.
	 * @param d text
	 */
	public void parseAppend(String d) {
		int i = 0;
		Record record = newRecord();
		for (Field f : fields) {
			String t = d.substring(i, i + f.getSize());
			if (!f.getValidator().apply(t)) 
				throw new InvalidFieldDataException();
			record.set(f.getName(), t);
			i += f.getSize();
		}
		addRecord(record);
	}
	
	/**
	 * Create new record.
	 * @return record
	 */
	public Record newRecord() {
		return new Record(fields.size());
	}
	
	/**
	 * Get record.
	 * @param i index
	 * @return record
	 */
	public Record getRecord(int i) {
		return ds.get(i);
	}
	
	/**
	 * Add record.
	 * @param record record
	 */
	public void addRecord(Record record) {
		if (ds == null) ds = new ArrayList<>();
		ds.add(record);
	}
	
	@Getter @Setter
	public class Field {
		private String name;
		private int size;
		private Function<String, Boolean> validator;
		public Field(String name, int size, Function<String, Boolean> validator) {
			this.name = name;
			this.size = size;
			this.validator = validator;
		}
	}
	
	public class Record {
		
		/**
		 * Data 
		 */
		private final List<String> data;
		
		public Record(int c) {
			data = Arrays.asList(new String[c]);
		}
		
		/**
		 * Set.
		 * @param f
		 * @param d
		 */
		public void set(String f, String d) {
			int i = integerIndex.get(f);
			Field field = fields.get(i);
			if (d.length() > field.getSize()) throw new RuntimeException("Data exceeds maximum length.");
			data.set(i, d);
		}
		
		/**
		 * Get.
		 * @param f
		 * @return text
		 */
		public String get(String f) {
			Integer i = integerIndex.get(f);
			if (i == null) throw new RuntimeException("Column doesn't exist.");
			return data.get(i);
		}
	}
}
