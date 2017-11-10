package nts.uk.ctx.bs.employee.app.command.category;

public enum CategoryTypeCode {
	CS00003("CS00003"),
	CS00004("CS00004"),
	CS00005("CS00005"),
	CS00006("CS00006"),
	CS00007("CS00007"),
	CS00008("CS00008"),
	CS00009("CS00009"),
	CS00010("CS00010"),
	CS00011("CS00011"),
	CS00012("CS00012"),
	CS00013("CS00013"),
	CS00014("CS00014"),
	CS00015("CS00015");
	
	private final String categoryCode;

    private CategoryTypeCode(final String categoryCode) {
        this.categoryCode =categoryCode;
    }

    @Override
    public String toString() {
        return categoryCode;
    }
}
