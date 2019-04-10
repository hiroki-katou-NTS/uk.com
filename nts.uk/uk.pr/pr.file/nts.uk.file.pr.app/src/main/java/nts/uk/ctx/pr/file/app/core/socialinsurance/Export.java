package nts.uk.ctx.pr.file.app.core.socialinsurance;

public  enum Export {

	HEALTHY(0),
	WELFARE_PENSION(1),
	SOCIAL_INSURANCE_OFFICE(2),
	CONTRIBUTION_RATE(3),
	SALARY_HEALTHY(4);

	public final int value;

	private Export(int value) {
		this.value = value;
	}
}
