package sample;

public interface HasIntValue<X> {

	int getIntValue();
	
	X newValueAdded(int valueToAdd);
}
