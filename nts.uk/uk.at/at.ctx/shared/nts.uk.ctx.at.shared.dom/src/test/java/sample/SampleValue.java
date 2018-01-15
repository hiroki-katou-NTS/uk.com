package sample;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SampleValue implements HasIntValue<SampleValue> {
	
	private final int value;

	@Override
	public int getIntValue() {
		return value;
	}

	@Override
	public SampleValue newValueAdded(int valueToAdd) {
		return new SampleValue(this.value + valueToAdd);
	}

}
