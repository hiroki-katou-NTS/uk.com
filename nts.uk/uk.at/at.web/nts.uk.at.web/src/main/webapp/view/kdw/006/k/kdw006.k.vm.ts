/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk006.k {

    const API = {
        LIST_WORK_INFO: 'at/record/kdw006/view-k/get-list-work',
        LIST_HISTORY: 'at/record/kdw006/view-k/get-list-history',
        WORK_INFO_DATAIL: 'at/record/kdw006/view-k/get-work-info-detail',
        WORK_INFO_DATAIL_BY_HISTORYS: 'at/record/kdw006/view-k/get-list-work-info-detail',
        REGISTER: 'at/record/kdw006/view-k/register-new-obtion',
        UPDATE: 'at/record/kdw006/view-k/update-obtion',
        REMOTE: 'at/record/kdw006/view-k/delete'
    }

    const dateNow = new Date()
    const DATE_FORMAT = 'YYYY/MM/DD';

    @bean()
    export class ViewModel extends ko.ViewModel {

        public itemWorks: KnockoutObservableArray<IWorkInfo> = ko.observableArray([]);
        public selectedItemWork: KnockoutObservable<String> = ko.observable('');
        public isEnable: KnockoutObservable<boolean>;
        public isEditable: KnockoutObservable<boolean>;
        public historys: KnockoutObservableArray<IWorkDetail> = ko.observableArray([]);
        public currentCode: KnockoutObservable<String> = ko.observable('');
        public startDate: KnockoutObservable<String> = ko.observable(moment(dateNow).format(DATE_FORMAT));
        public endDate: KnockoutObservable<String> = ko.observable(moment(dateNow).format(DATE_FORMAT));
        public screenMode: KnockoutObservable<number> = ko.observable(SCREEN_MODE.UPDATE);
        public historyLocals: IHistory[] = [];
        public modelHistory: KnockoutObservable<IHistory> = ko.observable();
        public model: WorkDetail = new WorkDetail();


        created() {
            const vm = this;

            const inputListWork = {
                cId: vm.$user.companyId,
                items: [25, 26, 27, 28, 29]
            }

            vm.selectedItemWork.subscribe(() => {
                vm.reloadList();
            });

            vm.currentCode.subscribe(() => {
                vm.reloadData();
                vm.screenMode(SCREEN_MODE.UPDATE);
                vm.screenMode.valueHasMutated();
            })

            vm.$blockui('invisible')
                .then(() => {
                    vm.$ajax('at', API.LIST_WORK_INFO, inputListWork)
                        .then((data: IWorkInfo[]) => {
                            if (data.length === 0) {
                                vm.screenMode(SCREEN_MODE.NOT_HISTORY);
                            } else {
                                vm.screenMode(SCREEN_MODE.UPDATE);
                                vm.itemWorks(data);
                                vm.selectedItemWork(data[0].itemId);
                            }
                        })
                })
                .then(() => {
                    vm.$ajax('at', API.LIST_HISTORY)
                        .then((history: IHistory[]) => {
                            vm.historyLocals = history;
                            vm.getDate();
                        })
                })
                .always(() => {
                    vm.$blockui('clear');
                });

            vm.isEnable = ko.observable(true);
            vm.isEditable = ko.observable(true);

        }

        mounted() {
            const vm = this;
            const index = _.map(ko.unwrap(vm.historys), m => m.code).indexOf(ko.unwrap(vm.model.code));

            vm.startDate.subscribe(() => vm.reloadList(index));
            vm.endDate.subscribe(() => vm.startDate.valueHasMutated());
        }

        getDate() {
            const vm = this;

            var historys: IHistory[] = [];
            _.forEach(vm.historyLocals, ((value: IHistory) => {
                if (value.itemId === ko.unwrap(vm.selectedItemWork)) {
                    historys.push(value);
                }
            }));
            console.log(ko.unwrap(vm.selectedItemWork));
            if (historys.length > 0) {
                _.orderBy(historys, ['itemId'], ['asc']);

                vm.startDate(moment(historys[0].dateHistoryItems[0].startDate).format(DATE_FORMAT));
                vm.endDate(moment(historys[0].dateHistoryItems[0].endDate).format(DATE_FORMAT));
                vm.selectedItemWork.valueHasMutated();
            }
        }

        reloadList(index?: number) {
            const vm = this,
                startDate = new Date(ko.unwrap(vm.startDate) as string),
                endDate = new Date(ko.unwrap(vm.endDate) as string);
            var listHistorys: IHistoryInfo[] = [];
            var historyDateils: IWorkDetail[] = [];
            let dfdGetAllData = $.Deferred();

            vm.$blockui('invisible')
                .then(() => {
                    _.forEach(vm.historyLocals, ((value) => {
                        if (value.itemId === ko.unwrap(vm.selectedItemWork)) {
                            _.forEach(value.dateHistoryItems, ((item: IHistoryInfo) => {
                                if (new Date(moment(item.startDate).format(DATE_FORMAT)) <= startDate && new Date(moment(item.endDate).format(DATE_FORMAT)) >= endDate) {
                                    listHistorys.push(item);
                                }
                            }))
                        }
                    }));
                })
                .then(() => {
                    _.forEach(listHistorys, ((item: IHistoryInfo) => {
                        vm.$ajax('at', API.WORK_INFO_DATAIL, { historyId: item.historyId })
                            .done((data: IWorkDetail[]) => {
                                _.forEach(data, ((value: IWorkDetail) => {
                                    historyDateils.push(value);
                                }));
                            })
                            .then(() => {
                                if (item.historyId === listHistorys[listHistorys.length - 1].historyId) {
                                    dfdGetAllData.resolve();
                                }
                            });
                    }));
                });
            $.when(dfdGetAllData)
                .done(() => {
                    vm.historys(_.orderBy(historyDateils, ['code'], ['asc']));

                    if (index) {
                        vm.currentCode(ko.unwrap(vm.historys)[index].code);
                    } else {
                        vm.currentCode(historyDateils[0].code);
                    }
                })
                .then(() => {
                    vm.$blockui('clear');
                })
        }

        reloadData() {
            const vm = this;
            vm.model.update(_.find(ko.unwrap(vm.historys), ((value: IWorkDetail) => { return value.code === ko.unwrap(vm.currentCode) })));
        }

        openViewL() {
            const vm = this;

            var data: IHistory;
            _.forEach(vm.historyLocals, (item: IHistory) => {
                if (item.itemId === ko.unwrap(vm.selectedItemWork)) {
                    if (_.find(item.dateHistoryItems, ((value: IHistoryInfo) => { return value.historyId === ko.unwrap(vm.model.historyId) }))) {
                        data = item;
                    }
                }
            });

            const param = {
                itemId: ko.unwrap(vm.selectedItemWork),
                history: data
            }

            vm.$window.modal('at', '/view/kdw/006/l/index.xhtml', param);
        }

        new() {
            const vm = this;
            vm.model.update({
                historyId: ko.unwrap(vm.model.historyId),
                itemId: ko.unwrap(vm.model.itemId),
                code: '',
                name: '',
                externalCode: ''
            });
            vm.screenMode(SCREEN_MODE.NEW);
        }

        addOrUpdate() {
            const vm = this;
            const param = {
                historyId: ko.unwrap(vm.model.historyId),
                choiceCode: ko.unwrap(vm.model.code),
                optionName: ko.unwrap(vm.model.name),
                eternalCodeOfChoice: ko.unwrap(vm.model.externalCode),
                itemId: ko.unwrap(vm.model.itemId)
            }
            const index = _.map(ko.unwrap(vm.historys), m => m.code).indexOf(ko.unwrap(vm.model.code));
            let reloadDateSuccess = $.Deferred();

            if (ko.unwrap(vm.screenMode) === SCREEN_MODE.NEW) {
                vm.$blockui('invisible')
                    .then(() => {
                        vm.$ajax('at', API.REGISTER, param)
                            .done(() => {
                                vm.$dialog.info('Msg_15');
                                vm.screenMode(SCREEN_MODE.UPDATE);
                                vm.reloadList();
                                reloadDateSuccess.resolve();
                            })
                            .fail((data: any) => {
                                vm.$dialog.error(data.messageId);
                            });
                    })
                    .always(() => {
                        vm.$blockui('clear');
                    });

                $.when(reloadDateSuccess)
                    .done(() => {
                        vm.currentCode(param.choiceCode);
                    })
            }
            if (ko.unwrap(vm.screenMode) === SCREEN_MODE.UPDATE) {
                vm.$blockui('invisible')
                    .then(() => {
                        vm.$ajax('at', API.UPDATE, param)
                            .done(() => {
                                vm.$dialog.info('Msg_15');
                                vm.reloadList(index);
                                reloadDateSuccess.resolve();
                            })
                            .fail((data: any) => {
                                vm.$dialog.error(data.messageId);
                            });
                    })
                    .always(() => {
                        vm.$blockui('clear');
                    });

                $.when(reloadDateSuccess)
                    .done(() => {
                        vm.currentCode(param.choiceCode);
                    })
            }
        }

        delete() {
            const vm = this;
            const param = {
                historyId: ko.unwrap(vm.model.historyId),
                choiceCode: ko.unwrap(vm.model.code)
            }, index = _.map(ko.unwrap(vm.historys), m => m.code).indexOf(ko.unwrap(vm.model.code));

            nts.uk.ui.dialog
                .confirm({ messageId: "Msg_18" })
                .ifYes(() => {
                    vm.$blockui("invisible")
                        .then(() => {
                            vm.$ajax('at', API.REMOTE, param)
                                .then(() => vm.$dialog.info({ messageId: "Msg_16" }))
                                .then(() => vm.reloadList(index))
                                .always(() => vm.$blockui('clear'))
                        })
                })
        }

        validate(action: 'clear' | undefined = undefined) {
            if (action === 'clear') {
                return $.Deferred().resolve()
                    .then(() => $('.nts-input').ntsError('clear'));
            } else {
                return $.Deferred().resolve()
                    /** Gọi xử lý validate của kiban */
                    .then(() => $('.nts-input').trigger("validate"))
                    /** Nếu có lỗi thì trả về false, không thì true */
                    .then(() => !$('.nts-input').ntsError('hasError'));
            }
        }
    }

    interface ItemModel {
        code: String;
        name: String;
    }

    enum SCREEN_MODE {
        NEW = 1,
        UPDATE = 2,
        NOT_HISTORY = 3
    }

    interface IWorkInfo {
        /** 項目ID*/
        itemId: String;
        /** 名称*/
        name: String;
        /** フォーマット設定に表示する*/
        useAtr: boolean;
    }

    interface IHistory {
        /** 項目ID */
        itemId: String;
        /** 履歴項目 */
        dateHistoryItems: IHistoryInfo[];
    }

    class History {
        itemId: KnockoutObservable<String> = ko.observable('');
        dateHistoryItems: KnockoutObservableArray<HistoryInfo> = ko.observableArray([]);

        constructor(param: IHistory) {
            this.itemId(param.itemId);
            var historyInfos: HistoryInfo[] = [];
            _.forEach(param.dateHistoryItems, ((value: IHistoryInfo) => {
                historyInfos.push(new HistoryInfo(value));
            }));
            this.dateHistoryItems(historyInfos);
        }
    }

    interface IHistoryInfo {
        historyId: String;
        startDate: Date;
        endDate: Date;
    }

    class HistoryInfo {
        historyId: KnockoutObservable<String> = ko.observable('');
        startDate: KnockoutObservable<Date> = ko.observable(new Date);;
        endDate: KnockoutObservable<Date> = ko.observable(new Date);
        period: KnockoutObservable<String> = ko.observable('');

        constructor(param: IHistoryInfo) {
            this.historyId(param.historyId);
            this.startDate(param.startDate);
            this.endDate(param.endDate);
            this.period(param.startDate.toString() + ' ~ ' + param.endDate.toString());
        }
    }

    interface IWorkDetail {
        /** 履歴ID */
        historyId: String;
        /** 項目ID */
        itemId: String;
        /** コード */
        code: String;
        /** 名称 */
        name: String;
        /** 外部コード */
        externalCode: String;
    }

    class WorkDetail {
        historyId: KnockoutObservable<String> = ko.observable('');
        itemId: KnockoutObservable<String> = ko.observable('');
        code: KnockoutObservable<String> = ko.observable('');
        name: KnockoutObservable<String> = ko.observable('');
        externalCode: KnockoutObservable<String> = ko.observable('');

        constructor(param?: IWorkDetail) {
            if (param) {
                this.historyId(param.historyId);
                this.itemId(param.itemId);
                this.code(param.code);
                this.name(param.name);
                this.externalCode(param.externalCode);
            }
        }

        public update(param?: IWorkDetail) {
            if (param) {
                this.historyId(param.historyId);
                this.itemId(param.itemId);
                this.code(param.code);
                this.name(param.name);
                this.externalCode(param.externalCode);
            }
        }
    }
}
