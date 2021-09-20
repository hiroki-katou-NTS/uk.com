/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.ksu003.d.test {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    @bean()
    class ViewModel extends ko.ViewModel {
        dateValue: KnockoutObservable<any>;
        startDateString: KnockoutObservable<string>;
        endDateString: KnockoutObservable<string>;

        orgUnit: KnockoutObservable<number> = ko.observable(0);
        orgId: KnockoutObservable<string> = ko.observable("");
        targetOrganizationName: KnockoutObservable<string> = ko.observable("");
        selectedWkpId: KnockoutObservable<string> = ko.observable(null);
        selectedWkpGroupId: KnockoutObservable<string> = ko.observable(null);

        selectedEmployeeIdTxt: KnockoutObservable<string>;

        enable: KnockoutObservable<boolean> = ko.observable(false);

        constructor(params: any) {
            super();
            const vm = this;
            vm.startDateString = ko.observable("");
            vm.endDateString = ko.observable("");
            vm.dateValue = ko.observable({startDate: new Date().toISOString(), endDate: new Date().toISOString()});
            vm.startDateString.subscribe(function (value) {
                vm.dateValue().startDate = value;
                vm.dateValue.valueHasMutated();
            });
            vm.endDateString.subscribe(function (value) {
                vm.dateValue().endDate = value;
                vm.dateValue.valueHasMutated();
            });

            vm.selectedEmployeeIdTxt = ko.observable("4f1ceb83-c59d-482f-bbd5-85c42dda1c5c, 74c2fe23-8772-4bb7-b3fc-bcc98324521f");
        }

        created(params: any) {
            const vm = this;
        }

        mounted() {
            const vm = this;
        }

        openKDL046(): void {
            let vm = this;
            let param = {
                unit: vm.orgUnit(),
                date: moment(vm.dateValue().endDate),
                workplaceId: vm.selectedWkpId(),
                workplaceGroupId: vm.selectedWkpGroupId(),
                showBaseDate: false
            };
            setShared('dataShareDialog046', param);
            nts.uk.ui.windows.sub.modal('/view/kdl/046/a/index.xhtml').onClosed(function (): any {
                let dataFrom046 = getShared('dataShareKDL046');
                if (!_.isNil(dataFrom046)) {
                    vm.enable(true);
                    vm.orgUnit(dataFrom046.unit);
                    vm.orgId(dataFrom046.unit === 0 ? dataFrom046.workplaceId : dataFrom046.workplaceGroupID);
                    vm.targetOrganizationName(dataFrom046.unit === 0 ? dataFrom046.workplaceName : dataFrom046.workplaceGroupName);
                }
            });
        }

        openKSU003D(): void {
            let vm = this;
            let data = {
                targetOrg: {
                    unit: vm.orgUnit(),
                    workplaceId: vm.orgUnit() === 0 ? vm.orgId() : null,
                    workplaceGroupId: vm.orgUnit() === 0 ? null : vm.orgId(),
                },
                employeeIds: _.split(vm.selectedEmployeeIdTxt().trim(), ","),
                targetPeriod: {
                    startDate: vm.dateValue().startDate,
                    endDate: vm.dateValue().endDate
                }
            };
            setShared('dataShareKsu003D', data);
            nts.uk.ui.windows.sub.modal("/view/ksu/003/d/index.xhtml");
        }
    }
}