/// <reference path='../../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.kmt010.a.tabs.viewmodel {

    @component({
        name: 'kmt010-tab-work',
        template: `<div class="kmt010-tab-work">
                <div class="mb-10 table">
                  <div class="cell valign-center" data-bind="ntsFormLabel: { required: false }">
                    <span data-bind="text: $vm.$i18n('KMT010_6')"></span>
                  </div>
                </div>
                <div class="button mb-10">
                  <button class="A6_1" data-bind="text: $vm.$i18n('KMT010_7'), click: openDialogKDL012">1</button>
                  <button class="danger" data-bind="text: $vm.$i18n('KMT010_8'), click: deleteRowItem, enable: currentCodeList().length &gt; 0">2</button>
                </div> 						
                <div class="gridList">
                  <table id="multi-list" data-bind="ntsGridList: {
                    height: 265,
                    options: tabWorkSetting,
                    optionsValue: 'code',
                    primaryKey: 'code',
                    itemDraggable: false,
                    columns: ko.observableArray([
                      { headerText: $i18n('KMT010_10'), key: 'code', hidden: true },
                      { headerText: $i18n('KMT010_10'), key: 'displayCodeName', width: 300, columnCssClass: 'limited-label', formatter: _.escape },
                      { headerText: $i18n('KMT010_11'), key: 'displayExpiration', width: 210, formatter: _.escape  }
                    ]),
                    multiple: true,
                    value: currentCodeList
                  }"></table>
                </div>
              </div>`
    })

    class WorkTab extends ko.ViewModel {
        isNewMode: KnockoutObservable<boolean> = ko.observable(true);
        currentCodeList: KnockoutObservableArray<string> = ko.observableArray([]);
        tabWorkSetting: KnockoutObservableArray<WorkItem>;
        workFrameNoSelection: KnockoutObservable<number> = ko.observable(0);
        indexTab: KnockoutObservable<number> = ko.observable(0);

        constructor(params: any) {
            super();
            const vm = this;

            if (params) {
                //input from parent
                vm.indexTab(params.index);
                vm.workFrameNoSelection(params.workFrameNoSelection);
                vm.tabWorkSetting = params.listOfWorkItems;

            }
        }

        created(params: any) {
            const vm = this;
        }

        openDialogKDL012() {
            const vm = this;
            //kdl012 input
            let params = {
                isMultiple: true, //選択モード single or multiple
                showExpireDate: true, //表示モード	show/hide expire date
                referenceDate: moment().format('YYYY/MM/DD'), //システム日付
                workFrameNoSelection: vm.workFrameNoSelection(), //作業枠NO選択
                selectionCodeList: vm.tabWorkSetting().map(i => i.code) // 初期選択コードリスト
            }

            vm.$window.modal('/view/kdl/012/index.xhtml', params).done((data) => {
                if (data && data.setShareKDL012) {
                    vm.$blockui('show');
                    vm.$ajax('at/shared/scherec/taskmanagement/task/kmt009/getlist', {frameNo: vm.workFrameNoSelection()}).done((tasks: Array<any>) => {
                        let newListOfWorkItems: Array<WorkItem> = tasks
                            .filter((t: any) => data.setShareKDL012.indexOf(t.code) >= 0)
                            .map((x: any) => new WorkItem(
                                x.code,
                                x.displayInfo.taskName,
                                x.expirationStartDate,
                                x.expirationEndDate
                            ));
                        //update model
                        newListOfWorkItems = _.orderBy(newListOfWorkItems, ['code', 'asc']);
                        vm.tabWorkSetting.removeAll();
                        vm.tabWorkSetting(newListOfWorkItems);
                    }).fail((error) => {
                        vm.$dialog.error(error);
                    }).always(() => {
                        vm.$blockui('hide');
                    });
                }
            });
        }

        deleteRowItem() {
            const vm = this;
            let newListOfWorkItems = _.filter(vm.tabWorkSetting(), (x) => {
                return !_.includes(vm.currentCodeList(), x.code)
            });
            vm.tabWorkSetting.removeAll();
            vm.tabWorkSetting(newListOfWorkItems);
        }
    }

    export class ListItem {
        checked: KnockoutObservable<boolean> = ko.observable(false);
        rowId: number;
        name_code: string;
        expiration: string;

        constructor(checked: boolean, rowId: number, name_code: string, expiration: string) {
            this.checked(checked);
            this.rowId = rowId;
            this.name_code = name_code;
            this.expiration = expiration;
        }
    }

    export class WorkItem {
        code: string;
        name: string;
        displayCodeName: string;
        displayExpiration: string;
        //remark: string;
        startDate: string;
        endDate: string;

        constructor(code?: string, name?: string, startDate?: string, endDate?: string, remark?: string) {
            this.code = code;
            this.name = name;
            this.startDate = startDate;
            this.endDate = endDate;
            this.displayCodeName = code + ((!_.isNull(name) && !_.isEmpty(name)) ? ' ' + name : '');
            this.displayExpiration = startDate + ' ～ ' + endDate;
            //this.remark = remark;
        }
    }
}