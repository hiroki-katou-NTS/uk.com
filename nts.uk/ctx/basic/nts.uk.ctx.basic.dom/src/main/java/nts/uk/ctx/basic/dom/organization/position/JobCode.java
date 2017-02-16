package nts.uk.ctx.basic.dom.organization.position;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

	@StringMaxLength(10)
	public class JobCode extends StringPrimitiveValue<JobCode> {

		public JobCode(String rawValue) {
			super(rawValue);
			// TODO Auto-generated constructor stub
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public static boolean isExisted(String companyCode, JobCode jobCode) {
			// TODO Auto-generated method stub
			return false;
		}
}
