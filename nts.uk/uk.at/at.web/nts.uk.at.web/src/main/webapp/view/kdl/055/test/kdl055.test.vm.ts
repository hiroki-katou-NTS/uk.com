/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
import setShared = nts.uk.ui.windows.setShared;
import getShared = nts.uk.ui.windows.getShared;


module nts.uk.at.view.kdl055.test.viewmodel {

    @bean()
    export class KDL055TestViewModel extends ko.ViewModel {
        sIDs: string[] = [];
        KEY: string = 'nts.uk.characteristics.ksu001Data';
        listSid: KnockoutObservableArray<string> = ko.observableArray([]);
        startDate: string;
        endDate: string;
        targetInfo: any;

        created() {
            const vm = this;
            let item = uk.localStorage.getItem(vm.KEY);
            let userInfor: IUserInfor = {};
            if (item.isPresent()) {
                userInfor = JSON.parse(item.get());
            }
            let viewMode = item.isPresent() ? userInfor.disPlayFormat : 'time';
            
            let param = {
                viewMode: viewMode,
                startDate: null,
                endDate  : null,
                shiftPalletUnit: item.isPresent() ? userInfor.shiftPalletUnit : 1, // 1: company , 2 : workPlace 
                pageNumberCom: item.isPresent() ? userInfor.shiftPalettePageNumberCom : 1,
                pageNumberOrg: item.isPresent() ? userInfor.shiftPalettePageNumberOrg : 1,
                getActualData: false,
                listShiftMasterNotNeedGetNew: item.isPresent() ? userInfor.shiftMasterWithWorkStyleLst : [], // List of shifts không cần lấy mới
                listSid: vm.listSid(),
                unit: item.isPresent() ? userInfor.unit : 0,
                workplaceId     : null,
                workplaceGroupId: null,
            }

            vm.$blockui('show');
            vm.$ajax(paths.getDataStartScreen, param).done((res) => {
                if (res) {
                    vm.listSid(_.map(res.listEmpInfo, 'employeeId'));
                    vm.startDate = res.dataBasicDto.startDate;
                    vm.endDate = res.dataBasicDto.endDate;
                    vm.targetInfo = {unit: res.dataBasicDto.unit, workplaceId: res.dataBasicDto.workplaceId, workplaceGroupId: res.dataBasicDto.workplaceGroupId};
                }
            }).fail((err) => {
                if (err) {
                    console.log(err);
                    vm.$dialog.error(err.messageId)
                }
            }).always(() => vm.$blockui('hide') );
        }

        mounted() {

        }

        openDialog() {
            const vm = this;
            let param: any = {unit: vm.targetInfo.unit, workplaceId: vm.targetInfo.workplaceId, workplaceGroupId: vm.targetInfo.workplaceGroupId, 
                sIDs: vm.listSid(), startDate: vm.startDate, endDate: vm.endDate};

            vm.$window.modal('at', '/view/kdl/055/a/index.xhtml', param)
            .then(() => {
                let paramB = getShared('paramB');
                if (paramB) {
                    return vm.$window.modal('at', '/view/kdl/055/b/index.xhtml', paramB).then(() => {
                        vm.openDialog();
                    });
                }
            });
        }
    }

    const paths = {
        getDataStartScreen: "screen/at/schedule/start"
    }
}