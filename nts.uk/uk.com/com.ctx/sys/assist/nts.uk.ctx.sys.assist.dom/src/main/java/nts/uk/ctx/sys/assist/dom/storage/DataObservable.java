package nts.uk.ctx.sys.assist.dom.storage;

import java.util.Observable;

import lombok.Getter;

@Getter
public class DataObservable extends Observable {
	private String dataStorageProcessId;
	
	public void setDataStorageProcessId(String dataStorageProcessId) {
		this.dataStorageProcessId = dataStorageProcessId;
		setChanged();
		notifyObservers(dataStorageProcessId);
	}
}
