package nts.uk.ctx.at.shared.dom.specialholiday.grantday;


	import nts.arc.primitive.IntegerPrimitiveValue;
	import nts.arc.primitive.PrimitiveValue;
	import nts.arc.primitive.constraint.IntegerMaxValue;

	@IntegerMaxValue(150)
	public class GrantAge extends IntegerPrimitiveValue<PrimitiveValue<Integer>> {
		/** serialVersionUID */
		private static final long serialVersionUID = 1L;

		public GrantAge(int rawValue) {
			super(rawValue);

		}
}
