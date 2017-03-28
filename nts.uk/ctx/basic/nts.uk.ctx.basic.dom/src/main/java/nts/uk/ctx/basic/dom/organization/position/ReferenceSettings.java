package nts.uk.ctx.basic.dom.organization.position;
	
	public enum ReferenceSettings {
		CanNotRefer(0),
		CanRefer(1);

		public final int value;

		private ReferenceSettings(int value) {
			this.value = value;
		}

	}
