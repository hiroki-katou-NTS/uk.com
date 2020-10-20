package nts.uk.ctx.sys.assist.dom.storage;

import java.util.Map;
import java.util.TreeMap;

import javax.ejb.Stateless;

import lombok.Getter;

@Stateless
@Getter
public class ObservableService {
	private Map<String, DataObservable> pool = new TreeMap<>();
	
	public void addObservable(String contractCode, DataObservable observable) {
		this.pool.put(contractCode, observable);
	}
	
	public DataObservable getObservable(String contractCode) {
		return pool.get(contractCode);
	}
	
	public void removeObservable(String contractCode) {
		this.pool.remove(contractCode);
	}
}
