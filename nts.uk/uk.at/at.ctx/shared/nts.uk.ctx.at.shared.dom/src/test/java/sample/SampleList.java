package sample;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SampleList<T extends HasIntValue<T>> {

	private final List<T> list;
	
	public T sum() {
		T result = list.get(0);
		for (int i = 1; i < list.size(); i++) {
			result = result.newValueAdded(list.get(i).getIntValue());
		}

		return result;
	}
}
