declare module nts.uk.pr.view.kmf001.c {
    module viewmodel {
        import EnumertionModel = service.model.EnumerationModel;
        class ScreenModel {
            textEditorOption: KnockoutObservable<any>;
            manageDistinctList: KnockoutObservableArray<EnumertionModel>;
            selectedAnnualManage: KnockoutObservable<string>;
            enableAnnualVacation: KnockoutObservable<boolean>;
            selectedAddAttendanceDay: KnockoutObservable<string>;
            selectedMaxManageSemiVacation: KnockoutObservable<number>;
            maxDayReferenceList: KnockoutObservableArray<EnumertionModel>;
            selectedMaxNumberSemiVacation: KnockoutObservable<number>;
            maxNumberCompany: KnockoutObservable<number>;
            maxGrantDay: KnockoutObservable<number>;
            maxRemainingDay: KnockoutObservable<number>;
            numberYearRetain: KnockoutObservable<number>;
            enableMaxNumberCompany: KnockoutObservable<boolean>;
            permissionList: KnockoutObservableArray<EnumertionModel>;
            selectedPermission: KnockoutObservable<number>;
            preemptionPermitList: KnockoutObservableArray<EnumertionModel>;
            selectedPreemptionPermit: KnockoutObservable<number>;
            displayDivisionList: KnockoutObservableArray<EnumertionModel>;
            selectedNumberRemainingYearly: KnockoutObservable<number>;
            selectedNextAnunalVacation: KnockoutObservable<number>;
            selectedTimeManagement: KnockoutObservable<number>;
            vacationTimeUnitList: KnockoutObservableArray<EnumertionModel>;
            enableTimeUnit: KnockoutObservable<boolean>;
            selectedVacationTimeUnit: KnockoutObservable<number>;
            selectedManageUpperLimitDayVacation: KnockoutObservable<number>;
            enableManageUpperLimit: KnockoutObservable<boolean>;
            selectedMaxDayVacation: KnockoutObservable<number>;
            timeMaxNumberCompany: KnockoutObservable<number>;
            isEnoughTimeOneDay: KnockoutObservable<boolean>;
            constructor();
            startPage(): JQueryPromise<any>;
            private update();
            private openVacationTypeScreen();
            private loadSetting();
            private toJsObject();
            private initUI(res);
        }
    }
}
