package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

public enum SocialInsuColleMonth {
	BEFORE_MONTH(0,"Enum_SocialInsuColleMonth_BEFORE_MONTH"),
	LAST_MONTH(1,"Enum_SocialInsuColleMonth_LAST_MONTH"),
	MONTH(2,"Enum_SocialInsuColleMonth_MONTH"),
	NEXT_MONTH(3,"Enum_SocialInsuColleMonth_NEXT_MONTH"),
	SECOND_FOLLOWING_MONTH(4,"Enum_SocialInsuColleMonth_SECOND_FOLLOWING_MONTH");
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private SocialInsuColleMonth(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
