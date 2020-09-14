package nts.uk.shr.com.license.option;

public class OptionLicense {

	public static CustomizeOptions customize() {
		return new CustomizeOptions() {
			@Override
			public boolean ootsuka() {
				return false;
			}
		};
	}
	
	public static SubsystemOptions subsystem() {
		return new SubsystemOptions() {
			@Override
			public boolean payroll() {
				return true;
			}
			@Override
			public boolean mobile() {
				return true;
			}
			@Override
			public boolean humanResource() {
				return true;
			}
			@Override
			public boolean healthLife() {
				return true;
			}
			@Override
			public boolean attendance() {
				return true;
			}
		};
	}
	
	public static ExtendedOperationOptions extendedOperation() {
		return new ExtendedOperationOptions() {
			@Override
			public boolean smile() {
				return true;
			}
			@Override
			public boolean multipleCompanies() {
				return true;
			}
			@Override
			public boolean externalIO() {
				return true;
			}
		};
	}
	
	public static AttendanceOptions attendance() {
		return new AttendanceOptions() {
			@Override
			public boolean workload() {
				return true;
			}
			@Override
			public boolean reservation() {
				return true;
			}
			@Override
			public boolean multipleWork() {
				return true;
			}
			@Override
			public boolean hourlyPaidLeave() {
				return true;
			}
			@Override
			public boolean application() {
				return true;
			}
			@Override
			public boolean anyPeriodAggregation() {
				return true;
			}
			@Override
			public boolean alarmList() {
				return true;
			}
			@Override
			public WebTimeStamp webTimeStamp() {
				return new WebTimeStamp() {
					@Override
					public boolean isEnabled() {
						return true;
					}
					@Override
					public boolean fingerNEC() {
						return true;
					}
				};
			}
			@Override
			public Schedule schedule() {
				return new Schedule() {
					@Override
					public boolean isEnabled() {
						return true;
					}
					@Override
					public boolean nursing() {
						return true;
					}
					@Override
					public boolean medical() {
						return true;
					}
				};
			}
		};
	}
}
